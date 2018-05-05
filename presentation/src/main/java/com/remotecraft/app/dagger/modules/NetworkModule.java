package com.remotecraft.app.dagger.modules;

import com.remotecraft.app.BuildConfig;
import com.remotecraft.app.domain.provider.NetworkActionProvider;
import com.remotecraft.app.infrastructure.manager.ServerSearchManager;
import com.remotecraft.app.infrastructure.network.NetworkConnectionlessMockTransmitter;
import com.remotecraft.app.infrastructure.network.NetworkConnectionlessTransmitter;
import com.remotecraft.app.infrastructure.network.NetworkDatagramTransmitter;
import com.remotecraft.app.infrastructure.protocol.ProtocolMessageComposer;
import com.remotecraft.app.infrastructure.protocol.mapper.MessageJsonMapper;
import com.remotecraft.app.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.remotecraft.app.infrastructure.provider.NetworkDataProvider;
import com.remotecraft.app.infrastructure.provider.ServerSearchSettings;
import com.remotecraft.app.infrastructure.provider.broadcastaddress.AndroidBroadcastAddressProvider;
import com.remotecraft.app.infrastructure.provider.broadcastaddress.BroadcastAddressProvider;
import com.remotecraft.app.infrastructure.provider.networkinterface.AndroidNetworkInterfaceProvider;
import com.remotecraft.app.infrastructure.provider.networkinterface.NetworkInterfaceProvider;
import com.remotecraft.app.infrastructure.validation.NetworkInterfaceValidator;
import com.remotecraft.app.infrastructure.validation.ServerMessageValidator;
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
      NetworkDatagramTransmitter networkDatagramTransmitter,
      NetworkConnectionlessMockTransmitter networkConnectionlessMockTransmitter) {
    if (BuildConfig.IS_MOCK) {
      return networkConnectionlessMockTransmitter;
    } else {
      return networkDatagramTransmitter;
    }
  }

  @Provides @Singleton NetworkDatagramTransmitter provideNetworkDatagramTransmitter(
      DatagramSocket datagramSocket) {
    return new NetworkDatagramTransmitter(datagramSocket);
  }

  @Provides @Singleton ServerSearchSettings provideServerSearchSettings() {
    return new ServerSearchSettings.Builder().build();
  }

  @Provides @Singleton NetworkActionProvider provideNetworkProvider(
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
