package com.zireck.remotecraft.navigation;

import android.content.Context;
import android.content.Intent;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.view.activity.SearchServerActivity;
import com.zireck.remotecraft.view.activity.ServerFoundActivity;
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

    Intent intentToLaunch = SearchServerActivity.getCallingIntent(context);
    context.startActivity(intentToLaunch);
  }

  public void navigateToServerFoundActivity(Context context, ServerModel serverModel) {
    if (context == null) {
      Timber.e("Context cannot be null.");
      return;
    }

    if (serverModel == null) {
      Timber.e("Cannot navigate using a null ServerModel.");
      return;
    }

    Intent intentToLaunch = ServerFoundActivity.getCallingIntent(context, serverModel);
    context.startActivity(intentToLaunch);
  }
}
