package com.zireck.remotecraft.dagger.components;

import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.dagger.modules.ActivityModule;
import com.zireck.remotecraft.dagger.modules.InteractorsModule;
import com.zireck.remotecraft.dagger.modules.PermissionsModule;
import com.zireck.remotecraft.dagger.modules.PresentersModule;
import com.zireck.remotecraft.dagger.modules.UiModule;
import com.zireck.remotecraft.view.activity.ServerSearchActivity;
import dagger.Component;

@PerActivity
@Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class,
        UiModule.class,
        InteractorsModule.class,
        PresentersModule.class,
        PermissionsModule.class
    }
)
public interface ServerSearchComponent extends ActivityComponent {
  void inject(ServerSearchActivity serverSearchActivity);
}
