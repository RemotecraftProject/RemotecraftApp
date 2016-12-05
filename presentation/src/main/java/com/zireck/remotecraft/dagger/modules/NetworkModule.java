package com.zireck.remotecraft.dagger.modules;

import com.google.gson.Gson;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.manager.NetworkInterfaceManager;
import com.zireck.remotecraft.infrastructure.manager.NetworkProtocolManager;
import com.zireck.remotecraft.infrastructure.manager.ServerSearchManager;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerMapper;
import com.zireck.remotecraft.infrastructure.provider.NetworkDataProvider;
import com.zireck.remotecraft.infrastructure.validation.ServerMessageValidator;
import dagger.Module;
import dagger.Provides;
import java.net.DatagramSocket;
import java.net.SocketException;
import javax.inject.Singleton;
import timber.log.Timber;

@Module public class NetworkModule {

  public NetworkModule() {

  }

  @Provides @Singleton DatagramSocket provideDatagramSocket() {
    DatagramSocket datagramSocket = null;

    try {
      datagramSocket = new DatagramSocket();
    } catch (SocketException e) {
      Timber.e("Error instantiating DatagramSocket");
      Timber.e(e.getMessage());
    }

    return datagramSocket;
  }

  @Provides @Singleton NetworkProvider provideNetworkProvider(
      NetworkDataProvider networkDataProvider) {
    return networkDataProvider;
  }

  @Provides @Singleton NetworkInterfaceManager provideNetworkInterfaceManager() {
    return new NetworkInterfaceManager();
  }

  @Provides @Singleton ServerSearchManager provideServerSearchManager(DatagramSocket datagramSocket,
      NetworkInterfaceManager networkInterfaceManager,
      NetworkProtocolManager networkProtocolManager, MessageJsonMapper messageJsonMapper,
      ServerMapper serverMapper, ServerMessageValidator serverValidator) {
    return new ServerSearchManager(datagramSocket, networkInterfaceManager, networkProtocolManager,
        messageJsonMapper, serverMapper, serverValidator);
  }

  @Provides @Singleton NetworkProtocolManager provideNetworkProtocolManager(Gson gson) {
    return new NetworkProtocolManager(gson);
  }
}
