package com.zireck.remotecraft.dagger.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ToolsModule {

  public ToolsModule() {

  }

  @Provides @Singleton Gson provideGson() {
    return new Gson();
  }

  @Provides @Singleton GsonBuilder provideGsonBuilder() {
    return new GsonBuilder();
  }
}
