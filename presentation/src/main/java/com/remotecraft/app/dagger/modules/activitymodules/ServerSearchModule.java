package com.remotecraft.app.dagger.modules.activitymodules;

import com.remotecraft.app.view.activity.ServerSearchActivity;
import dagger.Module;

@Module public class ServerSearchModule extends ActivityModule<ServerSearchActivity> {

  public ServerSearchModule(ServerSearchActivity activity) {
    super(activity);
  }
}
