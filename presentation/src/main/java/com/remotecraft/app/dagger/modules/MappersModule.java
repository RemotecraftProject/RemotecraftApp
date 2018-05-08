package com.remotecraft.app.dagger.modules;

import com.remotecraft.app.infrastructure.protocol.mapper.MessageJsonMapper;
import com.remotecraft.app.infrastructure.tool.JsonSerializer;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class MappersModule {

  public MappersModule() {

  }

  @Provides @Singleton MessageJsonMapper provideMessageJsonMapper(JsonSerializer jsonSerializer) {
    return new MessageJsonMapper(jsonSerializer);
  }
}
