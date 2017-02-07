package com.zireck.remotecraft.infrastructure.protocol.mapper;

import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class MessageJsonMapperTest {

  private MessageJsonMapper messageJsonMapper;

  @Mock private JsonSerializer jsonSerializer;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    messageJsonMapper = new MessageJsonMapper(jsonSerializer);
  }

  @Test public void shouldProperlyDeserializeJsonString() throws Exception {
    String someJson = "any value";
    Message mockMessage = mock(Message.class);
    when(jsonSerializer.fromJson(someJson, Message.class)).thenReturn(mockMessage);

    Message message = messageJsonMapper.transformMessage(someJson);

    assertThat(message, notNullValue());
    assertThat(message, is(instanceOf(Message.class)));
    verify(jsonSerializer, times(1)).fromJson(someJson, Message.class);
  }
}