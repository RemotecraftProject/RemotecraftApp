package com.zireck.remotecraft.infrastructure.manager;

import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.exception.InvalidServerException;
import com.zireck.remotecraft.infrastructure.exception.NoResponseException;
import com.zireck.remotecraft.infrastructure.protocol.NetworkProtocolHelper;
import com.zireck.remotecraft.infrastructure.protocol.ProtocolMessageComposer;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.base.Server;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerMapper;
import com.zireck.remotecraft.infrastructure.protocol.messages.CommandMessage;
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
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class ServerSearchManager {

  private static final int SEARCH_PORT = 9998;
  private static final String BROADCAST_ADDRESS = "255.255.255.255";
  private static final int RETRY_COUNT = 5;
  private static final int RESPONSE_BUFFER_SIZE = 15000;

  private final NetworkConnectionlessTransmitter networkConnectionlessTransmitter;
  private final BroadcastAddressProvider broadcastAddressProvider;
  private final ProtocolMessageComposer protocolMessageComposer;
  private final MessageJsonMapper messageJsonMapper;
  private final ServerMapper serverMapper;
  private final ServerMessageValidator serverValidator;
  private String ipAddress;

  public ServerSearchManager(NetworkConnectionlessTransmitter networkConnectionlessTransmitter,
      BroadcastAddressProvider broadcastAddressProvider,
      ProtocolMessageComposer protocolMessageComposer, MessageJsonMapper messageJsonMapper,
      ServerMapper serverMapper, ServerMessageValidator serverValidator) {
    this.networkConnectionlessTransmitter = networkConnectionlessTransmitter;
    this.broadcastAddressProvider = broadcastAddressProvider;
    this.protocolMessageComposer = protocolMessageComposer;
    this.messageJsonMapper = messageJsonMapper;
    this.serverMapper = serverMapper;
    this.serverValidator = serverValidator;
  }

  public Maybe<ServerEntity> searchServer(String ipAddress) {
    this.ipAddress = ipAddress;
    return searchServer();
  }

  public Maybe<ServerEntity> searchServer() {
    return doSearch().map(serverMapper::transform);
  }

  private Maybe<Server> doSearch() {
    return Observable.<Message>create(emitter -> {
      DatagramPacket response = null;
      Message message = null;

      try {
        sendDiscoveryRequest();
        response = waitForServerResponse();
        message = parseResponse(response);
      } catch (NoResponseException | IOException e) {
        emitter.onError(e);
      }

      if (message != null) {
        emitter.onNext(message);
      }
    }).retryWhen(errors ->
        errors
          .zipWith(Observable.range(0, RETRY_COUNT), (n, i) -> i)
          .flatMap(retryCount -> Observable.timer(retryCount, TimeUnit.SECONDS)))
      .filter(serverValidator::isValid)
      .firstElement()
      .map(serverValidator::cast);
  }

  private void sendDiscoveryRequest() throws IOException {
    if (ipAddress != null && ipAddress.length() > 0) {
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
    CommandMessage getServerInfoCommand = protocolMessageComposer.composeGetServerInfoCommand();
    String getServerInfoCommandJson = messageJsonMapper.transformMessage(getServerInfoCommand);

    Timber.d("Sending JSON:");
    Timber.d(getServerInfoCommandJson);

    byte[] socketPacket = getServerInfoCommandJson.getBytes();
    return new DatagramPacket(socketPacket, socketPacket.length, inetAddress, SEARCH_PORT);
  }

  private DatagramPacket waitForServerResponse() throws IOException, NoResponseException {
    byte[] responseBuffer = new byte[RESPONSE_BUFFER_SIZE];
    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

    networkConnectionlessTransmitter.setTimeout(NetworkProtocolHelper.SOCKET_TIMEOUT);
    networkConnectionlessTransmitter.receive(responsePacket);
    networkConnectionlessTransmitter.shutdown();

    return responsePacket;
  }

  private Message parseResponse(DatagramPacket responsePacket) throws InvalidServerException {
    if (responsePacket == null || responsePacket.getData() == null) {
      Timber.e("Response Packet cannot be null.");
      return null;
    }

    String messageJsonResponse = new String(responsePacket.getData()).trim();
    Message message = messageJsonMapper.transformMessage(messageJsonResponse);

    if (message == null || !message.isSuccess() || !message.isServer()) {
      Timber.e("Invalid message received");
      throw new InvalidServerException();
    }

    return message;
  }
}
