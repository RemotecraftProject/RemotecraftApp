package com.zireck.remotecraft.domain.manager;

import rx.Observable;

public interface ReceiversManager {
  Observable<Integer> getWifiState();
}
