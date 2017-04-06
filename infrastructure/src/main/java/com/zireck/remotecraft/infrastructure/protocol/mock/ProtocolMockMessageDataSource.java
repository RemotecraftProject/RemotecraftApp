package com.zireck.remotecraft.infrastructure.protocol.mock;

import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.entity.mock.EntityMockDataSource;
import com.zireck.remotecraft.infrastructure.protocol.ProtocolMessageComposer;
import com.zireck.remotecraft.infrastructure.protocol.base.type.ServerProtocol;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.zireck.remotecraft.infrastructure.protocol.messages.ServerMessage;

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
