package com.zireck.remotecraft.domain.provider;

import rx.Observable;

public interface ReceiversProvider {
  Observable<Integer> getWifiState();
}
