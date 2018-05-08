package com.remotecraft.app.domain.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.remotecraft.app.domain.Server;
import java.lang.reflect.Type;

public class ServerDeserializer implements JsonDeserializer<Server> {

  private final GsonBuilder gsonBuilder;
  private final AutoValueGsonTypeAdapterFactory autoValueGsonTypeAdapterFactory;

  public ServerDeserializer(GsonBuilder gsonBuilder,
      AutoValueGsonTypeAdapterFactory autoValueGsonTypeAdapterFactory) {
    this.gsonBuilder = gsonBuilder;
    this.autoValueGsonTypeAdapterFactory = autoValueGsonTypeAdapterFactory;
  }

  @Override
  public Server deserialize(String serializedServer) {
    Gson gson = gsonBuilder
        .registerTypeAdapterFactory(autoValueGsonTypeAdapterFactory)
        .create();

    Type type = new TypeToken<Server>() {}.getType();
    return gson.fromJson(serializedServer, type);
  }
}
