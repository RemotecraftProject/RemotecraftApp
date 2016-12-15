package com.zireck.remotecraft.domain.provider;

import io.reactivex.Maybe;

public interface ReceiversProvider {
  Maybe<Integer> getWifiState();
}
