package com.zireck.remotecraft.infrastructure.tool;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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

    assertThat(serializedObject, notNullValue());
    assertThat(serializedObject, is(instanceOf(String.class)));
    assertThat(serializedObject, is("359"));
  }

  @Test public void shouldProperlyDeserializeAnyGivenObject() throws Exception {
    Integer deserializedObject = gsonSerializer.fromJson("1080", Integer.class);

    assertThat(deserializedObject, notNullValue());
    assertThat(deserializedObject, is(instanceOf(Integer.class)));
    assertThat(deserializedObject, is(1080));
  }
}