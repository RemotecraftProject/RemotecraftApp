package com.zireck.remotecraft.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.zireck.remotecraft.dagger.PerActivity;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.model.ServerModel;
import java.util.Collection;
import javax.inject.Inject;

@PerActivity public class ServerModelDataMapper {

  @Inject public ServerModelDataMapper() {

  }

  public ServerModel transform(Server server) {
    if (server == null) {
      throw new IllegalArgumentException("Cannot transform a null Server object.");
    }

    return new ServerModel.Builder()
        .ssid(server.getSsid())
        .ip(server.getIp())
        .hostname(server.getHostname())
        .os(server.getOs())
        .version(server.getVersion())
        .seed(server.getSeed())
        .worldName(server.getWorldName())
        .playerName(server.getPlayerName())
        .build();
  }

  public Collection<ServerModel> transform(Collection<Server> serversCollection) {
    return Stream.of(serversCollection)
        .map(this::transform)
        .collect(Collectors.toList());
  }
}
