package com.zireck.remotecraft.dagger.modules.activitymodules;

import com.zireck.remotecraft.view.activity.ServerSearchActivity;
import dagger.Module;

@Module public class ServerSearchModule extends ActivityModule<ServerSearchActivity> {

  public ServerSearchModule(ServerSearchActivity activity) {
    super(activity);
  }
}
