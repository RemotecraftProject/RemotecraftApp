package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.domain.provider.PermissionProvider;
import com.zireck.remotecraft.infrastructure.manager.ServerSearchManager;
import com.zireck.remotecraft.infrastructure.protocol.ProtocolMessageComposer;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.zireck.remotecraft.infrastructure.provider.NetworkDataProvider;
import com.zireck.remotecraft.infrastructure.provider.PermissionDataProvider;
import com.zireck.remotecraft.infrastructure.provider.ServerSearchSettings;
import com.zireck.remotecraft.infrastructure.provider.broadcastaddress.AndroidBroadcastAddressProvider;
import com.zireck.remotecraft.infrastructure.provider.broadcastaddress.BroadcastAddressProvider;
import com.zireck.remotecraft.infrastructure.provider.networkinterface.AndroidNetworkInterfaceProvider;
import com.zireck.remotecraft.infrastructure.provider.networkinterface.NetworkInterfaceProvider;
import com.zireck.remotecraft.infrastructure.tool.NetworkConnectionlessDatagramTransmitter;
import com.zireck.remotecraft.infrastructure.tool.NetworkConnectionlessTransmitter;
import com.zireck.remotecraft.infrastructure.validation.NetworkInterfaceValidator;
import com.zireck.remotecraft.infrastructure.validation.ServerMessageValidator;
import dagger.Module;
import dagger.Provides;
import java.net.DatagramSocket;
import java.net.SocketException;
import javax.inject.Singleton;

@Module public class NetworkModule {

  public NetworkModule() {

  }

  @Provides @Singleton DatagramSocket provideDatagramSocket() {
    // TODO use Optional
    DatagramSocket datagramSocket;
    try {
      datagramSocket = new DatagramSocket();
    } catch (SocketException e) {
      datagramSocket = null;
    }

    return datagramSocket;
  }

  @Provides @Singleton NetworkConnectionlessTransmitter provideNetworkConnectionlessTransmitter(
      DatagramSocket datagramSocket) {
    return new NetworkConnectionlessDatagramTransmitter(datagramSocket);
  }

  @Provides @Singleton ServerSearchSettings provideServerSearchSettings() {
    return new ServerSearchSettings.Builder().build();
  }

  @Provides @Singleton NetworkProvider provideNetworkProvider(
      NetworkDataProvider networkDataProvider) {
    return networkDataProvider;
  }

  @Provides @Singleton NetworkInterfaceProvider provideNetworkInterfaceProvider() {
    return new AndroidNetworkInterfaceProvider();
  }

  @Provides @Singleton BroadcastAddressProvider provideBroadcastAddressProvider(
      NetworkInterfaceProvider networkInterfaceProvider,
      NetworkInterfaceValidator networkInterfaceValidator) {
    return new AndroidBroadcastAddressProvider(networkInterfaceProvider, networkInterfaceValidator);
  }

  @Provides @Singleton ServerSearchManager provideServerSearchManager(
      ServerSearchSettings serverSearchSettings,
      NetworkConnectionlessTransmitter networkConnectionlessTransmitter,
      BroadcastAddressProvider broadcastAddressProvider,
      ProtocolMessageComposer protocolMessageComposer, MessageJsonMapper messageJsonMapper,
      ServerProtocolMapper serverProtocolMapper, ServerMessageValidator serverValidator) {
    return new ServerSearchManager(serverSearchSettings, networkConnectionlessTransmitter,
        broadcastAddressProvider, protocolMessageComposer, messageJsonMapper, serverProtocolMapper,
        serverValidator);
  }

  @Provides @Singleton ProtocolMessageComposer provideNetworkProtocolManager() {
    return new ProtocolMessageComposer();
  }
}
