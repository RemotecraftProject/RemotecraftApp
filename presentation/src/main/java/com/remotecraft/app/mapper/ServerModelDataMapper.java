package com.remotecraft.app.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.remotecraft.app.dagger.qualifiers.PerActivity;
import com.remotecraft.app.domain.Server;
import com.remotecraft.app.model.ServerModel;
import java.util.Collection;
import javax.inject.Inject;

@PerActivity public class ServerModelDataMapper {

  @Inject public ServerModelDataMapper() {

  }

  public ServerModel transform(Server server) {
    if (server == null) {
      throw new IllegalArgumentException("Cannot transform a null Server object.");
    }

    return ServerModel.builder()
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

  public Collection<ServerModel> transform(Collection<Server> serversCollection) {
    return Stream.of(serversCollection)
        .map(this::transform)
        .collect(Collectors.toList());
  }
}
