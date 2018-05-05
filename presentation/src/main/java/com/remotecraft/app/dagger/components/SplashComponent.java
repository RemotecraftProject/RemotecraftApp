package com.remotecraft.app.dagger.components;

import com.remotecraft.app.dagger.ActivityComponentBuilder;
import com.remotecraft.app.dagger.qualifiers.PerActivity;
import com.remotecraft.app.dagger.modules.activitymodules.SplashModule;
import com.remotecraft.app.view.activity.SplashActivity;
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
