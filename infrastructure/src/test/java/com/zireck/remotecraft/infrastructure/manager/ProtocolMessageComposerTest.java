package com.zireck.remotecraft.infrastructure.manager;

import com.zireck.remotecraft.infrastructure.protocol.ProtocolMessageComposer;
import com.zireck.remotecraft.infrastructure.protocol.messages.CommandMessage;
import com.zireck.remotecraft.infrastructure.protocol.type.CommandType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class ProtocolMessageComposerTest {

  private ProtocolMessageComposer protocolMessageComposer;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    protocolMessageComposer = new ProtocolMessageComposer();
  }

  @Test public void shouldReturnValidCommandMessage() throws Exception {
    CommandMessage getServerInfoCommand = protocolMessageComposer.composeGetServerInfoCommand();

    assertThat(getServerInfoCommand, notNullValue());
    assertThat(getServerInfoCommand, is(instanceOf(CommandMessage.class)));
    assertThat(getServerInfoCommand.isSuccess(), is(true));
    assertThat(getServerInfoCommand.isCommand(), is(true));
    assertThat(getServerInfoCommand.getCommand(), notNullValue());
    assertThat(getServerInfoCommand.getCommand().getName(),
        containsString(CommandType.GET_SERVER_INFO.toString()));
    assertThat(getServerInfoCommand.isInfo(), is(false));
    assertThat(getServerInfoCommand.isServer(), is(false));
  }
}