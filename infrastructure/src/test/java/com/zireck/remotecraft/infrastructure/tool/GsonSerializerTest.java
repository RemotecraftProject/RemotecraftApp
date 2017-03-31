package com.zireck.remotecraft.infrastructure.tool;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class) public class GsonSerializerTest {

  private GsonSerializer gsonSerializer;
  private Gson gson;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    gson = new Gson();
    gsonSerializer = new GsonSerializer(gson);
  }

  @Test public void shouldProperlySerializeAnyGivenValue() throws Exception {
    Integer integer = new Integer(359);
    String serializedObject = gsonSerializer.toJson(integer);

    assertThat(serializedObject).isNotNull();
    assertThat(serializedObject).isInstanceOf(String.class);
    assertThat(serializedObject).isEqualTo("359");
  }

  @Test public void shouldProperlyDeserializeAnyGivenObject() throws Exception {
    Integer deserializedObject = gsonSerializer.fromJson("1080", Integer.class);

    assertThat(deserializedObject).isNotNull();
    assertThat(deserializedObject).isInstanceOf(Integer.class);
    assertThat(deserializedObject).isEqualTo(1080);
  }
}