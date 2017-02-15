package com.zireck.remotecraft;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.zireck.remotecraft.dagger.components.ApplicationComponent;
import com.zireck.remotecraft.dagger.components.DaggerApplicationComponent;
import com.zireck.remotecraft.dagger.modules.ApplicationModule;
import timber.log.Timber;

public class RemotecraftApp extends Application implements Application.ActivityLifecycleCallbacks {

  private ApplicationComponent applicationComponent;
  private Activity currentActivity;

  @Override public void onCreate() {
    super.onCreate();
    this.registerActivityLifecycleCallbacks(this);
    this.initLogger();
    this.initializeInjector();
  }

  private void initLogger() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  private void initializeInjector() {
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  @Override public void onActivityCreated(Activity activity, Bundle bundle) {
    currentActivity = activity;
  }

  @Override public void onActivityStarted(Activity activity) {
    currentActivity = activity;
  }

  @Override public void onActivityResumed(Activity activity) {
    currentActivity = activity;
  }

  @Override public void onActivityPaused(Activity activity) {

  }

  @Override public void onActivityStopped(Activity activity) {

  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

  }

  @Override public void onActivityDestroyed(Activity activity) {

  }

  public Activity getCurrentActivity() {
    return currentActivity;
  }
}
