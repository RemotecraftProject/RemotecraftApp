package com.zireck.remotecraft.infrastructure.network;

import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.protocol.ProtocolMessageComposer;
import com.zireck.remotecraft.infrastructure.protocol.base.type.ServerProtocol;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.zireck.remotecraft.infrastructure.protocol.messages.ServerMessage;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

public class NetworkConnectionlessMockTransmitter implements NetworkConnectionlessTransmitter {

  private final JsonSerializer jsonSerializer;
  private final ServerProtocolMapper serverProtocolMapper;
  private final ProtocolMessageComposer protocolMessageComposer;

  public NetworkConnectionlessMockTransmitter(JsonSerializer jsonSerializer,
      ServerProtocolMapper serverProtocolMapper, ProtocolMessageComposer protocolMessageComposer) {
    this.jsonSerializer = jsonSerializer;
    this.serverProtocolMapper = serverProtocolMapper;
    this.protocolMessageComposer = protocolMessageComposer;
  }

  @Override public boolean isReady() {
    return true;
  }

  @Override public void setBroadcast(boolean isBroadcast) throws SocketException {

  }

  @Override public void setTimeout(int timeout) throws SocketException {

  }

  @Override public void send(DatagramPacket datagramPacket) throws IOException {

  }

  @Override public DatagramPacket receive(DatagramPacket datagramPacket) throws IOException {
    ServerMessage mockServerMessageResponse = getMockMessageResponse();
    String mockServerMessageResponseJson = jsonSerializer.toJson(mockServerMessageResponse);
    datagramPacket.setData(mockServerMessageResponseJson.getBytes());

    return datagramPacket;
  }

  // TODO set up a MockDataSourceProvider
  private ServerMessage getMockMessageResponse() {
    ServerEntity mockServerEntity = getMockServerEntity();
    ServerProtocol mockServerProtocol = serverProtocolMapper.transform(mockServerEntity);
    return protocolMessageComposer.composeServerMessage(mockServerProtocol);
  }

  private ServerEntity getMockServerEntity() {
    return ServerEntity.builder()
        .ssid("MOVISTAR_C64C")
        .ip("85.215.47.129")
        .hostname("iMac")
        .os("macOS Sierra")
        .version("2.8.14")
        .seed("4346234563458034")
        .worldName("Reign of Giants")
        .playerName("Etho")
        .build();
  }
}
