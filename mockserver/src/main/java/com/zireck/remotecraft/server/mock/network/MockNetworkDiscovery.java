package com.zireck.remotecraft.server.mock.network;

import com.google.gson.Gson;
import com.zireck.remotecraft.server.mock.protocol.BaseMessage;
import com.zireck.remotecraft.server.mock.protocol.DiscoveryData;
import com.zireck.remotecraft.server.mock.protocol.ResponseBuilder;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MockNetworkDiscovery implements Runnable {

  private static final String TAG = MockNetworkDiscovery.class.getSimpleName();
  private final static int PORT = 9998;
  private static MockNetworkDiscovery INSTANCE;

  private Thread thread;
  private DatagramSocket datagramSocket;

  private MockNetworkDiscovery() {
    thread = new Thread(this);
    thread.start();
  }

  public static MockNetworkDiscovery getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new MockNetworkDiscovery();
    }

    return INSTANCE;
  }

  @Override public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        datagramSocket =
            new DatagramSocket(MockNetworkDiscovery.PORT, InetAddress.getByName("0.0.0.0"));
        datagramSocket.setBroadcast(true);
        while (!Thread.currentThread().isInterrupted() && datagramSocket.isBound()) {
          byte[] receiveBuffer = new byte[15000];
          DatagramPacket datagramPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
          System.out.println(TAG + "> Waiting to receive request");
          datagramSocket.receive(datagramPacket);
          System.out.println(TAG + "> Request received!");

          String receiveMessage = new String(datagramPacket.getData()).trim();
          System.out.println(receiveMessage);

          // TODO check if it's asking for server info
          reply(datagramSocket, datagramPacket);
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        datagramSocket.disconnect();
        datagramSocket.close();
      }
    }
  }

  private void reply(DatagramSocket datagramSocket, DatagramPacket datagramPacket)
      throws IOException {
    DiscoveryData discoveryData = new DiscoveryData()
        .setSsid("MOVISTAR_C33")
        .setIp("127.0.0.2")
        .setVersion("2.3.4")
        .setSeed("0123456789")
        .setWorldName("The New World")
        .setPlayerName("Zireck");

    BaseMessage baseMessage = new ResponseBuilder<DiscoveryData>()
        .success()
        .setData(discoveryData)
        .build();

    Gson gson = new Gson();
    String responseMessage = gson.toJson(baseMessage);

    //String responseMessage =
    //    "REMOTECRAFT_DISCOVERY_RESPONSE:0123456789_MockWorldName_MockPlayerName";
    byte[] sendData = responseMessage.getBytes();

    DatagramPacket sendPacket =
        new DatagramPacket(sendData, sendData.length, datagramPacket.getAddress(),
            datagramPacket.getPort());
    if (datagramSocket.isBound()) {
      System.out.println(TAG + "> Sending to client: ");
      System.out.println(TAG + responseMessage);
      datagramSocket.send(sendPacket);
    }
  }

  public void shutdown() {
    if (datagramSocket != null) {
      datagramSocket.close();
      datagramSocket = null;
    }

    if (thread != null) {
      thread.interrupt();
    }

    thread = null;

    INSTANCE = null;
  }
}
