package com.zireck.remotecraft.dagger.modules;

import com.google.gson.Gson;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.manager.NetworkDiscoveryManager;
import com.zireck.remotecraft.infrastructure.manager.NetworkInterfaceManager;
import com.zireck.remotecraft.infrastructure.manager.NetworkProtocolManager;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerMapper;
import com.zireck.remotecraft.infrastructure.provider.NetworkDataProvider;
import com.zireck.remotecraft.infrastructure.validation.ServerMessageValidator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class NetworkModule {

  public NetworkModule() {

  }

  @Provides @Singleton NetworkProvider provideNetworkProvider(
      NetworkDataProvider networkDataProvider) {
    return networkDataProvider;
  }

  @Provides @Singleton NetworkInterfaceManager provideNetworkInterfaceManager() {
    return new NetworkInterfaceManager();
  }

  @Provides @Singleton NetworkDiscoveryManager provideNetworkDiscoveryManager(
      NetworkInterfaceManager networkInterfaceManager,
      NetworkProtocolManager networkProtocolManager, MessageJsonMapper messageJsonMapper,
      ServerMapper serverMapper, ServerMessageValidator serverValidator) {
    return new NetworkDiscoveryManager(networkInterfaceManager, networkProtocolManager,
        messageJsonMapper, serverMapper, serverValidator);
  }

  @Provides @Singleton NetworkProtocolManager provideNetworkProtocolManager(Gson gson) {
    return new NetworkProtocolManager(gson);
  }
}
