package com.remotecraft.app.dagger.modules;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.remotecraft.app.dagger.qualifiers.PlayerAvatarSize;
import com.remotecraft.app.dagger.qualifiers.PlayerAvatarUrl;
import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.util.AutoValueGsonTypeAdapterFactory;
import com.remotecraft.app.domain.util.JsonDeserializer;
import com.remotecraft.app.domain.util.ServerDeserializer;
import com.remotecraft.app.infrastructure.tool.GsonSerializer;
import com.remotecraft.app.infrastructure.tool.ImageLoader;
import com.remotecraft.app.infrastructure.tool.JsonSerializer;
import com.remotecraft.app.infrastructure.tool.PicassoImageLoader;
import com.remotecraft.app.tools.UriParser;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ToolsModule {

  public ToolsModule() {

  }

  @Provides @Singleton @PlayerAvatarUrl String providePlayerAvatarUrl() {
    return "https://minotar.net/helm/%s/%s.png";
  }

  @Provides @Singleton @PlayerAvatarSize int providePlayerAvatarSize() {
    return 100;
  }

  @Provides @Singleton Picasso providePicasso(Context context) {
    return Picasso.with(context);
  }

  @Provides @Singleton ImageLoader provideImageLoader(Picasso picasso) {
    return new PicassoImageLoader(picasso);
  }

  @Provides @Singleton Gson provideGson() {
    return new Gson();
  }

  @Provides @Singleton GsonBuilder provideGsonBuilder() {
    return new GsonBuilder();
  }

  @Provides @Singleton JsonSerializer provideJsonSerializer(Gson gson) {
    return new GsonSerializer(gson);
  }

  @Provides @Singleton AutoValueGsonTypeAdapterFactory autoValueGsonTypeAdapterFactory() {
    return new AutoValueGsonTypeAdapterFactory();
  }

  @Provides @Singleton JsonDeserializer<Server> serverDeserializer(GsonBuilder gsonBuilder,
      AutoValueGsonTypeAdapterFactory autoValueGsonTypeAdapterFactory) {
    return new ServerDeserializer(gsonBuilder, autoValueGsonTypeAdapterFactory);
  }

  @Provides @Singleton UriParser provideUriParser() {
    return new UriParser();
  }
}
