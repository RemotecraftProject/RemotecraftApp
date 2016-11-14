package com.zireck.remotecraft.infrastructure.manager;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import com.zireck.remotecraft.infrastructure.protocol.NetworkProtocolHelper;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.data.Server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Collection;
import timber.log.Timber;

public class NetworkDiscoveryManager {

  private static final int RETRY_COUNT = 5;

  private Gson gson;
  private GsonBuilder gsonBuilder;
  private NetworkInterfaceManager networkInterfaceManager;
  private NetworkResponseManager networkResponseManager;
  private NetworkProtocolManager networkProtocolManager;

  private DatagramSocket datagramSocket;
  private WorldEntity worldEntity = null;

  public NetworkDiscoveryManager(Gson gson, GsonBuilder gsonBuilder,
      NetworkInterfaceManager networkInterfaceManager,
      NetworkResponseManager networkResponseManager,
      NetworkProtocolManager networkProtocolManager) {
    this.gson = gson;
    this.gsonBuilder = gsonBuilder;
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
    Timber.d("Sending JSON:");
    Timber.d(discoveryRequest);
    byte[] discoveryCommand = discoveryRequest.getBytes();
    return new DatagramPacket(discoveryCommand, discoveryCommand.length, inetAddress,
        NetworkProtocolHelper.DISCOVERY_PORT);
  }

  private WorldEntity parseResponse(DatagramPacket responsePacket) {
    if (responsePacket == null || responsePacket.getData() == null) {
      Timber.e("Response Packet cannot be null.");
      return null;
    }

    String response = new String(responsePacket.getData()).trim();
    Message message = gson.fromJson(response, Message.class);

    if (message == null || !message.isSuccess() || !message.isServer()) {
      Timber.e("Invalid message received");
      return null;
    }

    Server server = message.getServer();

    return new WorldEntity.Builder().ip(server.getIp())
        .seed(server.getSeed())
        .name(server.getWorldName())
        .player(server.getPlayerName())
        .build();
  }
}
