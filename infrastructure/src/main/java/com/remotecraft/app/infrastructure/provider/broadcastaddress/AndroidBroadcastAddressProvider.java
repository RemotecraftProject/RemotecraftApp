package com.remotecraft.app.infrastructure.provider.broadcastaddress;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.remotecraft.app.infrastructure.provider.networkinterface.NetworkInterfaceProvider;
import com.remotecraft.app.infrastructure.validation.NetworkInterfaceValidator;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;

public class AndroidBroadcastAddressProvider implements BroadcastAddressProvider {

  private final NetworkInterfaceProvider networkInterfaceProvider;
  private final NetworkInterfaceValidator networkInterfaceValidator;

  public AndroidBroadcastAddressProvider(NetworkInterfaceProvider networkInterfaceProvider,
      NetworkInterfaceValidator networkInterfaceValidator) {
    this.networkInterfaceProvider = networkInterfaceProvider;
    this.networkInterfaceValidator = networkInterfaceValidator;
  }

  @Override public Collection<InetAddress> getBroadcastAddresses() throws SocketException {
    return Stream.of(networkInterfaceProvider.getNetworkInterfaces())
        .filter(networkInterfaceValidator::isValid)
        .map(NetworkInterface::getInterfaceAddresses)
        .flatMap(Stream::of)
        .map(InterfaceAddress::getBroadcast)
        .filter(broadcastAddress -> broadcastAddress != null)
        .collect(Collectors.toList());
  }
}
