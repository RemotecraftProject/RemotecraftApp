package com.zireck.remotecraft.infrastructure.protocol;

import com.zireck.remotecraft.infrastructure.protocol.enumeration.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class) public class MessageTypeTest {

  @Test public void shouldReturnValidStringForACertainMessageType() throws Exception {
    String messageTypeString = MessageType.INFO.toString();

    assertThat(messageTypeString).isNotNull();
    assertThat(messageTypeString).isInstanceOf(String.class);
    assertThat(messageTypeString).isEqualTo("info");
  }
}