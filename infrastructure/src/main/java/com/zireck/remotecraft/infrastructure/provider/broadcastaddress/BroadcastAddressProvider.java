package com.zireck.remotecraft.infrastructure.provider.broadcastaddress;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.Collection;

public interface BroadcastAddressProvider {
  Collection<InetAddress> getBroadcastAddresses() throws SocketException;
}
