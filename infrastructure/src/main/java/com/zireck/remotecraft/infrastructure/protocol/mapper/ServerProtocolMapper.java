package com.zireck.remotecraft.infrastructure.protocol.mapper;

import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.protocol.base.type.ServerProtocol;
import javax.inject.Inject;

public class ServerProtocolMapper {

  @Inject public ServerProtocolMapper() {

  }

  public ServerEntity transform(ServerProtocol serverProtocol) {
    if (serverProtocol == null) {
      return null;
    }

    return new ServerEntity.Builder()
        .ip(serverProtocol.getIp())
        .ssid(serverProtocol.getSsid())
        .version(serverProtocol.getVersion())
        .seed(serverProtocol.getSeed())
        .worldName(serverProtocol.getWorldName())
        .playerName(serverProtocol.getPlayerName())
        .build();
  }
}
