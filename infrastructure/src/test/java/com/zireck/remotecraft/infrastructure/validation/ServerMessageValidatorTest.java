package com.zireck.remotecraft.infrastructure.validation;

import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.base.Server;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class ServerMessageValidatorTest {

  private ServerMessageValidator serverMessageValidator;

  @Mock private Message mockMessage;
  @Mock private Server mockServer;

  @Before public void setUp() throws Exception {
    serverMessageValidator = new ServerMessageValidator();
  }

  @Test public void shouldProperlyCastMessageIntoServer() throws Exception {
    when(mockMessage.getServer()).thenReturn(mockServer);

    Server server = serverMessageValidator.cast(mockMessage);

    assertThat(server, notNullValue());
    assertThat(server, is(instanceOf(Server.class)));
    verify(mockMessage, times(1)).getServer();
    verifyNoMoreInteractions(mockMessage);
  }

  @Test public void shouldCheckInvalidMessageGivenUnsuccessfulMessage() throws Exception {
    when(mockMessage.isSuccess()).thenReturn(false);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid, is(false));
    assertThat(invalidServerData, notNullValue());
    assertThat(invalidServerData.size(), is(1));
  }

  @Test public void shouldCheckInvalidMessageGivenANotServerMessage() throws Exception {
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(false);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid, is(false));
    assertThat(invalidServerData, notNullValue());
    assertThat(invalidServerData.size(), is(1));
  }

  @Test public void shouldCheckInvalidMessageGivenNullIp() throws Exception {
    when(mockServer.getIp()).thenReturn(null);
    when(mockServer.getSeed()).thenReturn("42356735435435345");
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServer);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid, is(false));
    assertThat(invalidServerData, notNullValue());
    assertThat(invalidServerData.size(), is(1));
  }

  @Test public void shouldCheckInvalidMessageGivenEmptyIp() throws Exception {
    when(mockServer.getIp()).thenReturn("");
    when(mockServer.getSeed()).thenReturn("656478878432054");
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServer);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid, is(false));
    assertThat(invalidServerData, notNullValue());
    assertThat(invalidServerData.size(), is(1));
  }

  @Test public void shouldCheckInvalidMessageGivenNullSeed() throws Exception {
    when(mockServer.getIp()).thenReturn("192.168.2.42");
    when(mockServer.getSeed()).thenReturn(null);
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServer);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid, is(false));
    assertThat(invalidServerData, notNullValue());
    assertThat(invalidServerData.size(), is(1));
  }

  @Test public void shouldCheckInvalidMessageGivenEmptySeed() throws Exception {
    when(mockServer.getIp()).thenReturn("192.168.99.32");
    when(mockServer.getSeed()).thenReturn("");
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServer);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid, is(false));
    assertThat(invalidServerData, notNullValue());
    assertThat(invalidServerData.size(), is(1));
  }

  @Test public void shouldReturnTwoInvalidDataWhenIpAndSeedAreInvalid() throws Exception {
    when(mockServer.getIp()).thenReturn("");
    when(mockServer.getSeed()).thenReturn(null);
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServer);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid, is(false));
    assertThat(invalidServerData, notNullValue());
    assertThat(invalidServerData.size(), is(2));
  }
}