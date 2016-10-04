package com.zireck.remotecraft.infrastructure.manager;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

public class NetworkInterfaceManager {

  public NetworkInterfaceManager() {

  }

  public Collection<InetAddress> getBroadcastAddresses() throws SocketException {
    Collection<NetworkInterface> networkInterfaces = getNetworkInterfaces();
    Collection<InterfaceAddress> interfaceAddresses = getInterfaceAddressesFrom(networkInterfaces);
    Collection<InetAddress> broadcastAddresses = getBroadcastAddressesFrom(interfaceAddresses);

    return broadcastAddresses;
  }

  private Collection<InetAddress> getBroadcastAddressesFrom(Collection<InterfaceAddress> interfaceAddresses) {
    List<InetAddress> broadcastAddresses = new ArrayList<>();

    for (InterfaceAddress interfaceAddress : interfaceAddresses) {
      InetAddress broadcastAddress = interfaceAddress.getBroadcast();

      if (broadcastAddress == null) {
        continue;
      }

      broadcastAddresses.add(broadcastAddress);
    }

    return broadcastAddresses;
  }

  private Collection<InterfaceAddress> getInterfaceAddressesFrom(Collection<NetworkInterface> networkInterfaces) {
    List<InterfaceAddress> interfaceAddresses = new ArrayList<>();

    for (NetworkInterface networkInterface : networkInterfaces) {
      for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
        interfaceAddresses.add(interfaceAddress);
      }
    }

    return interfaceAddresses;
  }

  private Collection<NetworkInterface> getNetworkInterfaces() throws SocketException {
    List<NetworkInterface> networkInterfaces = new ArrayList<>();

    Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
    while (interfaces.hasMoreElements()) {
      NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

      if (networkInterface.isLoopback() || !networkInterface.isUp()) {
        continue;
      }

      networkInterfaces.add(networkInterface);
    }

    return networkInterfaces;
  }
}
