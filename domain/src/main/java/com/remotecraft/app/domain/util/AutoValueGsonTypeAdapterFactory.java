package com.remotecraft.app.domain.util;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.remotecraft.app.domain.Server;

public class AutoValueGsonTypeAdapterFactory implements TypeAdapterFactory {

  public AutoValueGsonTypeAdapterFactory() {

  }

  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    Class<? super T> rawType = type.getRawType();

    if (rawType.equals(Server.class)) {
      return (TypeAdapter<T>) Server.typeAdapter(gson);
    }

    return null;
  }
}
