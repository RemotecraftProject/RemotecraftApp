package com.zireck.remotecraft.infrastructure.entity.mapper;

import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import javax.inject.Inject;

public class ServerEntityDataMapper {

  @Inject public ServerEntityDataMapper() {

  }

  public Server transform(ServerEntity serverEntity) {
    if (serverEntity == null) {
      return null;
    }

    return new Server.Builder()
        .ssid(serverEntity.getSsid())
        .ip(serverEntity.getIp())
        .version(serverEntity.getVersion())
        .seed(serverEntity.getSeed())
        .worldName(serverEntity.getWorldName())
        .playerName(serverEntity.getPlayerName())
        .build();
  }
}
