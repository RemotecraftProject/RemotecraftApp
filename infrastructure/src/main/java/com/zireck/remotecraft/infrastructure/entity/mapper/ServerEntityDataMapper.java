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

    return Server.builder()
        .ssid(serverEntity.getSsid())
        .ip(serverEntity.getIp())
        .hostname(serverEntity.getHostname())
        .os(serverEntity.getOs())
        .version(serverEntity.getVersion())
        .seed(serverEntity.getSeed())
        .worldName(serverEntity.getWorldName())
        .playerName(serverEntity.getPlayerName())
        .build();
  }

  public ServerEntity transformInverse(Server server) {
    if (server == null) {
      return null;
    }

    return new ServerEntity.Builder()
        .ssid(server.ssid())
        .ip(server.ip())
        .hostname(server.hostname())
        .os(server.os())
        .version(server.version())
        .seed(server.seed())
        .worldName(server.worldName())
        .playerName(server.playerName())
        .build();
  }
}
