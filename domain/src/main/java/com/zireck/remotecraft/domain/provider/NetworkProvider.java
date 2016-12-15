package com.zireck.remotecraft.domain.provider;

import com.zireck.remotecraft.domain.World;
import io.reactivex.Maybe;

public interface NetworkProvider {
  Maybe<World> searchWorld();
}
