package com.remotecraft.app.infrastructure.provider.networkinterface;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;

public interface NetworkInterfaceProvider {
  Collection<NetworkInterface> getNetworkInterfaces() throws SocketException;
}
