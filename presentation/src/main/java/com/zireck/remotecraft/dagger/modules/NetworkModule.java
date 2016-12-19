package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.manager.NetworkInterfaceManager;
import com.zireck.remotecraft.infrastructure.manager.NetworkProtocolManager;
import com.zireck.remotecraft.infrastructure.manager.ServerSearchManager;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerMapper;
import com.zireck.remotecraft.infrastructure.provider.NetworkDataProvider;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import com.zireck.remotecraft.infrastructure.tool.NetworkConnectionlessTransmitter;
import com.zireck.remotecraft.infrastructure.tool.NetworkConnectionlessDatagramTransmitter;
import com.zireck.remotecraft.infrastructure.validation.ServerMessageValidator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class NetworkModule {

  public NetworkModule() {

  }

  @Provides @Singleton
  NetworkConnectionlessTransmitter provideNetworkConnectionlessTransmitter() {
    return new NetworkConnectionlessDatagramTransmitter();
  }

  @Provides @Singleton NetworkProvider provideNetworkProvider(
      NetworkDataProvider networkDataProvider) {
    return networkDataProvider;
  }

  @Provides @Singleton NetworkInterfaceManager provideNetworkInterfaceManager() {
    return new NetworkInterfaceManager();
  }

  @Provides @Singleton ServerSearchManager provideServerSearchManager(
          NetworkConnectionlessTransmitter networkConnectionlessTransmitter, NetworkInterfaceManager networkInterfaceManager,
          NetworkProtocolManager networkProtocolManager, MessageJsonMapper messageJsonMapper,
          ServerMapper serverMapper, ServerMessageValidator serverValidator) {
    return new ServerSearchManager(networkConnectionlessTransmitter, networkInterfaceManager,
        networkProtocolManager, messageJsonMapper, serverMapper, serverValidator);
  }

  @Provides @Singleton NetworkProtocolManager provideNetworkProtocolManager(
      JsonSerializer jsonSerializer) {
    return new NetworkProtocolManager(jsonSerializer);
  }
}
