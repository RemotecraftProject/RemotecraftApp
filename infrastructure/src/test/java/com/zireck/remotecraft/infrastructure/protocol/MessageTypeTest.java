package com.zireck.remotecraft.infrastructure.protocol;

import com.zireck.remotecraft.infrastructure.protocol.type.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class MessageTypeTest {

  @Test public void shouldReturnValidStringForACertainMessageType() throws Exception {
    String messageTypeString = MessageType.INFO.toString();

    assertThat(messageTypeString, notNullValue());
    assertThat(messageTypeString, is(instanceOf(String.class)));
    assertThat(messageTypeString, is("info"));
  }
}