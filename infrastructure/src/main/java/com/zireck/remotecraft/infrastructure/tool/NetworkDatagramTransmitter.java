package com.zireck.remotecraft.infrastructure.tool;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import javax.inject.Inject;
import timber.log.Timber;

public class NetworkDatagramTransmitter implements NetworkTransmitter {

  private DatagramSocket datagramSocket;

  @Inject public NetworkDatagramTransmitter() {
    try {
      datagramSocket = new DatagramSocket();
    } catch (SocketException e) {
      Timber.e("Error instantiating DatagramSocket");
      Timber.e(e.getMessage());
    }
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
