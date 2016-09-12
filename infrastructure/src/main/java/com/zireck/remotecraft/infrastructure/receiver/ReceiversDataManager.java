package com.zireck.remotecraft.infrastructure.receiver;

import android.content.Context;
import com.f2prateek.rx.receivers.wifi.RxWifiManager;
import com.zireck.remotecraft.domain.repository.ReceiversManager;
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
