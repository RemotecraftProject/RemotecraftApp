package com.zireck.remotecraft.dagger.modules;

import com.google.gson.Gson;
import com.zireck.remotecraft.infrastructure.tool.GsonSerializer;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ToolsModule {

  public ToolsModule() {

  }

  @Provides @Singleton Gson provideGson() {
    return new Gson();
  }

  @Provides @Singleton JsonSerializer provideJsonSerializer(Gson gson) {
    return new GsonSerializer(gson);
  }
}
