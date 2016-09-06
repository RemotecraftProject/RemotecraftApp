package com.zireck.remotecraft.data.repository;

import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.repository.NetworkRepository;
import javax.inject.Inject;
import rx.Observable;

public class NetworkDataRepository implements NetworkRepository {

  @Inject
  public NetworkDataRepository() {

  }

  @Override public Observable<World> searchWorld() {
    return null;
  }
}
