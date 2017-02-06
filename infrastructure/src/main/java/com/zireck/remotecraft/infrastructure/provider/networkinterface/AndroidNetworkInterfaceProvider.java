package com.zireck.remotecraft.infrastructure.provider.networkinterface;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

public class AndroidNetworkInterfaceProvider implements NetworkInterfaceProvider {

  @Inject public AndroidNetworkInterfaceProvider() {

  }

  @Override public Collection<NetworkInterface> getNetworkInterfaces() throws SocketException {
    return Collections.list(NetworkInterface.getNetworkInterfaces());
  }
}
