package com.zireck.remotecraft.infrastructure.manager;

import android.content.Context;
import com.f2prateek.rx.receivers.wifi.RxWifiManager;
import com.zireck.remotecraft.domain.manager.ReceiversManager;
import javax.inject.Inject;
import rx.Observable;

public class ReceiversDataManager implements ReceiversManager {

  @Inject Context context;

  @Inject public ReceiversDataManager() {

  }

  @Override public Observable<Integer> getWifiState() {
    return RxWifiManager.wifiStateChanges(context);
  }
}
