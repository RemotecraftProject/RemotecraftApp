package com.zireck.remotecraft.infrastructure.manager;

import android.content.Context;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.manager.NetworkManager;
import javax.inject.Inject;
import rx.Observable;

public class NetworkDataManager implements NetworkManager {

  @Inject Context context;

  @Inject public NetworkDataManager() {

  }

  @Override public Observable<World> searchWorld() {
    return null;
  }
}
