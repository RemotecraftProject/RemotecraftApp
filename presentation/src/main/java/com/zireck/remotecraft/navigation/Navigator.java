package com.zireck.remotecraft.navigation;

import android.content.Context;
import android.content.Intent;
import com.zireck.remotecraft.model.WorldModel;
import com.zireck.remotecraft.view.activity.SearchWorldActivity;
import com.zireck.remotecraft.view.activity.WorldFoundActivity;
import javax.inject.Inject;
import javax.inject.Singleton;
import timber.log.Timber;

@Singleton
public class Navigator {

  @Inject
  public Navigator() {

  }

  public void navigateToSearchActivity(Context context) {
    if (context == null) {
      Timber.e("Context cannot be null.");
      return;
    }

    Intent intentToLaunch = SearchWorldActivity.getCallingIntent(context);
    context.startActivity(intentToLaunch);
  }

  public void navigateToWorldFoundActivity(Context context, WorldModel worldModel) {
    if (context == null) {
      Timber.e("Context cannot be null.");
      return;
    }

    if (worldModel == null) {
      Timber.e("Cannot navigate using a null WorldModel.");
      return;
    }

    Intent intentToLaunch = WorldFoundActivity.getCallingIntent(context, worldModel);
    context.startActivity(intentToLaunch);
  }
}
