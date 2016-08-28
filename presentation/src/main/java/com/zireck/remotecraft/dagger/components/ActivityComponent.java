package com.zireck.remotecraft.dagger.components;

import android.app.Activity;
import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.dagger.modules.ActivityModule;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  // Exposed to subgraph
  Activity activity();
}
