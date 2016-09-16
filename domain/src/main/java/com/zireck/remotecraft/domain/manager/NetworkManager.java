package com.zireck.remotecraft.domain.manager;

import com.zireck.remotecraft.domain.World;
import rx.Observable;

public interface NetworkManager {
  Observable<World> searchWorld();
}
