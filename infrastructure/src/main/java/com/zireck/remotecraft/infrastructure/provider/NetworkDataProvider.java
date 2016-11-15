package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.WorldEntityDataMapper;
import com.zireck.remotecraft.infrastructure.manager.NetworkDiscoveryManager;
import javax.inject.Inject;
import rx.Observable;

public class NetworkDataProvider implements NetworkProvider {

  @Inject Context context;
  private NetworkDiscoveryManager networkDiscoveryManager;
  private WorldEntityDataMapper worldEntityDataMapper;

  @Inject public NetworkDataProvider(NetworkDiscoveryManager networkDiscoveryManager,
      WorldEntityDataMapper worldEntityDataMapper) {
    this.networkDiscoveryManager = networkDiscoveryManager;
    this.worldEntityDataMapper = worldEntityDataMapper;
  }

  @Override public Observable<World> searchWorld() {
    Observable<WorldEntity> worldEntityObservable = networkDiscoveryManager.discoverWorld();
    return worldEntityObservable.map(worldEntityDataMapper::transform);
  }
}
