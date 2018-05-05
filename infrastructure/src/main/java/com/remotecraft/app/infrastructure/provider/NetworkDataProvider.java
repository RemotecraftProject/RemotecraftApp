package com.remotecraft.app.infrastructure.provider;

import android.content.Context;
import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.provider.NetworkActionProvider;
import com.remotecraft.app.infrastructure.entity.NetworkAddressEntity;
import com.remotecraft.app.infrastructure.entity.mapper.NetworkAddressEntityDataMapper;
import com.remotecraft.app.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.remotecraft.app.infrastructure.manager.ServerSearchManager;
import io.reactivex.Observable;
import javax.inject.Inject;

public class NetworkDataProvider implements NetworkActionProvider {

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
