package com.zireck.remotecraft.infrastructure.manager;

import android.text.TextUtils;
import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import com.zireck.remotecraft.infrastructure.exception.NoResponseException;
import com.zireck.remotecraft.infrastructure.protocol.NetworkProtocolHelper;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.data.Server;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerMapper;
import com.zireck.remotecraft.infrastructure.provider.broadcastaddress.BroadcastAddressProvider;
import com.zireck.remotecraft.infrastructure.tool.NetworkConnectionlessTransmitter;
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
  private static final int RESPONSE_BUFFER_SIZE = 15000;

  private NetworkConnectionlessTransmitter networkConnectionlessTransmitter;
  private BroadcastAddressProvider broadcastAddressProvider;
  private NetworkProtocolManager networkProtocolManager;
  private MessageJsonMapper messageJsonMapper;
  private ServerMapper serverMapper;
  private ServerMessageValidator serverValidator;
  private String ipAddress;

  public ServerSearchManager(NetworkConnectionlessTransmitter networkConnectionlessTransmitter,
      BroadcastAddressProvider broadcastAddressProvider,
      NetworkProtocolManager networkProtocolManager, MessageJsonMapper messageJsonMapper,
      ServerMapper serverMapper, ServerMessageValidator serverValidator) {
    this.networkConnectionlessTransmitter = networkConnectionlessTransmitter;
    this.broadcastAddressProvider = broadcastAddressProvider;
    this.networkProtocolManager = networkProtocolManager;
    this.messageJsonMapper = messageJsonMapper;
    this.serverMapper = serverMapper;
    this.serverValidator = serverValidator;
  }

  public Maybe<WorldEntity> searchWorld() {
    return searchServer().map(serverMapper::transform);
  }

  public Maybe<WorldEntity> searchWorld(String ipAddress) {
    this.ipAddress = ipAddress;
    return searchWorld();
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
    if (!TextUtils.isEmpty(ipAddress)) {
      sendRequestTo(ipAddress);
    } else {
      enableBroadcast();
      sendRequestTo(BROADCAST_ADDRESS);
      sendRequestToEveryInterfaceBroadcastAddress();
    }
  }

  private void sendRequestTo(String ip) throws IOException {
    DatagramPacket datagramPacket = getDatagramPacket(InetAddress.getByName(ip));
    networkConnectionlessTransmitter.send(datagramPacket);
  }

  private void enableBroadcast() throws SocketException {
    networkConnectionlessTransmitter.setBroadcast(true);
  }

  private void sendRequestToEveryInterfaceBroadcastAddress() throws IOException {
    DatagramPacket datagramPacket;
    Collection<InetAddress> broadcastAddresses = broadcastAddressProvider.getBroadcastAddresses();

    for (InetAddress broadcastAddress : broadcastAddresses) {
      datagramPacket = getDatagramPacket(broadcastAddress);
      networkConnectionlessTransmitter.send(datagramPacket);
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
    byte[] responseBuffer = new byte[RESPONSE_BUFFER_SIZE];
    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

    networkConnectionlessTransmitter.setTimeout(NetworkProtocolHelper.SOCKET_TIMEOUT);
    networkConnectionlessTransmitter.receive(responsePacket);

    Message message = parseResponse(responsePacket);

    networkConnectionlessTransmitter.shutdown();

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
