package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import com.zireck.remotecraft.infrastructure.manager.NetworkDiscoveryManager;
import java.io.IOException;
import javax.inject.Inject;
import rx.Observable;

public class NetworkDataProvider implements NetworkProvider {

  @Inject Context context;
  private NetworkDiscoveryManager networkDiscoveryManager;

  @Inject public NetworkDataProvider(NetworkDiscoveryManager networkDiscoveryManager) {
    this.networkDiscoveryManager = networkDiscoveryManager;
  }

  @Override public Observable<World> searchWorld() {
    return Observable.create(subscriber -> {
      try {
        networkDiscoveryManager.sendDiscoveryRequest();
        WorldEntity worldEntity = networkDiscoveryManager.discover();
        World world = new World.Builder()
            .version(worldEntity.getVersion())
            .ssid(worldEntity.getSsid())
            .ip(worldEntity.getIp())
            .name(worldEntity.getName())
            .player(worldEntity.getPlayer())
            .build();

        subscriber.onNext(world);
      } catch (IOException e) {
        subscriber.onError(e);
      }
    });
  }
}
