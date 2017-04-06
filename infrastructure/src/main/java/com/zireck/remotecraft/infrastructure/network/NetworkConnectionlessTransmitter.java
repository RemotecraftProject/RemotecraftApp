package com.zireck.remotecraft.infrastructure.network;

import java.io.IOException;
import java.net.SocketException;

public interface NetworkConnectionlessTransmitter {
  boolean isReady();
  void setBroadcast(boolean isBroadcast) throws SocketException;
  void setTimeout(int timeout) throws SocketException;
  void send(NetworkPacket networkPacket) throws IOException;
  NetworkPacket receive(NetworkPacket networkPacket) throws IOException;
}
