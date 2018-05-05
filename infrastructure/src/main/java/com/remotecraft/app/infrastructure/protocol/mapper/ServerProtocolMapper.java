package com.remotecraft.app.infrastructure.protocol.mapper;

import com.remotecraft.app.infrastructure.entity.ServerEntity;
import com.remotecraft.app.infrastructure.protocol.base.type.ServerProtocol;
import javax.inject.Inject;

public class ServerProtocolMapper {

  @Inject public ServerProtocolMapper() {

  }

  public ServerEntity transform(ServerProtocol serverProtocol) {
    if (serverProtocol == null) {
      return null;
    }

    return ServerEntity.builder()
        .ssid(serverProtocol.getSsid())
        .ip(serverProtocol.getIp())
        .hostname(serverProtocol.getHostname())
        .os(serverProtocol.getOs())
        .version(serverProtocol.getVersion())
        .seed(serverProtocol.getSeed())
        .worldName(serverProtocol.getWorldName())
        .playerName(serverProtocol.getPlayerName())
        .build();
  }

  public ServerProtocol transform(ServerEntity serverEntity) {
    if (serverEntity == null) {
      return null;
    }

    return new ServerProtocol.Builder()
        .ssid(serverEntity.ssid())
        .ip(serverEntity.ip())
        .hostname(serverEntity.hostname())
        .os(serverEntity.os())
        .version(serverEntity.version())
        .seed(serverEntity.seed())
        .worldName(serverEntity.worldName())
        .playerName(serverEntity.playerName())
        .build();
  }
}
