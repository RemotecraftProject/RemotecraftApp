package com.remotecraft.app.dagger.components;

import com.remotecraft.app.dagger.ActivityComponentBuilder;
import com.remotecraft.app.dagger.qualifiers.PerActivity;
import com.remotecraft.app.dagger.modules.InteractorsModule;
import com.remotecraft.app.dagger.modules.PermissionsModule;
import com.remotecraft.app.dagger.modules.PresentersModule;
import com.remotecraft.app.dagger.modules.activitymodules.ServerSearchModule;
import com.remotecraft.app.view.activity.ServerSearchActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(
    modules = {
        ServerSearchModule.class,
        InteractorsModule.class,
        PresentersModule.class,
        PermissionsModule.class
    }
)
public interface ServerSearchComponent extends ActivityComponent<ServerSearchActivity> {

  @Subcomponent.Builder
  interface Builder extends ActivityComponentBuilder<ServerSearchModule, ServerSearchComponent> {

  }
}
