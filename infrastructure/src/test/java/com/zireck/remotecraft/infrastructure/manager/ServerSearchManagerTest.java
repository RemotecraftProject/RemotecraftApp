package com.zireck.remotecraft.infrastructure.manager;

import com.zireck.remotecraft.infrastructure.entity.NetworkAddressEntity;
import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.protocol.ProtocolMessageComposer;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.base.type.ServerProtocol;
import com.zireck.remotecraft.infrastructure.protocol.mapper.MessageJsonMapper;
import com.zireck.remotecraft.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.zireck.remotecraft.infrastructure.protocol.messages.CommandMessage;
import com.zireck.remotecraft.infrastructure.provider.ServerSearchSettings;
import com.zireck.remotecraft.infrastructure.provider.broadcastaddress.BroadcastAddressProvider;
import com.zireck.remotecraft.infrastructure.tool.NetworkConnectionlessTransmitter;
import com.zireck.remotecraft.infrastructure.validation.ServerMessageValidator;
import io.reactivex.Maybe;
import java.net.DatagramPacket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class ServerSearchManagerTest {

  private ServerSearchManager serverSearchManager;

  private ServerSearchSettings mockServerSearchSettings;
  @Mock private NetworkConnectionlessTransmitter mockNetworkConnectionlessTransmitter;
  @Mock private BroadcastAddressProvider mockBroadcastAddressProvider;
  @Mock private ProtocolMessageComposer mockProtocolMessageComposer;
  @Mock private MessageJsonMapper mockMessageJsonMapper;
  @Mock private ServerProtocolMapper mockServerProtocolMapper;
  @Mock private ServerMessageValidator mockServerMessageValidator;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockServerSearchSettings = new ServerSearchSettings.Builder()
        .retryDelayMultiplier(0)
        .build();

    serverSearchManager =
        new ServerSearchManager(mockServerSearchSettings, mockNetworkConnectionlessTransmitter,
            mockBroadcastAddressProvider, mockProtocolMessageComposer, mockMessageJsonMapper,
            mockServerProtocolMapper, mockServerMessageValidator);
  }

  @Test public void shouldFindServerGivenAValidNetworkResponse() throws Exception {
    final ServerProtocol mockServerProtocol = getMockServerProtocol();
    final ServerEntity mockServerEntity = getMockServerEntity();

    CommandMessage mockCommandMessage = mock(CommandMessage.class);
    when(mockProtocolMessageComposer.composeGetServerInfoCommand()).thenReturn(mockCommandMessage);
    when(mockMessageJsonMapper.transformMessage(mockCommandMessage)).thenReturn("whatever");

    Message mockMessage = mock(Message.class);
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServerProtocol);
    when(mockMessageJsonMapper.transformMessage(anyString())).thenReturn(mockMessage);

    when(mockServerMessageValidator.isValid(mockMessage)).thenReturn(true);
    when(mockServerMessageValidator.cast(mockMessage)).thenReturn(mockServerProtocol);
    when(mockServerProtocolMapper.transform(mockServerProtocol)).thenReturn(mockServerEntity);

    Maybe<ServerEntity> serverEntityMaybe = serverSearchManager.searchServer();

    ServerEntity serverEntity = serverEntityMaybe.blockingGet();
    assertThat(serverEntity, notNullValue());
    assertThat(serverEntity.getWorldName(), is("Za warudo"));

    verify(mockNetworkConnectionlessTransmitter, times(1)).setBroadcast(true);
    verify(mockProtocolMessageComposer, atLeastOnce()).composeGetServerInfoCommand();
    verify(mockMessageJsonMapper, atLeastOnce()).transformMessage(any(CommandMessage.class));
    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).send(any(DatagramPacket.class));

    verify(mockBroadcastAddressProvider, only()).getBroadcastAddresses();

    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).setTimeout(
        mockServerSearchSettings.getTimeout());
    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).receive(any(DatagramPacket.class));
    verify(mockMessageJsonMapper).transformMessage(anyString());
  }

  @Test public void shouldNotFindServerGivenAnUnsuccessfulResponse() throws Exception {
    final ServerProtocol mockServerProtocol = getMockServerProtocol();
    final ServerEntity mockServerEntity = getMockServerEntity();

    CommandMessage mockCommandMessage = mock(CommandMessage.class);
    when(mockProtocolMessageComposer.composeGetServerInfoCommand()).thenReturn(mockCommandMessage);
    when(mockMessageJsonMapper.transformMessage(mockCommandMessage)).thenReturn("whatever");

    Message mockMessage = mock(Message.class);
    when(mockMessage.isSuccess()).thenReturn(false);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServerProtocol);
    when(mockMessageJsonMapper.transformMessage(anyString())).thenReturn(mockMessage);

    when(mockServerMessageValidator.isValid(mockMessage)).thenReturn(true);
    when(mockServerMessageValidator.cast(mockMessage)).thenReturn(mockServerProtocol);
    when(mockServerProtocolMapper.transform(mockServerProtocol)).thenReturn(mockServerEntity);

    Maybe<ServerEntity> serverEntityMaybe = serverSearchManager.searchServer();

    ServerEntity serverEntity = serverEntityMaybe.blockingGet();
    assertThat(serverEntity, nullValue());
    verify(mockNetworkConnectionlessTransmitter, atLeast(5)).send(any(DatagramPacket.class));
    verifyZeroInteractions(mockServerMessageValidator, mockServerProtocolMapper);
  }

  @Test public void shouldNotFindServerGivenANonServerResponse() throws Exception {
    final ServerProtocol mockServerProtocol = getMockServerProtocol();
    final ServerEntity mockServerEntity = getMockServerEntity();

    CommandMessage mockCommandMessage = mock(CommandMessage.class);
    when(mockProtocolMessageComposer.composeGetServerInfoCommand()).thenReturn(mockCommandMessage);
    when(mockMessageJsonMapper.transformMessage(mockCommandMessage)).thenReturn("whatever");

    Message mockMessage = mock(Message.class);
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(false);
    when(mockMessage.getServer()).thenReturn(mockServerProtocol);
    when(mockMessageJsonMapper.transformMessage(anyString())).thenReturn(mockMessage);

    when(mockServerMessageValidator.isValid(mockMessage)).thenReturn(true);
    when(mockServerMessageValidator.cast(mockMessage)).thenReturn(mockServerProtocol);
    when(mockServerProtocolMapper.transform(mockServerProtocol)).thenReturn(mockServerEntity);

    Maybe<ServerEntity> serverEntityMaybe = serverSearchManager.searchServer();

    ServerEntity serverEntity = serverEntityMaybe.blockingGet();
    assertThat(serverEntity, nullValue());
    verify(mockNetworkConnectionlessTransmitter, atLeast(5)).send(any(DatagramPacket.class));
    verifyZeroInteractions(mockServerMessageValidator, mockServerProtocolMapper);
  }

  @Test public void shouldFindServerForIpAddressGivenAValidNetworkResponse() throws Exception {
    final ServerProtocol mockServerProtocol = getMockServerProtocol();
    final ServerEntity mockServerEntity = getMockServerEntity();

    CommandMessage mockCommandMessage = mock(CommandMessage.class);
    when(mockProtocolMessageComposer.composeGetServerInfoCommand()).thenReturn(mockCommandMessage);
    when(mockMessageJsonMapper.transformMessage(mockCommandMessage)).thenReturn("whatever");

    Message mockMessage = mock(Message.class);
    when(mockMessage.isSuccess()).thenReturn(true);
    when(mockMessage.isServer()).thenReturn(true);
    when(mockMessage.getServer()).thenReturn(mockServerProtocol);
    when(mockMessageJsonMapper.transformMessage(anyString())).thenReturn(mockMessage);

    when(mockServerMessageValidator.isValid(mockMessage)).thenReturn(true);
    when(mockServerMessageValidator.cast(mockMessage)).thenReturn(mockServerProtocol);
    when(mockServerProtocolMapper.transform(mockServerProtocol)).thenReturn(mockServerEntity);

    NetworkAddressEntity networkAddressEntity = new NetworkAddressEntity.Builder()
        .with("127.0.0.1")
        .build();
    Maybe<ServerEntity> serverEntityMaybe = serverSearchManager.searchServer(networkAddressEntity);

    ServerEntity serverEntity = serverEntityMaybe.blockingGet();
    assertThat(serverEntity, notNullValue());
    assertThat(serverEntity.getWorldName(), is("Za warudo"));

    verify(mockNetworkConnectionlessTransmitter, never()).setBroadcast(true);
    verify(mockProtocolMessageComposer, atLeastOnce()).composeGetServerInfoCommand();
    verify(mockMessageJsonMapper, atLeastOnce()).transformMessage(any(CommandMessage.class));
    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).send(any(DatagramPacket.class));

    verify(mockBroadcastAddressProvider, never()).getBroadcastAddresses();

    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).setTimeout(
        mockServerSearchSettings.getTimeout());
    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).receive(any(DatagramPacket.class));
    verify(mockMessageJsonMapper).transformMessage(anyString());
  }

  private ServerProtocol getMockServerProtocol() {
    return new ServerProtocol("WLAN_C33C", "127.0.0.1", "iMac", "Mac OS X", "v1.8", "123456789",
        "Za warudo", "Zireck");
  }

  private ServerEntity getMockServerEntity() {
    return new ServerEntity.Builder()
        .ssid("WLAN_C33C")
        .ip("127.0.0.1")
        .hostname("iMac")
        .os("Mac OS X")
        .version("v1.8")
        .seed("123456789")
        .worldName("Za warudo")
        .playerName("Zireck")
        .build();
  }
}