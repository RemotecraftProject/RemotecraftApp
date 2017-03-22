package com.zireck.remotecraft.dagger.components;

import com.zireck.remotecraft.dagger.ActivityComponentBuilder;
import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.dagger.modules.activitymodules.SplashModule;
import com.zireck.remotecraft.view.activity.SplashActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(
    modules = {
        SplashModule.class
    })
public interface SplashComponent extends ActivityComponent<SplashActivity> {

  @Subcomponent.Builder
  interface Builder extends ActivityComponentBuilder<SplashModule, SplashComponent> {

  }
}
