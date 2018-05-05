package com.remotecraft.app.dagger;

import com.remotecraft.app.dagger.components.ActivityComponent;
import com.remotecraft.app.dagger.modules.activitymodules.ActivityModule;

public interface ActivityComponentBuilder<M extends ActivityModule, C extends ActivityComponent> {
  ActivityComponentBuilder<M, C> activityModule(M activityModule);
  C build();
}
