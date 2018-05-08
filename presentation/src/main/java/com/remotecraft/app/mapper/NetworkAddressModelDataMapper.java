package com.remotecraft.app.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.remotecraft.app.dagger.qualifiers.PerActivity;
import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.model.NetworkAddressModel;
import java.util.Collection;
import javax.inject.Inject;

@PerActivity
public class NetworkAddressModelDataMapper {

  @Inject
  public NetworkAddressModelDataMapper() {

  }

  public NetworkAddressModel transform(NetworkAddress networkAddress) {
    if (networkAddress == null) {
      throw new IllegalArgumentException("Cannot transform a null NetworkAddress object.");
    }

    return NetworkAddressModel.builder()
        .ip(networkAddress.ip())
        .port(networkAddress.port())
        .build();
  }

  public Collection<NetworkAddressModel> transform(Collection<NetworkAddress> networkAddresses) {
    return Stream.of(networkAddresses)
        .map(this::transform)
        .collect(Collectors.toList());
  }

  public NetworkAddress transformInverse(NetworkAddressModel networkAddressModel) {
    if (networkAddressModel == null) {
      throw new IllegalArgumentException("Cannot transform a null NetworkAddressModel object.");
    }

    return NetworkAddress.builder()
        .ip(networkAddressModel.ip())
        .port(networkAddressModel.port())
        .build();
  }

  public Collection<NetworkAddress> transformInverse(
      Collection<NetworkAddressModel> networkAddressModels) {
    return Stream.of(networkAddressModels)
        .map(this::transformInverse)
        .collect(Collectors.toList());
  }
}
