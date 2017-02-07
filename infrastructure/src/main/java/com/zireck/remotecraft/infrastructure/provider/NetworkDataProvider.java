package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.zireck.remotecraft.infrastructure.manager.ServerSearchManager;
import io.reactivex.Maybe;
import javax.inject.Inject;

public class NetworkDataProvider implements NetworkProvider {

  @Inject Context context;
  private ServerSearchManager serverSearchManager;
  private ServerEntityDataMapper serverEntityDataMapper;

  @Inject public NetworkDataProvider(ServerSearchManager serverSearchManager,
      ServerEntityDataMapper serverEntityDataMapper) {
    this.serverSearchManager = serverSearchManager;
    this.serverEntityDataMapper = serverEntityDataMapper;
  }

  @Override public Maybe<Server> searchServer() {
    return serverSearchManager.searchServer().map(serverEntityDataMapper::transform);
  }

  @Override public Maybe<Server> searchServer(String ipAddress) {
    return serverSearchManager.searchServer(ipAddress).map(serverEntityDataMapper::transform);
  }
}
