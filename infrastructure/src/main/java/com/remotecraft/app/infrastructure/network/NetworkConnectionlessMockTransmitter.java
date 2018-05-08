package com.remotecraft.app.infrastructure.network;

import com.remotecraft.app.infrastructure.protocol.messages.ServerMessage;
import com.remotecraft.app.infrastructure.protocol.mock.ProtocolMockMessageDataSource;
import com.remotecraft.app.infrastructure.tool.JsonSerializer;
import java.io.IOException;
import java.net.SocketException;

public class NetworkConnectionlessMockTransmitter implements NetworkConnectionlessTransmitter {

  private final ProtocolMockMessageDataSource protocolMockMessageDataSource;
  private final JsonSerializer jsonSerializer;

  public NetworkConnectionlessMockTransmitter(
      ProtocolMockMessageDataSource protocolMockMessageDataSource, JsonSerializer jsonSerializer) {
    this.protocolMockMessageDataSource = protocolMockMessageDataSource;
    this.jsonSerializer = jsonSerializer;
  }

  @Override
  public boolean isReady() {
    return true;
  }

  @Override
  public void setBroadcast(boolean isBroadcast) throws SocketException {

  }

  @Override
  public void setTimeout(int timeout) throws SocketException {

  }

  @Override
  public void send(NetworkPacket networkPacket) throws IOException {

  }

  @Override
  public NetworkPacket receive(NetworkPacket networkPacket) throws IOException {
    ServerMessage mockServerMessageResponse = protocolMockMessageDataSource.getServerMessage();
    String mockServerMessageResponseJson = jsonSerializer.toJson(mockServerMessageResponse);

    sleep(3);

    return new NetworkPacket(mockServerMessageResponseJson);
  }

  private void sleep(int seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
