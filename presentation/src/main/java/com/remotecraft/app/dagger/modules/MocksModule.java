package com.remotecraft.app.dagger.modules;

import android.content.Context;
import com.remotecraft.app.infrastructure.entity.mock.EntityMockDataSource;
import com.remotecraft.app.infrastructure.network.NetworkConnectionlessMockTransmitter;
import com.remotecraft.app.infrastructure.protocol.ProtocolMessageComposer;
import com.remotecraft.app.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.remotecraft.app.infrastructure.protocol.mock.ProtocolMockMessageDataSource;
import com.remotecraft.app.infrastructure.tool.JsonSerializer;
import com.remotecraft.app.infrastructure.tool.ImageDecoder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class MocksModule {

  @Provides @Singleton EntityMockDataSource provideEntityMockDataSource(Context context,
      ImageDecoder imageDecoder) {
    return new EntityMockDataSource(context, imageDecoder);
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
