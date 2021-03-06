package com.remotecraft.app.dagger.modules.activitymodules;

import android.app.Activity;
import android.content.res.Resources;
import com.remotecraft.app.dagger.qualifiers.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ActivityModule<T extends Activity> {

  private final T activity;

  public ActivityModule(T activity) {
    this.activity = activity;
  }

  @Provides Activity provideActivity() {
    return this.activity;
  }

  @Provides @PerActivity Resources provideResources() {
    return this.activity.getResources();
  }
}
