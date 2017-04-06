package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.infrastructure.entity.mock.EntityMockDataSource;
import com.zireck.remotecraft.infrastructure.network.NetworkConnectionlessMockTransmitter;
import com.zireck.remotecraft.infrastructure.protocol.ProtocolMessageComposer;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.zireck.remotecraft.infrastructure.protocol.mock.ProtocolMockMessageDataSource;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class MocksModule {

  @Provides @Singleton EntityMockDataSource provideEntityMockDataSource() {
    return new EntityMockDataSource();
  }

  @Provides @Singleton ProtocolMockMessageDataSource provideProtocolMockMessageDataSource(
      EntityMockDataSource entityMockDataSource, ServerProtocolMapper serverProtocolMapper,
      ProtocolMessageComposer protocolMessageComposer) {
    return new ProtocolMockMessageDataSource(entityMockDataSource, serverProtocolMapper,
        protocolMessageComposer);
  }

  @Provides @Singleton
  NetworkConnectionlessMockTransmitter provideNetworkConnectionlessMockTransmitter(
      ProtocolMockMessageDataSource protocolMockMessageDataSource, JsonSerializer jsonSerializer) {
    return new NetworkConnectionlessMockTransmitter(protocolMockMessageDataSource, jsonSerializer);
  }
}
