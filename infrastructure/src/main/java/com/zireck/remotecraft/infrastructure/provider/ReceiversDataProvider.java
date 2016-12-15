package com.zireck.remotecraft.infrastructure.provider;

import android.content.Context;
import com.zireck.remotecraft.domain.provider.ReceiversProvider;
import io.reactivex.Maybe;
import javax.inject.Inject;

public class ReceiversDataProvider implements ReceiversProvider {

  @Inject Context context;

  @Inject public ReceiversDataProvider() {

  }

  @Override public Maybe<Integer> getWifiState() {
    // TODO wait until RxReceivers library updates to RxJava 2
    //return RxWifiManager.wifiStateChanges(context);
    return Maybe.empty();
  }
}
