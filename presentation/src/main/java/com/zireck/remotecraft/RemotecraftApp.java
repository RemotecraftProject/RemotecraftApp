package com.zireck.remotecraft;

import android.app.Application;
import com.zireck.remotecraft.dagger.components.ApplicationComponent;
import com.zireck.remotecraft.dagger.components.DaggerApplicationComponent;
import com.zireck.remotecraft.dagger.modules.ApplicationModule;
import timber.log.Timber;

public class RemotecraftApp extends Application {
  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();
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
}
