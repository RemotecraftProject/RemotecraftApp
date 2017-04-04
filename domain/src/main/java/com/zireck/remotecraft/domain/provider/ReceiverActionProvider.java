package com.zireck.remotecraft.domain.provider;

import io.reactivex.Maybe;

public interface ReceiverActionProvider {
  Maybe<Integer> getWifiState();
}
