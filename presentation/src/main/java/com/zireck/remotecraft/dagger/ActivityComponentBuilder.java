package com.zireck.remotecraft.dagger;

import com.zireck.remotecraft.dagger.components.ActivityComponent;
import com.zireck.remotecraft.dagger.modules.activitymodules.ActivityModule;

public interface ActivityComponentBuilder<M extends ActivityModule, C extends ActivityComponent> {
  ActivityComponentBuilder<M, C> activityModule(M activityModule);
  C build();
}
