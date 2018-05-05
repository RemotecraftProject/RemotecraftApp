package com.remotecraft.app.domain.util;

public interface JsonDeserializer<T> {
  T deserialize(String serializedJson);
}
