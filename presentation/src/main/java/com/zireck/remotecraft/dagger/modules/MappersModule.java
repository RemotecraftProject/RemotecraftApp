package com.zireck.remotecraft.dagger.modules;

import com.google.gson.Gson;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class MappersModule {

  public MappersModule() {

  }

  @Provides @Singleton MessageJsonMapper provideMessageJsonMapper(Gson gson) {
    return new MessageJsonMapper(gson);
  }
}
