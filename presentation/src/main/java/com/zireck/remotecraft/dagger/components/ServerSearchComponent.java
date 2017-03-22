package com.zireck.remotecraft.dagger.components;

import com.zireck.remotecraft.dagger.ActivityComponentBuilder;
import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.dagger.modules.InteractorsModule;
import com.zireck.remotecraft.dagger.modules.PermissionsModule;
import com.zireck.remotecraft.dagger.modules.PresentersModule;
import com.zireck.remotecraft.dagger.modules.activitymodules.ServerSearchModule;
import com.zireck.remotecraft.dagger.modules.UiModule;
import com.zireck.remotecraft.view.activity.ServerSearchActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(
    modules = {
        ServerSearchModule.class,
        UiModule.class,
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
