package com.remotecraft.app.infrastructure.protocol.mock;

import com.remotecraft.app.infrastructure.entity.ServerEntity;
import com.remotecraft.app.infrastructure.entity.mock.EntityMockDataSource;
import com.remotecraft.app.infrastructure.protocol.ProtocolMessageComposer;
import com.remotecraft.app.infrastructure.protocol.base.type.ServerProtocol;
import com.remotecraft.app.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.remotecraft.app.infrastructure.protocol.messages.ServerMessage;

public class ProtocolMockMessageDataSource {

  private final EntityMockDataSource entityMockDataSource;
  private final ServerProtocolMapper serverProtocolMapper;
  private final ProtocolMessageComposer protocolMessageComposer;

  public ProtocolMockMessageDataSource(EntityMockDataSource entityMockDataSource,
      ServerProtocolMapper serverProtocolMapper, ProtocolMessageComposer protocolMessageComposer) {
    this.entityMockDataSource = entityMockDataSource;
    this.serverProtocolMapper = serverProtocolMapper;
    this.protocolMessageComposer = protocolMessageComposer;
  }

  public ServerMessage getServerMessage() {
    ServerEntity serverEntity = entityMockDataSource.getServerEntity();
    ServerProtocol serverProtocol = serverProtocolMapper.transform(serverEntity);
    return protocolMessageComposer.composeServerMessage(serverProtocol);
  }
}
