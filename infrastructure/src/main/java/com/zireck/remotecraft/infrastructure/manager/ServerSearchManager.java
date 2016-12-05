package com.zireck.remotecraft.infrastructure.manager;

import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
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
import java.util.Collection;
import rx.Observable;
import timber.log.Timber;

public class ServerSearchManager {

  private static final String BROADCAST_ADDRESS = "255.255.255.255";
  private static final int RETRY_COUNT = 5;

  private DatagramSocket datagramSocket;
  private NetworkInterfaceManager networkInterfaceManager;
  private NetworkProtocolManager networkProtocolManager;
  private MessageJsonMapper messageJsonMapper;
  private ServerMapper serverMapper;
  private ServerMessageValidator serverValidator;

  public ServerSearchManager(DatagramSocket datagramSocket,
      NetworkInterfaceManager networkInterfaceManager,
      NetworkProtocolManager networkProtocolManager, MessageJsonMapper messageJsonMapper,
      ServerMapper serverMapper, ServerMessageValidator serverValidator) {
    this.datagramSocket = datagramSocket;
    this.networkInterfaceManager = networkInterfaceManager;
    this.networkProtocolManager = networkProtocolManager;
    this.messageJsonMapper = messageJsonMapper;
    this.serverMapper = serverMapper;
    this.serverValidator = serverValidator;

    try {
      datagramSocket.setBroadcast(true);
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  public Observable<WorldEntity> searchWorld() {
    return searchServer().map(serverMapper::transform);
  }

  private Observable<Server> searchServer() {
    return Observable.<Message>create(subscriber -> {
      Message message = null;

      try {
        sendDiscoveryRequest();
        message = waitForServerResponse();
      } catch (NoResponseException | IOException e) {
        subscriber.onError(e);
      }

      subscriber.onNext(message);
      subscriber.onCompleted();
    })
      .retryWhen(errors ->
        errors
          .zipWith(Observable.range(0, RETRY_COUNT), (n, i) -> i)
          .flatMap(retryCount -> Observable.empty())
      )
      .takeFirst(serverValidator::isValid)
      .map(serverValidator::cast);
  }

  private void sendDiscoveryRequest() throws IOException {
    sendRequestToDefaultBroadcastAddress();
    sendRequestToEveryInterfaceBroadcastAddress();
  }

  private void sendRequestToDefaultBroadcastAddress() throws IOException {
    DatagramPacket datagramPacket = getDatagramPacket(InetAddress.getByName(BROADCAST_ADDRESS));
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

  private Message waitForServerResponse() throws IOException, NoResponseException {
    byte[] responseBuffer = new byte[15000];
    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

    datagramSocket.setSoTimeout(NetworkProtocolHelper.SOCKET_TIMEOUT);
    datagramSocket.receive(responsePacket);

    Message message = parseResponse(responsePacket);

    datagramSocket.disconnect();
    datagramSocket.close();

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
