package com.remotecraft.app.infrastructure.protocol.mapper;

import com.remotecraft.app.infrastructure.protocol.base.Message;
import com.remotecraft.app.infrastructure.protocol.messages.ErrorMessage;
import com.remotecraft.app.infrastructure.tool.JsonSerializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageJsonMapperTest {

  private MessageJsonMapper messageJsonMapper;

  @Mock private JsonSerializer jsonSerializer;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    messageJsonMapper = new MessageJsonMapper(jsonSerializer);
  }

  @Test
  public void shouldProperlyDeserializeJsonString() throws Exception {
    String someJson = "any value";
    Message mockMessage = mock(Message.class);
    when(jsonSerializer.fromJson(someJson, Message.class)).thenReturn(mockMessage);

    Message message = messageJsonMapper.transformMessage(someJson);

    assertThat(message).isNotNull();
    assertThat(message).isInstanceOf(Message.class);
    verify(jsonSerializer, times(1)).fromJson(someJson, Message.class);
  }

  @Test
  public void shouldProperlySerializeMessage() throws Exception {
    ErrorMessage errorMessage = new ErrorMessage.Builder().with(404).and("Not found!").build();
    String fakeSerializedJson = "{status:fail, message:not found!}";
    when(jsonSerializer.toJson(errorMessage)).thenReturn(fakeSerializedJson);

    String message = messageJsonMapper.transformMessage(errorMessage);

    assertThat(message).isNotNull();
    assertThat(message.length()).isEqualTo(fakeSerializedJson.length());
  }
}