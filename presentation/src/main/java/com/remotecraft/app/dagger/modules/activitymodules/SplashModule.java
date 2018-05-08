package com.remotecraft.app.dagger.modules.activitymodules;

import com.remotecraft.app.view.activity.SplashActivity;
import dagger.Module;

@Module
public class SplashModule extends ActivityModule<SplashActivity> {

  public SplashModule(SplashActivity activity) {
    super(activity);
  }
}
