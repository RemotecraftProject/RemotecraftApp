package com.zireck.remotecraft.mapper;

import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.model.WorldModel;
import javax.inject.Inject;

@PerActivity public class WorldModelDataMapper {

  @Inject public WorldModelDataMapper() {

  }

  public WorldModel transform(World world) {
    if (world == null) {
      throw new IllegalArgumentException("Cannot transform a null World object.");
    }

    return new WorldModel.Builder()
        .version(world.getVersion())
        .ssid(world.getSsid())
        .ip(world.getIp())
        .name(world.getName())
        .player(world.getPlayer())
        .build();
  }
}
