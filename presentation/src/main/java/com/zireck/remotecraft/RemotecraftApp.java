package com.zireck.remotecraft;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.VisibleForTesting;
import com.zireck.remotecraft.dagger.components.ApplicationComponent;
import com.zireck.remotecraft.dagger.components.DaggerApplicationComponent;
import com.zireck.remotecraft.dagger.modules.ApplicationModule;
import com.zireck.remotecraft.tools.ActivityTracker;
import timber.log.Timber;

public class RemotecraftApp extends Application {

  private ApplicationComponent applicationComponent;
  private ActivityTracker activityTracker;

  @Override public void onCreate() {
    super.onCreate();

    this.initLogger();
    this.initializeInjector();
    this.initActivityTracker();
  }

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  @VisibleForTesting
  public void setApplicationComponent(ApplicationComponent applicationComponent) {
    this.applicationComponent = applicationComponent;
  }

  public Activity getCurrentActivity() {
    return activityTracker != null ? activityTracker.getCurrentActivity() : null;
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

  private void initActivityTracker() {
    activityTracker = new ActivityTracker();
    registerActivityLifecycleCallbacks(activityTracker);
  }
}
