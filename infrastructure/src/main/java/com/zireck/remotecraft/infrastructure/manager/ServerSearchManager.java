package com.zireck.remotecraft.infrastructure.manager;

import com.zireck.remotecraft.infrastructure.entity.NetworkAddressEntity;
import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.exception.InvalidServerException;
import com.zireck.remotecraft.infrastructure.exception.NoResponseException;
import com.zireck.remotecraft.infrastructure.protocol.ProtocolMessageComposer;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.base.type.ServerProtocol;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.zireck.remotecraft.infrastructure.protocol.messages.CommandMessage;
import com.zireck.remotecraft.infrastructure.provider.ServerSearchSettings;
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

  private final ServerSearchSettings serverSearchSettings;
  private final NetworkConnectionlessTransmitter networkConnectionlessTransmitter;
  private final BroadcastAddressProvider broadcastAddressProvider;
  private final ProtocolMessageComposer protocolMessageComposer;
  private final MessageJsonMapper messageJsonMapper;
  private final ServerProtocolMapper serverProtocolMapper;
  private final ServerMessageValidator serverValidator;
  private NetworkAddressEntity networkAddressEntity;

  public ServerSearchManager(ServerSearchSettings serverSearchSettings,
      NetworkConnectionlessTransmitter networkConnectionlessTransmitter,
      BroadcastAddressProvider broadcastAddressProvider,
      ProtocolMessageComposer protocolMessageComposer, MessageJsonMapper messageJsonMapper,
      ServerProtocolMapper serverProtocolMapper, ServerMessageValidator serverValidator) {
    this.serverSearchSettings = serverSearchSettings;
    this.networkConnectionlessTransmitter = networkConnectionlessTransmitter;
    this.broadcastAddressProvider = broadcastAddressProvider;
    this.protocolMessageComposer = protocolMessageComposer;
    this.messageJsonMapper = messageJsonMapper;
    this.serverProtocolMapper = serverProtocolMapper;
    this.serverValidator = serverValidator;
  }

  public Maybe<ServerEntity> searchServer(NetworkAddressEntity networkAddressEntity) {
    this.networkAddressEntity = networkAddressEntity;
    return searchServer();
  }

  public Maybe<ServerEntity> searchServer() {
    return performSearch().map(serverProtocolMapper::transform);
  }

  private Maybe<ServerProtocol> performSearch() {
    return Observable.<Message>create(emitter -> {
      DatagramPacket incomingPacket = null;
      Message message = null;

      try {
        sendDiscoveryRequest();
        incomingPacket = waitForServerResponse();
        message = parseResponse(incomingPacket);
      } catch (NoResponseException | IOException e) {
        emitter.onError(e);
      }

      if (message != null) {
        emitter.onNext(message);
      }
    }).retryWhen(errors -> {
      Observable<Integer> range = Observable.range(1, serverSearchSettings.getRetryCount());
      Observable<Observable<Long>> zipWith = errors.zipWith(range,
          (e, i) -> i < serverSearchSettings.getRetryCount() ? Observable.timer(
              serverSearchSettings.getRetryDelayMultiplier() * i, TimeUnit.SECONDS)
              : Observable.error(e));
      return Observable.merge(zipWith);
    })
      .filter(serverValidator::isValid)
      .firstElement()
      .map(serverValidator::cast);
  }

  private void sendDiscoveryRequest() throws IOException {
    if (networkAddressEntity != null) {
      sendRequestTo(networkAddressEntity);
    } else {
      enableBroadcast();
      sendRequestTo(serverSearchSettings.getBroadcastAddress());
      sendRequestToEveryInterfaceBroadcastAddress();
    }
  }

  private void sendRequestTo(NetworkAddressEntity networkAddressEntity) throws IOException {
    InetAddress inetAddress = InetAddress.getByName(networkAddressEntity.getIp());
    DatagramPacket outgoingPacket = getTransmissionPacket(inetAddress);
    networkConnectionlessTransmitter.send(outgoingPacket);
  }

  private void enableBroadcast() throws SocketException {
    networkConnectionlessTransmitter.setBroadcast(true);
  }

  private void sendRequestToEveryInterfaceBroadcastAddress() throws IOException {
    DatagramPacket outgoingPacket;
    Collection<InetAddress> broadcastAddresses = broadcastAddressProvider.getBroadcastAddresses();

    for (InetAddress broadcastAddress : broadcastAddresses) {
      outgoingPacket = getTransmissionPacket(broadcastAddress);
      networkConnectionlessTransmitter.send(outgoingPacket);
    }
  }

  private DatagramPacket getTransmissionPacket(InetAddress inetAddress) {
    CommandMessage getServerInfoCommand = protocolMessageComposer.composeGetServerInfoCommand();
    String getServerInfoCommandJson = messageJsonMapper.transformMessage(getServerInfoCommand);

    Timber.d("Sending JSON:");
    Timber.d(getServerInfoCommandJson);

    byte[] payload = getServerInfoCommandJson.getBytes();
    return new DatagramPacket(payload, payload.length, inetAddress, serverSearchSettings.getPort());
  }

  private DatagramPacket waitForServerResponse() throws IOException, NoResponseException {
    byte[] responseBuffer = new byte[serverSearchSettings.getResponseBufferSize()];
    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

    networkConnectionlessTransmitter.setTimeout(serverSearchSettings.getTimeout());
    networkConnectionlessTransmitter.receive(responsePacket);

    return responsePacket;
  }

  private Message parseResponse(DatagramPacket incomingPacket) throws InvalidServerException {
    if (incomingPacket == null || incomingPacket.getData() == null) {
      Timber.e("Response Packet cannot be null.");
      return null;
    }

    String messageJsonResponse = new String(incomingPacket.getData()).trim();
    Message message = messageJsonMapper.transformMessage(messageJsonResponse);

    if (message == null || !message.isSuccess() || !message.isServer()) {
      Timber.e("Invalid message received");
      throw new InvalidServerException();
    }

    return message;
  }
}
