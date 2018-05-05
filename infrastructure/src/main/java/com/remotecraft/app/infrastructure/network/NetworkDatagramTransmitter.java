package com.remotecraft.app.infrastructure.network;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class NetworkDatagramTransmitter implements NetworkConnectionlessTransmitter {

  private final DatagramSocket datagramSocket;

  public NetworkDatagramTransmitter(DatagramSocket datagramSocket) {
    this.datagramSocket = datagramSocket;
  }

  @Override public boolean isReady() {
    return datagramSocket != null;
  }

  @Override public void setBroadcast(boolean isBroadcast) throws SocketException {
    datagramSocket.setBroadcast(isBroadcast);
  }

  @Override public void setTimeout(int timeout) throws SocketException {
    datagramSocket.setSoTimeout(timeout);
  }

  @Override public void send(NetworkPacket networkPacket) throws IOException {
    datagramSocket.send(networkPacket.getDatagramPacket());
  }

  @Override public NetworkPacket receive(NetworkPacket networkPacket) throws IOException {
    datagramSocket.receive(networkPacket.getDatagramPacket());
    return new NetworkPacket(networkPacket.getDatagramPacket());
  }
}
