package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.entity.mapper.WorldEntityDataMapper;
import com.zireck.remotecraft.infrastructure.manager.ServerSearchManager;
import io.reactivex.Maybe;
import javax.inject.Inject;

public class NetworkDataProvider implements NetworkProvider {

  @Inject Context context;
  private ServerSearchManager serverSearchManager;
  private WorldEntityDataMapper worldEntityDataMapper;

  @Inject public NetworkDataProvider(ServerSearchManager serverSearchManager,
      WorldEntityDataMapper worldEntityDataMapper) {
    this.serverSearchManager = serverSearchManager;
    this.worldEntityDataMapper = worldEntityDataMapper;
  }

  @Override public Maybe<World> searchWorld() {
    return serverSearchManager.searchWorld().map(worldEntityDataMapper::transform);
  }

  @Override public Maybe<World> searchWorld(String ipAddress) {
    return serverSearchManager.searchWorld(ipAddress).map(worldEntityDataMapper::transform);
  }
}
