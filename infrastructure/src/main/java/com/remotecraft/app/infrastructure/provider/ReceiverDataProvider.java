package com.remotecraft.app.infrastructure.provider;

import android.content.Context;
import com.remotecraft.app.domain.provider.ReceiverActionProvider;
import io.reactivex.Maybe;
import javax.inject.Inject;

public class ReceiverDataProvider implements ReceiverActionProvider {

  @Inject Context context;

  @Inject
  public ReceiverDataProvider() {

  }

  @Override
  public Maybe<Integer> getWifiState() {
    // TODO wait until RxReceivers library updates to RxJava 2
    //return RxWifiManager.wifiStateChanges(context);
    return Maybe.empty();
  }
}
