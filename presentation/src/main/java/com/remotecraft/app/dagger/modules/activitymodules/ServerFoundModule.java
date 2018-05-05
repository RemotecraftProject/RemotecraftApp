package com.remotecraft.app.dagger.modules.activitymodules;

import com.remotecraft.app.view.activity.ServerFoundActivity;
import dagger.Module;

@Module public class ServerFoundModule extends ActivityModule<ServerFoundActivity> {

  public ServerFoundModule(ServerFoundActivity activity) {
    super(activity);
  }
}
