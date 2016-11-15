package com.zireck.remotecraft.infrastructure.manager;

import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import com.zireck.remotecraft.infrastructure.exception.InvalidWorldException;
import com.zireck.remotecraft.infrastructure.exception.NoResponseException;
import com.zireck.remotecraft.infrastructure.protocol.NetworkProtocolHelper;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.data.Server;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerMapper;
import com.zireck.remotecraft.infrastructure.validation.ServerMessageValidator;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Collection;
import rx.Observable;
import timber.log.Timber;

public class NetworkDiscoveryManager {

  private static final int RETRY_COUNT = 5;

  private NetworkInterfaceManager networkInterfaceManager;
  private NetworkProtocolManager networkProtocolManager;
  private MessageJsonMapper messageJsonMapper;
  private ServerMapper serverMapper;
  private ServerMessageValidator serverValidator;

  private DatagramSocket datagramSocket;
  private Message message = null;

  public NetworkDiscoveryManager(NetworkInterfaceManager networkInterfaceManager,
      NetworkProtocolManager networkProtocolManager, MessageJsonMapper messageJsonMapper,
      ServerMapper serverMapper, ServerMessageValidator serverValidator) {
    this.networkInterfaceManager = networkInterfaceManager;
    this.networkProtocolManager = networkProtocolManager;
    this.messageJsonMapper = messageJsonMapper;
    this.serverMapper = serverMapper;
    this.serverValidator = serverValidator;

    try {
      datagramSocket = new DatagramSocket();
      datagramSocket.setBroadcast(true);
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  public Observable<WorldEntity> discoverWorld() {
    Observable<Server> serverObservable = Observable.create(subscriber -> {
      Message message = null;

      try {
        sendDiscoveryRequest();
        message = waitForServerResponse();
      } catch (NoResponseException | IOException e) {
        subscriber.onError(e);
        return;
      }

      if (!serverValidator.isValid(message)) {
        subscriber.onError(new InvalidWorldException());
      }

      Server server = serverValidator.cast(message);

      subscriber.onNext(server);
      subscriber.onCompleted();
    });

    return serverObservable.map(serverMapper::transform);
  }

  private void sendDiscoveryRequest() throws IOException {
    sendRequestToDefaultBroadcastAddress();
    sendRequestToEveryInterfaceBroadcastAddress();
  }

  private Message waitForServerResponse() throws IOException, NoResponseException {
    DatagramPacket responsePacket;
    byte[] responseBuffer;
    int failCount = 0;
    while (failCount < RETRY_COUNT) {
      responseBuffer = new byte[15000];
      responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

      datagramSocket.setSoTimeout(NetworkProtocolHelper.SOCKET_TIMEOUT);

      try {
        datagramSocket.receive(responsePacket);
      } catch (SocketTimeoutException e) {
        continue;
      }

      message = parseResponse(responsePacket);
      if (message != null) {
        break;
      }

      failCount++;
    }

    datagramSocket.disconnect();
    datagramSocket.close();

    if (message == null && failCount >= RETRY_COUNT) {
      throw new NoResponseException();
    }

    return message;
  }

  private void sendRequestToDefaultBroadcastAddress() throws IOException {
    DatagramPacket datagramPacket = getDatagramPacket(InetAddress.getByName("255.255.255.255"));
    datagramSocket.send(datagramPacket);
  }

  private void sendRequestToEveryInterfaceBroadcastAddress() throws IOException {
    DatagramPacket datagramPacket;
    Collection<InetAddress> broadcastAddresses = networkInterfaceManager.getBroadcastAddresses();

    for (InetAddress broadcastAddress : broadcastAddresses) {
      datagramPacket = getDatagramPacket(broadcastAddress);
      datagramSocket.send(datagramPacket);
    }
  }

  private DatagramPacket getDatagramPacket(InetAddress inetAddress) {
    String discoveryRequest = networkProtocolManager.getDiscoveryRequest();
    Timber.d("Sending JSON:");
    Timber.d(discoveryRequest);
    byte[] discoveryCommand = discoveryRequest.getBytes();
    return new DatagramPacket(discoveryCommand, discoveryCommand.length, inetAddress,
        NetworkProtocolHelper.DISCOVERY_PORT);
  }

  private Message parseResponse(DatagramPacket responsePacket) {
    if (responsePacket == null || responsePacket.getData() == null) {
      Timber.e("Response Packet cannot be null.");
      return null;
    }

    String messageJsonResponse = new String(responsePacket.getData()).trim();
    Message message = messageJsonMapper.transformMessage(messageJsonResponse);

    if (message == null || !message.isSuccess() || !message.isServer()) {
      Timber.e("Invalid message received");
      return null;
    }

    return message;
  }
}
