package com.zireck.remotecraft.dagger.modules.activitymodules;

import com.zireck.remotecraft.view.activity.SplashActivity;
import dagger.Module;

@Module public class SplashModule extends ActivityModule<SplashActivity> {

  public SplashModule(SplashActivity activity) {
    super(activity);
  }
}
