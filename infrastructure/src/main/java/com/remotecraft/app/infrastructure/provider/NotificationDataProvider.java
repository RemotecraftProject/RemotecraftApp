package com.remotecraft.app.infrastructure.provider;

import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.provider.NotificationActionProvider;
import com.remotecraft.app.infrastructure.entity.ServerEntity;
import com.remotecraft.app.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.remotecraft.app.infrastructure.manager.AndroidNotificationManager;
import javax.inject.Inject;

public class NotificationDataProvider implements NotificationActionProvider {

  private final AndroidNotificationManager androidNotificationManager;
  private final ServerEntityDataMapper serverEntityDataMapper;

  @Inject public NotificationDataProvider(AndroidNotificationManager androidNotificationManager,
      ServerEntityDataMapper serverEntityDataMapper) {
    this.androidNotificationManager = androidNotificationManager;
    this.serverEntityDataMapper = serverEntityDataMapper;
  }

  @Override public void notifyServerFound(Server server) {
    ServerEntity serverEntity = serverEntityDataMapper.transformInverse(server);
    androidNotificationManager.notifyServerFound(serverEntity);
  }
}
