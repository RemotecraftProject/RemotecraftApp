package com.zireck.remotecraft.infrastructure.protocol.mapper;

import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.protocol.base.Server;
import javax.inject.Inject;

public class ServerMapper {

  @Inject public ServerMapper() {

  }

  public ServerEntity transform(Server server) {
    if (server == null) {
      return null;
    }

    return new ServerEntity.Builder()
        .ip(server.getIp())
        .ssid(server.getSsid())
        .version(server.getVersion())
        .seed(server.getSeed())
        .worldName(server.getWorldName())
        .playerName(server.getPlayerName())
        .build();
  }
}
