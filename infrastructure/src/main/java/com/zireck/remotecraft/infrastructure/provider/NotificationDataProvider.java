package com.zireck.remotecraft.infrastructure.provider;

import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.provider.NotificationProvider;
import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.zireck.remotecraft.infrastructure.manager.AndroidNotificationManager;
import javax.inject.Inject;

public class NotificationDataProvider implements NotificationProvider {

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
