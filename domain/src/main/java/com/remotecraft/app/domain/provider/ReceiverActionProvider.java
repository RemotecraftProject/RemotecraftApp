package com.remotecraft.app.domain.provider;

import io.reactivex.Maybe;

public interface ReceiverActionProvider {
  Maybe<Integer> getWifiState();
}
