package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.manager.NetworkDiscoveryManager;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;

public class NetworkDataProvider implements NetworkProvider {

  @Inject Context context;
  private NetworkDiscoveryManager networkDiscoveryManager;

  @Inject public NetworkDataProvider(NetworkDiscoveryManager networkDiscoveryManager) {
    this.networkDiscoveryManager = networkDiscoveryManager;
  }

  @Override public Observable<World> searchWorld() {
    return Observable.create(new Observable.OnSubscribe<World>() {

      @Override public void call(Subscriber<? super World> subscriber) {
        subscriber.onNext(new World.Builder().name("mock").build());
      }
    });
  }
}
