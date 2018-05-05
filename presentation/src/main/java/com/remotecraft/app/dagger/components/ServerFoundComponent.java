package com.remotecraft.app.dagger.components;

import com.remotecraft.app.dagger.ActivityComponentBuilder;
import com.remotecraft.app.dagger.qualifiers.PerActivity;
import com.remotecraft.app.dagger.modules.InteractorsModule;
import com.remotecraft.app.dagger.modules.PresentersModule;
import com.remotecraft.app.dagger.modules.activitymodules.ServerFoundModule;
import com.remotecraft.app.view.activity.ServerFoundActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(
    modules = {
        ServerFoundModule.class,
        InteractorsModule.class,
        PresentersModule.class
    }
)
public interface ServerFoundComponent extends ActivityComponent<ServerFoundActivity> {

  @Subcomponent.Builder
  interface Builder extends ActivityComponentBuilder<ServerFoundModule, ServerFoundComponent> {

  }
}
