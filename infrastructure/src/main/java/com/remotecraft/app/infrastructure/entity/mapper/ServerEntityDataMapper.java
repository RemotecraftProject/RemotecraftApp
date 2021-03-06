package com.remotecraft.app.infrastructure.entity.mapper;

import com.remotecraft.app.domain.Server;
import com.remotecraft.app.infrastructure.entity.ServerEntity;
import javax.inject.Inject;

public class ServerEntityDataMapper {

  @Inject
  public ServerEntityDataMapper() {

  }

  public Server transform(ServerEntity serverEntity) {
    if (serverEntity == null) {
      return null;
    }

    return Server.builder()
        .ssid(serverEntity.ssid())
        .ip(serverEntity.ip())
        .hostname(serverEntity.hostname())
        .os(serverEntity.os())
        .version(serverEntity.version())
        .seed(serverEntity.seed())
        .worldName(serverEntity.worldName())
        .playerName(serverEntity.playerName())
        .encodedWorldImage(serverEntity.encodedWorldImage())
        .build();
  }

  public ServerEntity transformInverse(Server server) {
    if (server == null) {
      return null;
    }

    return ServerEntity.builder()
        .ssid(server.ssid())
        .ip(server.ip())
        .hostname(server.hostname())
        .os(server.os())
        .version(server.version())
        .seed(server.seed())
        .worldName(server.worldName())
        .playerName(server.playerName())
        .encodedWorldImage(server.encodedWorldImage())
        .build();
  }
}
