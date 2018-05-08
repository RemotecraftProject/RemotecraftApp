package com.remotecraft.app.infrastructure.entity.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.infrastructure.entity.NetworkAddressEntity;
import java.util.Collection;
import javax.inject.Inject;

public class NetworkAddressEntityDataMapper {

  @Inject
  public NetworkAddressEntityDataMapper() {

  }

  public NetworkAddress transform(NetworkAddressEntity networkAddressEntity) {
    if (networkAddressEntity == null) {
      return null;
    }

    return NetworkAddress.builder()
        .ip(networkAddressEntity.ip())
        .port(networkAddressEntity.port())
        .build();
  }

  public Collection<NetworkAddress> transform(
      Collection<NetworkAddressEntity> networkAddressEntities) {
    return Stream.of(networkAddressEntities)
        .map(this::transform)
        .collect(Collectors.toList());
  }

  public NetworkAddressEntity transformInverse(NetworkAddress networkAddress) {
    if (networkAddress == null) {
      return null;
    }

    return NetworkAddressEntity.builder()
        .ip(networkAddress.ip())
        .port(networkAddress.port())
        .build();
  }

  public Collection<NetworkAddressEntity> transformInverse(
      Collection<NetworkAddress> networkAddresses) {
    return Stream.of(networkAddresses)
        .map(this::transformInverse)
        .collect(Collectors.toList());
  }
}
