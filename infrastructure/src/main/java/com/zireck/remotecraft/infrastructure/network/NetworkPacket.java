package com.zireck.remotecraft.infrastructure.network;

import com.zireck.remotecraft.infrastructure.entity.NetworkAddressEntity;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class NetworkPacket {

  private final DatagramPacket datagramPacket;

  public NetworkPacket(DatagramPacket datagramPacket) {
    this.datagramPacket = datagramPacket;
  }

  public NetworkPacket(int bufferSize) {
    byte[] buffer = new byte[bufferSize];
    datagramPacket = new DatagramPacket(buffer, buffer.length);
  }

  public NetworkPacket(String content) {
    byte[] payload = content.getBytes();
    datagramPacket = new DatagramPacket(payload, payload.length);
  }

  public NetworkPacket(String content, InetAddress inetAddress, int port) {
    byte[] payload = content.getBytes();
    datagramPacket = new DatagramPacket(payload, payload.length, inetAddress, port);
  }

  public NetworkPacket(String content, NetworkAddressEntity networkAddressEntity, int port)
      throws IOException {
    InetAddress inetAddress = InetAddress.getByName(networkAddressEntity.ip());
    byte[] payload = content.getBytes();
    datagramPacket = new DatagramPacket(payload, payload.length, inetAddress, port);
  }

  public DatagramPacket getDatagramPacket() {
    return datagramPacket;
  }

  public String getContent() {
    return datagramPacket.getData() == null ? "" : new String(datagramPacket.getData()).trim();
  }
}
