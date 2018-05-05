package com.remotecraft.app.infrastructure.entity.mock;

import com.remotecraft.app.infrastructure.entity.ServerEntity;

public class EntityMockDataSource {

  public EntityMockDataSource() {

  }

  public ServerEntity getServerEntity() {
    return ServerEntity.builder()
        .ssid("MOVISTAR_C64C")
        .ip("85.215.47.129")
        .hostname("iMac")
        .os("macOS Sierra")
        .version("2.8.14")
        .seed("4346234563458034")
        .worldName("Reign of Giants")
        .playerName("Etho")
        .build();
  }
}
