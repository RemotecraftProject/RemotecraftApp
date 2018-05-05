package com.remotecraft.app.dagger.modules.activitymodules;

import com.remotecraft.app.dagger.ActivityComponentBuilder;
import com.remotecraft.app.dagger.ActivityKey;
import com.remotecraft.app.dagger.components.ServerFoundComponent;
import com.remotecraft.app.dagger.components.ServerSearchComponent;
import com.remotecraft.app.dagger.components.SplashComponent;
import com.remotecraft.app.view.activity.ServerFoundActivity;
import com.remotecraft.app.view.activity.ServerSearchActivity;
import com.remotecraft.app.view.activity.SplashActivity;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(
    subcomponents = {
        SplashComponent.class,
        ServerSearchComponent.class,
        ServerFoundComponent.class
    })
public abstract class ActivityBindingModule {

  @Binds
  @IntoMap
  @ActivityKey(SplashActivity.class)
  public abstract ActivityComponentBuilder splashActivityComponentBuilder(
      SplashComponent.Builder impl);

  @Binds
  @IntoMap
  @ActivityKey(ServerSearchActivity.class)
  public abstract ActivityComponentBuilder serverSearchActivityComponentBuilder(
      ServerSearchComponent.Builder impl);

  @Binds
  @IntoMap
  @ActivityKey(ServerFoundActivity.class)
  public abstract ActivityComponentBuilder serverFoundActivityComponentBuilder(
      ServerFoundComponent.Builder impl);
}
