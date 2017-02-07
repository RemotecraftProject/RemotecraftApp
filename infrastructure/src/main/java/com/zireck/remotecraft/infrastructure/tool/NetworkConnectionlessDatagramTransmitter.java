package com.zireck.remotecraft.infrastructure.tool;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import timber.log.Timber;

public class NetworkConnectionlessDatagramTransmitter implements NetworkConnectionlessTransmitter {

  private final DatagramSocket datagramSocket;

  public NetworkConnectionlessDatagramTransmitter(DatagramSocket datagramSocket) {
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

  @Override public void send(DatagramPacket datagramPacket) throws IOException {
    datagramSocket.send(datagramPacket);
  }

  @Override public void receive(DatagramPacket datagramPacket) throws IOException {
    datagramSocket.receive(datagramPacket);
  }

  @Override public void shutdown() {
    datagramSocket.disconnect();
    datagramSocket.close();
  }
}
