package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import javax.inject.Inject;
import rx.Observable;

public class NetworkDataProvider implements NetworkProvider {

  @Inject Context context;

  @Inject public NetworkDataProvider() {

  }

  @Override public Observable<World> searchWorld() {
    return null;
  }
}
