package com.zireck.remotecraft.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.model.WorldModel;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

@PerActivity public class WorldModelDataMapper {

  @Inject public WorldModelDataMapper() {

  }

  public WorldModel transform(World world) {
    if (world == null) {
      throw new IllegalArgumentException("Cannot transform a null World object.");
    }

    return new WorldModel.Builder().version(world.getVersion())
        .ssid(world.getSsid())
        .ip(world.getIp())
        .name(world.getName())
        .player(world.getPlayer())
        .build();
  }

  public Collection<WorldModel> transform(Collection<World> worldsCollection) {
    Collection<WorldModel> worldModelsCollection;

    if (worldsCollection == null || worldsCollection.isEmpty()) {
      worldModelsCollection = Collections.emptyList();
    } else {
      worldModelsCollection =
          Stream.of(worldsCollection)
              .map(this::transform)
              .collect(Collectors.toList());
    }

    return worldModelsCollection;
  }
}
