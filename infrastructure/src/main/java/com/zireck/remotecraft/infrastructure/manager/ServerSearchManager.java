package com.zireck.remotecraft.infrastructure.manager;

import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import com.zireck.remotecraft.infrastructure.exception.NoResponseException;
import com.zireck.remotecraft.infrastructure.protocol.NetworkProtocolHelper;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.data.Server;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerMapper;
import com.zireck.remotecraft.infrastructure.tool.NetworkTransmitter;
import com.zireck.remotecraft.infrastructure.validation.ServerMessageValidator;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Collection;
import timber.log.Timber;

public class ServerSearchManager {

  private static final int SEARCH_PORT = 9998;
  private static final String BROADCAST_ADDRESS = "255.255.255.255";
  private static final int RETRY_COUNT = 5;

  private NetworkTransmitter networkTransmitter;
  private NetworkInterfaceManager networkInterfaceManager;
  private NetworkProtocolManager networkProtocolManager;
  private MessageJsonMapper messageJsonMapper;
  private ServerMapper serverMapper;
  private ServerMessageValidator serverValidator;

  public ServerSearchManager(NetworkTransmitter networkTransmitter,
      NetworkInterfaceManager networkInterfaceManager,
      NetworkProtocolManager networkProtocolManager, MessageJsonMapper messageJsonMapper,
      ServerMapper serverMapper, ServerMessageValidator serverValidator) {
    this.networkTransmitter = networkTransmitter;
    this.networkInterfaceManager = networkInterfaceManager;
    this.networkProtocolManager = networkProtocolManager;
    this.messageJsonMapper = messageJsonMapper;
    this.serverMapper = serverMapper;
    this.serverValidator = serverValidator;

    try {
      networkTransmitter.setBroadcast(true);
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  public Maybe<WorldEntity> searchWorld() {
    return searchServer().map(serverMapper::transform);
  }

  private Maybe<Server> searchServer() {
    return Observable.<Message>create(subscriber -> {
      Message message = null;

      try {
        sendDiscoveryRequest();
        message = waitForServerResponse();
      } catch (NoResponseException | IOException e) {
        subscriber.onError(e);
      }

      if (message != null) {
        subscriber.onNext(message);
      }
      subscriber.onComplete();
    }).retryWhen(errors -> errors.zipWith(Observable.range(0, RETRY_COUNT), (n, i) -> i)
        .flatMap(retryCount -> Observable.empty()))
        .filter(serverValidator::isValid)
        .firstElement()
        .map(serverValidator::cast);
  }

  private void sendDiscoveryRequest() throws IOException {
    sendRequestToDefaultBroadcastAddress();
    sendRequestToEveryInterfaceBroadcastAddress();
  }

  private void sendRequestToDefaultBroadcastAddress() throws IOException {
    DatagramPacket datagramPacket = getDatagramPacket(InetAddress.getByName(BROADCAST_ADDRESS));
    networkTransmitter.send(datagramPacket);
  }

  private void sendRequestToEveryInterfaceBroadcastAddress() throws IOException {
    DatagramPacket datagramPacket;
    Collection<InetAddress> broadcastAddresses = networkInterfaceManager.getBroadcastAddresses();

    for (InetAddress broadcastAddress : broadcastAddresses) {
      datagramPacket = getDatagramPacket(broadcastAddress);
      networkTransmitter.send(datagramPacket);
    }
  }

  private DatagramPacket getDatagramPacket(InetAddress inetAddress) {
    String serverSearchRequest = networkProtocolManager.composeServerSearchRequest();
    Timber.d("Sending JSON:");
    Timber.d(serverSearchRequest);
    byte[] discoveryCommand = serverSearchRequest.getBytes();
    return new DatagramPacket(discoveryCommand, discoveryCommand.length, inetAddress, SEARCH_PORT);
  }

  private Message waitForServerResponse() throws IOException, NoResponseException {
    byte[] responseBuffer = new byte[15000];
    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

    networkTransmitter.setTimeout(NetworkProtocolHelper.SOCKET_TIMEOUT);
    networkTransmitter.receive(responsePacket);

    Message message = parseResponse(responsePacket);

    networkTransmitter.shutdown();

    return message;
  }

  private Message parseResponse(DatagramPacket responsePacket) throws NoResponseException {
    if (responsePacket == null || responsePacket.getData() == null) {
      Timber.e("Response Packet cannot be null.");
      return null;
    }

    String messageJsonResponse = new String(responsePacket.getData()).trim();
    Message message = messageJsonMapper.transformMessage(messageJsonResponse);

    if (message == null || !message.isSuccess() || !message.isServer()) {
      Timber.e("Invalid message received");
      throw new NoResponseException();
    }

    return message;
  }
}
