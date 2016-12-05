package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.WorldEntityDataMapper;
import com.zireck.remotecraft.infrastructure.manager.ServerSearchManager;
import javax.inject.Inject;
import rx.Observable;

public class NetworkDataProvider implements NetworkProvider {

  @Inject Context context;
  private ServerSearchManager serverSearchManager;
  private WorldEntityDataMapper worldEntityDataMapper;

  @Inject public NetworkDataProvider(ServerSearchManager serverSearchManager,
      WorldEntityDataMapper worldEntityDataMapper) {
    this.serverSearchManager = serverSearchManager;
    this.worldEntityDataMapper = worldEntityDataMapper;
  }

  @Override public Observable<World> searchWorld() {
    Observable<WorldEntity> worldEntityObservable = serverSearchManager.searchWorld();
    return worldEntityObservable.map(worldEntityDataMapper::transform);
  }
}
