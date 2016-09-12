package com.zireck.remotecraft.domain.repository;

import rx.Observable;

public interface ReceiversManager {
  Observable<Integer> getWifiState();
}
