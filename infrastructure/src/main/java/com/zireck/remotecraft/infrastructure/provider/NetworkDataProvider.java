package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.entity.NetworkAddressEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.NetworkAddressEntityDataMapper;
import com.zireck.remotecraft.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.zireck.remotecraft.infrastructure.manager.ServerSearchManager;
import io.reactivex.Observable;
import javax.inject.Inject;

public class NetworkDataProvider implements NetworkProvider {

  @Inject Context context;
  private final ServerSearchManager serverSearchManager;
  private final ServerEntityDataMapper serverEntityDataMapper;
  private final NetworkAddressEntityDataMapper networkAddressEntityDataMapper;

  @Inject public NetworkDataProvider(ServerSearchManager serverSearchManager,
      ServerEntityDataMapper serverEntityDataMapper,
      NetworkAddressEntityDataMapper networkAddressEntityDataMapper) {
    this.serverSearchManager = serverSearchManager;
    this.serverEntityDataMapper = serverEntityDataMapper;
    this.networkAddressEntityDataMapper = networkAddressEntityDataMapper;
  }

  @Override public Observable<Server> searchServer() {
    return serverSearchManager.searchServer().map(serverEntityDataMapper::transform);
  }

  @Override public Observable<Server> searchServer(NetworkAddress networkAddress) {
    NetworkAddressEntity networkAddressEntity =
        networkAddressEntityDataMapper.transformInverse(networkAddress);
    return serverSearchManager.searchServer(networkAddressEntity)
        .map(serverEntityDataMapper::transform);
  }
}
