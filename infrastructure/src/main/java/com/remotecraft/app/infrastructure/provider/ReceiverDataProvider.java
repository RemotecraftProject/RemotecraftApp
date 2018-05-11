package com.remotecraft.app.infrastructure.provider;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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

    return Maybe.create(emitter -> {
      WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
      if (wifiManager == null) {
        emitter.onError(new NullPointerException());
        return;
      }
      int numberOfLevels = 4;
      WifiInfo connectionInfo = wifiManager.getConnectionInfo();
      if (connectionInfo == null) {
        emitter.onError(new NullPointerException());
        return;
      }
      int wifiStrenght = WifiManager.calculateSignalLevel(connectionInfo.getRssi(), numberOfLevels);
      emitter.onSuccess(wifiStrenght);
      emitter.onComplete();
    });
  }
}
