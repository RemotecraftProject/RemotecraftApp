package com.zireck.remotecraft.dagger.modules.activitymodules;

import com.zireck.remotecraft.dagger.ActivityComponentBuilder;
import com.zireck.remotecraft.dagger.ActivityKey;
import com.zireck.remotecraft.dagger.components.ServerFoundComponent;
import com.zireck.remotecraft.dagger.components.ServerSearchComponent;
import com.zireck.remotecraft.dagger.components.SplashComponent;
import com.zireck.remotecraft.view.activity.ServerFoundActivity;
import com.zireck.remotecraft.view.activity.ServerSearchActivity;
import com.zireck.remotecraft.view.activity.SplashActivity;
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
