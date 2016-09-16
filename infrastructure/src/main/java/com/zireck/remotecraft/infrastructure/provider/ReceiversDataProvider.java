package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import com.f2prateek.rx.receivers.wifi.RxWifiManager;
import com.zireck.remotecraft.domain.provider.ReceiversProvider;
import javax.inject.Inject;
import rx.Observable;

public class ReceiversDataProvider implements ReceiversProvider {

  @Inject Context context;

  @Inject public ReceiversDataProvider() {

  }

  @Override public Observable<Integer> getWifiState() {
    return RxWifiManager.wifiStateChanges(context);
  }
}
