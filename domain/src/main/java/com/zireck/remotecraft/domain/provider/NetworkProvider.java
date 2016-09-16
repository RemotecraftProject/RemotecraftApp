package com.zireck.remotecraft.domain.provider;

import com.zireck.remotecraft.domain.World;
import rx.Observable;

public interface NetworkProvider {
  Observable<World> searchWorld();
}
