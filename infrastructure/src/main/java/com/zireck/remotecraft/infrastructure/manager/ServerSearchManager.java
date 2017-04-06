package com.zireck.remotecraft.infrastructure.manager;

import com.zireck.remotecraft.infrastructure.entity.NetworkAddressEntity;
import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.exception.InvalidServerException;
import com.zireck.remotecraft.infrastructure.exception.NoResponseException;
import com.zireck.remotecraft.infrastructure.network.NetworkConnectionlessTransmitter;
import com.zireck.remotecraft.infrastructure.network.NetworkPacket;
import com.zireck.remotecraft.infrastructure.protocol.ProtocolMessageComposer;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.base.type.ServerProtocol;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.zireck.remotecraft.infrastructure.protocol.messages.CommandMessage;
import com.zireck.remotecraft.infrastructure.provider.ServerSearchSettings;
import com.zireck.remotecraft.infrastructure.provider.broadcastaddress.BroadcastAddressProvider;
import com.zireck.remotecraft.infrastructure.validation.ServerMessageValidator;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import java.io.IOException;
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
  private int subscribers = -1;

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

  public void setSubscribers(int subscribers) {
    if (subscribers <= 0) {
      return;
    }

    this.subscribers = subscribers;
  }

  public Observable<ServerEntity> searchServer(NetworkAddressEntity networkAddressEntity) {
    this.networkAddressEntity = networkAddressEntity;
    return searchServer();
  }

  public Observable<ServerEntity> searchServer() {
    int subscribers =
        this.subscribers <= 0 ? serverSearchSettings.getSubscribers() : this.subscribers;
    return performSearch().autoConnect(subscribers).map(serverProtocolMapper::transform);
  }

  private ConnectableObservable<ServerProtocol> performSearch() {
    return Observable.<Message>create(emitter -> {
      NetworkPacket incomingPacket = null;
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
        emitter.onComplete();
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
      .map(serverValidator::cast)
      .publish();
  }

  private void sendDiscoveryRequest() throws IOException {
    if (networkAddressEntity != null) {
      sendRequestTo(networkAddressEntity);
    } else {
      enableBroadcastTransmission();
      sendRequestTo(serverSearchSettings.getBroadcastAddress());
      sendRequestToEveryInterfaceBroadcastAddress();
    }
  }

  private void sendRequestTo(NetworkAddressEntity networkAddressEntity) throws IOException {
    InetAddress inetAddress = InetAddress.getByName(networkAddressEntity.ip());
    NetworkPacket outgoingPacket = getTransmissionPacket(inetAddress);
    networkConnectionlessTransmitter.send(outgoingPacket);
  }

  private void enableBroadcastTransmission() throws SocketException {
    networkConnectionlessTransmitter.setBroadcast(true);
  }

  private void sendRequestToEveryInterfaceBroadcastAddress() throws IOException {
    NetworkPacket outgoingPacket;
    Collection<InetAddress> broadcastAddresses = broadcastAddressProvider.getBroadcastAddresses();

    for (InetAddress broadcastAddress : broadcastAddresses) {
      outgoingPacket = getTransmissionPacket(broadcastAddress);
      networkConnectionlessTransmitter.send(outgoingPacket);
    }
  }

  private NetworkPacket getTransmissionPacket(InetAddress inetAddress) {
    CommandMessage getServerInfoCommand = protocolMessageComposer.composeGetServerInfoCommand();
    String getServerInfoCommandJson = messageJsonMapper.transformMessage(getServerInfoCommand);

    Timber.d("Sending JSON:");
    Timber.d(getServerInfoCommandJson);

    return new NetworkPacket(getServerInfoCommandJson, inetAddress, serverSearchSettings.getPort());
  }

  private NetworkPacket waitForServerResponse() throws IOException, NoResponseException {
    NetworkPacket networkResponsePacket =
        new NetworkPacket(serverSearchSettings.getResponseBufferSize());

    networkConnectionlessTransmitter.setTimeout(serverSearchSettings.getTimeout());
    networkResponsePacket = networkConnectionlessTransmitter.receive(networkResponsePacket);

    return networkResponsePacket;
  }

  private Message parseResponse(NetworkPacket incomingPacket) throws InvalidServerException {
    if (incomingPacket == null || incomingPacket.getContent() == null) {
      Timber.e("Response Packet cannot be null.");
      return null;
    }

    String messageJsonResponse = incomingPacket.getContent();
    Message message = messageJsonMapper.transformMessage(messageJsonResponse);

    if (message == null || !message.isSuccess() || !message.isServer()) {
      Timber.e("Invalid message received");
      throw new InvalidServerException();
    }

    return message;
  }
}
