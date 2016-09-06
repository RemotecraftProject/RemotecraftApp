package com.zireck.remotecraft.domain.repository;

import com.zireck.remotecraft.domain.World;
import rx.Observable;

public interface NetworkRepository {
  Observable<World> searchWorld();
}
