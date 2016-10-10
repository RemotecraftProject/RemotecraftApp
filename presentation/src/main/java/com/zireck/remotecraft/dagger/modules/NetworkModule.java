package com.zireck.remotecraft.dagger.modules;

import com.google.gson.Gson;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.manager.NetworkDiscoveryManager;
import com.zireck.remotecraft.infrastructure.manager.NetworkInterfaceManager;
import com.zireck.remotecraft.infrastructure.manager.NetworkProtocolManager;
import com.zireck.remotecraft.infrastructure.manager.NetworkResponseManager;
import com.zireck.remotecraft.infrastructure.provider.NetworkDataProvider;
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

  @Provides @Singleton NetworkResponseManager provideNetworkResponseManager() {
    return new NetworkResponseManager();
  }

  @Provides @Singleton NetworkDiscoveryManager provideNetworkDiscoveryManager(
      Gson gson,
      NetworkInterfaceManager networkInterfaceManager,
      NetworkResponseManager networkResponseManager,
      NetworkProtocolManager networkProtocolManager) {
    return new NetworkDiscoveryManager(gson, networkInterfaceManager, networkResponseManager,
        networkProtocolManager);
  }

  @Provides @Singleton NetworkProtocolManager provideNetworkProtocolManager(Gson gson) {
    return new NetworkProtocolManager(gson);
  }
}
