package com.zireck.remotecraft.dagger.components;

import com.zireck.remotecraft.dagger.ActivityComponentBuilder;
import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.dagger.modules.InteractorsModule;
import com.zireck.remotecraft.dagger.modules.PresentersModule;
import com.zireck.remotecraft.dagger.modules.activitymodules.ServerFoundModule;
import com.zireck.remotecraft.dagger.modules.UiModule;
import com.zireck.remotecraft.view.activity.ServerFoundActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(
    modules = {
        ServerFoundModule.class,
        UiModule.class,
        InteractorsModule.class,
        PresentersModule.class
    }
)
public interface ServerFoundComponent extends ActivityComponent<ServerFoundActivity> {

  @Subcomponent.Builder
  interface Builder extends ActivityComponentBuilder<ServerFoundModule, ServerFoundComponent> {

  }
}
