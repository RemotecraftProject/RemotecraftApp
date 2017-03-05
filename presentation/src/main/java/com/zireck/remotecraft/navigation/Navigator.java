package com.zireck.remotecraft.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.view.activity.ServerFoundActivity;
import com.zireck.remotecraft.view.activity.ServerSearchActivity;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {

  @Inject
  public Navigator() {

  }

  public <T extends Parcelable> void finishActivity(Activity activity, boolean isSuccess,
      String extraKey, T extra) {
    HashMap<String, T> extras = new HashMap<>();
    extras.put(extraKey, extra);
    finishActivity(activity, isSuccess, extras);
  }

  public <T extends Parcelable> void finishActivity(Activity activity, boolean isSuccess,
      Map<String, T> extras) {
    checkValidActivity(activity);

    Intent intent = new Intent();
    for (String key : extras.keySet()) {
      intent.putExtra(key, extras.get(key));
    }
    int result = isSuccess ? Activity.RESULT_OK : Activity.RESULT_CANCELED;
    activity.setResult(result, intent);
    finishActivity(activity);
  }

  public void finishActivity(Activity activity) {
    activity.finish();
  }

  public void navigateToServerSearchActivity(Context context) {
    Intent intentToLaunch = ServerSearchActivity.getCallingIntent(context);
    startActivity(context, intentToLaunch);
  }

  public void navigateToServerFoundActivity(Activity activity, final ServerModel serverModel) {
    checkValidActivity(activity);
    if (serverModel == null) {
      throw new IllegalArgumentException("Cannot navigate using a null ServerModel.");
    }

    Intent intentToLaunch = ServerFoundActivity.getCallingIntent(activity, serverModel);
    startActivityForResult(activity, intentToLaunch, RequestCode.SERVER_FOUND);
  }

  private void startActivity(Context context, Intent intent) {
    checkValidContext(context);
    context.startActivity(intent);
  }

  private void startActivityForResult(Activity activity, Intent intent, int requestCode) {
    checkValidActivity(activity);
    activity.startActivityForResult(intent, requestCode);
  }

  private void checkValidContext(Context context) {
    if (context == null) {
      throw new IllegalArgumentException("Context cannot be null.");
    }
  }

  private void checkValidActivity(Activity activity) {
    if (activity == null) {
      throw new IllegalArgumentException("Activity cannot be null.");
    }
  }

  public static final class RequestCode {
    public static final int SERVER_FOUND = 1;
  }
}
