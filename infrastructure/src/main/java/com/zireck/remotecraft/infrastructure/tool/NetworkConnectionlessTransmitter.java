package com.zireck.remotecraft.infrastructure.tool;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

public interface NetworkConnectionlessTransmitter {
  boolean isReady();
  void setBroadcast(boolean isBroadcast) throws SocketException;
  void setTimeout(int timeout) throws SocketException;
  void send(DatagramPacket datagramPacket) throws IOException;
  void receive(DatagramPacket datagramPacket) throws IOException;
  void shutdown();
}
