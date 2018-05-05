package com.remotecraft.app.infrastructure.validation;

import com.remotecraft.app.infrastructure.protocol.base.Message;
import com.remotecraft.app.infrastructure.protocol.base.type.ServerProtocol;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class ServerMessageValidatorTest {

  private ServerMessageValidator serverMessageValidator;

  @Mock private Message mockMessage;
  @Mock private ServerProtocol mockServerProtocol;

  @Before public void setUp() throws Exception {
    serverMessageValidator = new ServerMessageValidator();
  }

  @Test public void shouldProperlyCastMessageIntoServer() throws Exception {
    when(mockMessage.getServer()).thenReturn(mockServerProtocol);

    ServerProtocol serverProtocol = serverMessageValidator.cast(mockMessage);

    assertThat(serverProtocol).isNotNull();
    assertThat(serverProtocol).isInstanceOf(ServerProtocol.class);
    verify(mockMessage, times(1)).getServer();
    verifyNoMoreInteractions(mockMessage);
  }

  @Test public void shouldCheckInvalidMessageGivenUnsuccessfulMessage() throws Exception {
    when(mockMessage.isSuccess()).thenReturn(false);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid).isFalse();
    assertThat(invalidServerData).isNotNull();
    assertThat(invalidServerData.size()).isEqualTo(1);
  }

  @Test public void shouldCheckInvalidMessageGivenANotServerMessage() throws Exception {
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(false);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid).isFalse();
    assertThat(invalidServerData).isNotNull();
    assertThat(invalidServerData.size()).isEqualTo(1);
  }

  @Test public void shouldCheckInvalidMessageGivenNullIp() throws Exception {
    when(mockServerProtocol.getIp()).thenReturn(null);
    when(mockServerProtocol.getSeed()).thenReturn("42356735435435345");
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServerProtocol);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid).isFalse();
    assertThat(invalidServerData).isNotNull();
    assertThat(invalidServerData.size()).isEqualTo(1);
  }

  @Test public void shouldCheckInvalidMessageGivenEmptyIp() throws Exception {
    when(mockServerProtocol.getIp()).thenReturn("");
    when(mockServerProtocol.getSeed()).thenReturn("656478878432054");
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServerProtocol);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid).isFalse();
    assertThat(invalidServerData).isNotNull();
    assertThat(invalidServerData.size()).isEqualTo(1);
  }

  @Test public void shouldCheckInvalidMessageGivenNullSeed() throws Exception {
    when(mockServerProtocol.getIp()).thenReturn("192.168.2.42");
    when(mockServerProtocol.getSeed()).thenReturn(null);
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServerProtocol);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid).isFalse();
    assertThat(invalidServerData).isNotNull();
    assertThat(invalidServerData.size()).isEqualTo(1);
  }

  @Test public void shouldCheckInvalidMessageGivenEmptySeed() throws Exception {
    when(mockServerProtocol.getIp()).thenReturn("192.168.99.32");
    when(mockServerProtocol.getSeed()).thenReturn("");
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServerProtocol);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid).isFalse();
    assertThat(invalidServerData).isNotNull();
    assertThat(invalidServerData.size()).isEqualTo(1);
  }

  @Test public void shouldReturnTwoInvalidDataWhenIpAndSeedAreInvalid() throws Exception {
    when(mockServerProtocol.getIp()).thenReturn("");
    when(mockServerProtocol.getSeed()).thenReturn(null);
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServerProtocol);

    boolean isValid = serverMessageValidator.isValid(mockMessage);
    List<InvalidServerData> invalidServerData = serverMessageValidator.getInvalidServerData();

    assertThat(isValid).isFalse();
    assertThat(invalidServerData).isNotNull();
    assertThat(invalidServerData.size()).isEqualTo(2);
  }
}