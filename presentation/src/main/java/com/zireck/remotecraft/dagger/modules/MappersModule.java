package com.zireck.remotecraft.dagger.modules;

import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class MappersModule {

  public MappersModule() {

  }

  @Provides @Singleton MessageJsonMapper provideMessageJsonMapper(JsonSerializer jsonSerializer) {
    return new MessageJsonMapper(jsonSerializer);
  }
}
