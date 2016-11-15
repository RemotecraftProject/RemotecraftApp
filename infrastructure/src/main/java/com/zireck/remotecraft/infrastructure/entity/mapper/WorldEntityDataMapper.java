package com.zireck.remotecraft.infrastructure.entity.mapper;

import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import javax.inject.Inject;

public class WorldEntityDataMapper {

  @Inject public WorldEntityDataMapper() {

  }

  public World transform(WorldEntity worldEntity) {
    if (worldEntity == null) {
      return null;
    }

    World world = new World.Builder().version(worldEntity.getVersion())
        .ssid(worldEntity.getSsid())
        .ip(worldEntity.getIp())
        .name(worldEntity.getName())
        .player(worldEntity.getPlayer())
        .build();

    return world;
  }
}
