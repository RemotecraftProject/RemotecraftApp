package com.zireck.remotecraft.infrastructure.manager;

import android.util.Log;
import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import com.zireck.remotecraft.infrastructure.protocol.NetworkProtocolHelper;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Collection;

public class NetworkDiscoveryManager {

  private static final int RETRY_COUNT = 5;

  private NetworkInterfaceManager networkInterfaceManager;
  private NetworkResponseManager networkResponseManager;
  private NetworkProtocolManager networkProtocolManager;

  private DatagramSocket datagramSocket;
  private boolean receivedValidResponse = false;
  private WorldEntity worldEntity = null;

  public NetworkDiscoveryManager(NetworkInterfaceManager networkInterfaceManager,
      NetworkResponseManager networkResponseManager,
      NetworkProtocolManager networkProtocolManager) {
    this.networkInterfaceManager = networkInterfaceManager;
    this.networkResponseManager = networkResponseManager;
    this.networkProtocolManager = networkProtocolManager;

    try {
      datagramSocket = new DatagramSocket();
      datagramSocket.setBroadcast(true);
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  public void sendDiscoveryRequest() throws IOException {
    sendRequestToDefaultBroadcastAddress();
    sendRequestToEveryInterfaceBroadcastAddress();
  }

  public WorldEntity discover() throws IOException {
    DatagramPacket responsePacket;
    byte[] responseBuffer;
    int failCount = 0;
    while (failCount < RETRY_COUNT) {
      responseBuffer = new byte[15000];
      responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);

      datagramSocket.setSoTimeout(NetworkProtocolHelper.SOCKET_TIMEOUT);

      try {
        datagramSocket.receive(responsePacket);
      } catch (SocketTimeoutException e) {
        continue;
      }

      worldEntity = parseResponse(responsePacket);
      if (worldEntity != null) {
        break;
      }

      failCount++;
    }

    datagramSocket.disconnect();
    datagramSocket.close();

    return worldEntity;
  }

  private void sendRequestToDefaultBroadcastAddress() throws IOException {
    DatagramPacket datagramPacket = getDatagramPacket(InetAddress.getByName("255.255.255.255"));
    datagramSocket.send(datagramPacket);
  }

  private void sendRequestToEveryInterfaceBroadcastAddress() throws IOException {
    DatagramPacket datagramPacket;
    Collection<InetAddress> broadcastAddresses = networkInterfaceManager.getBroadcastAddresses();

    for (InetAddress broadcastAddress : broadcastAddresses) {
      datagramPacket = getDatagramPacket(broadcastAddress);
      datagramSocket.send(datagramPacket);
    }
  }

  private DatagramPacket getDatagramPacket(InetAddress inetAddress) {
    String discoveryRequest = networkProtocolManager.getDiscoveryRequest();
    Log.d("k9d3", "Sending JSON: ");
    Log.d("k9d3", discoveryRequest);
    byte[] discoveryCommand = discoveryRequest.getBytes();
    return new DatagramPacket(discoveryCommand, discoveryCommand.length, inetAddress,
        NetworkProtocolHelper.DISCOVERY_PORT);
  }

  // TODO clean this mess up
  private WorldEntity parseResponse(DatagramPacket responsePacket) {
    if (responsePacket == null) {
      return null;
    }

    String responseMessage = new String(responsePacket.getData()).trim();
    receivedValidResponse = NetworkProtocolHelper.getCommand(responseMessage)
        .equals(NetworkProtocolHelper.DISCOVERY_RESPONSE);
    if (!receivedValidResponse) {
      return null;
    }

    String seedAndWorldName = NetworkProtocolHelper.getArg(responseMessage);

    String seed = "", worldName = "", playerName = "";
    try {
      seed = seedAndWorldName.split("_")[0];
      worldName = seedAndWorldName.split("_")[1];
      playerName = seedAndWorldName.split("_")[2];
    } catch (Exception e) {
      e.printStackTrace();
    }

    String ip = responsePacket.getAddress().toString().replace("/", "");

    WorldEntity worldEntity =
        new WorldEntity.Builder().ip(ip).seed(seed).name(worldName).player(playerName).build();

    return worldEntity;
  }
}
