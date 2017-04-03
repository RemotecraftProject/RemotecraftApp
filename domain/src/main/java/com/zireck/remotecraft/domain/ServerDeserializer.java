package com.zireck.remotecraft.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class ServerDeserializer {

  private final GsonBuilder gsonBuilder;
  private final AutoValueGsonTypeAdapterFactory autoValueGsonTypeAdapterFactory;

  public ServerDeserializer(GsonBuilder gsonBuilder,
      AutoValueGsonTypeAdapterFactory autoValueGsonTypeAdapterFactory) {
    this.gsonBuilder = gsonBuilder;
    this.autoValueGsonTypeAdapterFactory = autoValueGsonTypeAdapterFactory;
  }

  public Server deserialize(String serializedServer) {
    Gson gson = gsonBuilder
        .registerTypeAdapterFactory(autoValueGsonTypeAdapterFactory)
        .create();

    Type type = new TypeToken<Server>() {}.getType();
    return gson.fromJson(serializedServer, type);
  }
}
