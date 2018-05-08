package com.remotecraft.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import com.remotecraft.app.dagger.ActivityComponentBuilder;
import com.remotecraft.app.dagger.HasActivitySubcomponentBuilders;
import com.remotecraft.app.dagger.components.ApplicationComponent;
import com.remotecraft.app.dagger.components.DaggerApplicationComponent;
import com.remotecraft.app.dagger.modules.ApplicationModule;
import com.remotecraft.app.tools.ActivityTracker;
import java.util.Map;
import javax.inject.Inject;
import timber.log.Timber;

public class RemotecraftApp extends Application implements HasActivitySubcomponentBuilders {

  @Inject Map<Class<? extends Activity>, ActivityComponentBuilder> activityComponentBuilders;
  private ApplicationComponent applicationComponent;
  private ActivityTracker activityTracker;

  @Override
  public void onCreate() {
    super.onCreate();

    this.initLogger();
    this.initializeInjector();
    this.initActivityTracker();
  }

  public static HasActivitySubcomponentBuilders get(Context context) {
    return (HasActivitySubcomponentBuilders) context.getApplicationContext();
  }

  @Override
  public ActivityComponentBuilder getActivityComponentBuilder(
      Class<? extends Activity> activityClass) {
    return activityComponentBuilders.get(activityClass);
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
    applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
    applicationComponent.inject(this);
  }

  private void initActivityTracker() {
    activityTracker = new ActivityTracker();
    registerActivityLifecycleCallbacks(activityTracker);
  }
}
