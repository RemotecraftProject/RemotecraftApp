package com.zireck.remotecraft.server.mock.network;

import com.google.gson.Gson;
import com.zireck.remotecraft.server.mock.protocol.CommandType;
import com.zireck.remotecraft.server.mock.protocol.base.Message;
import com.zireck.remotecraft.server.mock.provider.MockDataProvider;
import com.zireck.remotecraft.server.mock.provider.ServerProvider;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// TODO clean up this mess!
public class MockNetworkDiscovery implements Runnable {

  private static final String TAG = MockNetworkDiscovery.class.getSimpleName();
  private final static int PORT = 9998;
  private static MockNetworkDiscovery INSTANCE;

  private ServerProvider serverProvider;
  private Gson gson;
  private Thread thread;
  private DatagramSocket datagramSocket;

  private MockNetworkDiscovery() {
    serverProvider = new MockDataProvider();
    gson = new Gson();
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

          Message message = gson.fromJson(receiveMessage, Message.class);
          if (message != null && message.isCommand() && message.getCommand()
              .equals(CommandType.GET_SERVER_INFO)) {
            reply(datagramSocket, datagramPacket);
          }
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
    String responseMessage = gson.toJson(serverProvider.getServerData());
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
