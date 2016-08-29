package com.zireck.remotecraft.navigation;

import android.content.Context;
import android.content.Intent;
import com.zireck.remotecraft.view.activity.MainActivity;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {

  @Inject
  public Navigator() {

  }

  public void navigateToMainActivity(Context context) {
    if (context == null) {
      return;
    }

    Intent intentToLaunch = MainActivity.getCallingIntent(context);
    context.startActivity(intentToLaunch);
  }
}
