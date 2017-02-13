package com.zireck.remotecraft.infrastructure.entity.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.infrastructure.entity.NetworkAddressEntity;
import java.util.Collection;
import javax.inject.Inject;

public class NetworkAddressEntityDataMapper {

  @Inject public NetworkAddressEntityDataMapper() {

  }

  public NetworkAddress transform(NetworkAddressEntity networkAddressEntity) {
    if (networkAddressEntity == null) {
      return null;
    }

    return new NetworkAddress.Builder()
        .with(networkAddressEntity.getIp())
        .and(networkAddressEntity.getPort())
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

    return new NetworkAddressEntity.Builder()
        .with(networkAddress.getIp())
        .and(networkAddress.getPort())
        .build();
  }

  public Collection<NetworkAddressEntity> transformInverse(
      Collection<NetworkAddress> networkAddresses) {
    return Stream.of(networkAddresses)
        .map(this::transformInverse)
        .collect(Collectors.toList());
  }
}
