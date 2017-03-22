package com.zireck.remotecraft.dagger.modules.activitymodules;

import com.zireck.remotecraft.view.activity.ServerFoundActivity;
import dagger.Module;

@Module public class ServerFoundModule extends ActivityModule<ServerFoundActivity> {

  public ServerFoundModule(ServerFoundActivity activity) {
    super(activity);
  }
}
