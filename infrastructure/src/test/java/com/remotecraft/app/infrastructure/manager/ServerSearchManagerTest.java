package com.remotecraft.app.infrastructure.manager;

import com.remotecraft.app.infrastructure.entity.NetworkAddressEntity;
import com.remotecraft.app.infrastructure.entity.ServerEntity;
import com.remotecraft.app.infrastructure.network.NetworkPacket;
import com.remotecraft.app.infrastructure.protocol.ProtocolMessageComposer;
import com.remotecraft.app.infrastructure.protocol.base.Message;
import com.remotecraft.app.infrastructure.protocol.base.type.ServerProtocol;
import com.remotecraft.app.infrastructure.protocol.mapper.MessageJsonMapper;
import com.remotecraft.app.infrastructure.protocol.mapper.ServerProtocolMapper;
import com.remotecraft.app.infrastructure.protocol.messages.CommandMessage;
import com.remotecraft.app.infrastructure.provider.ServerSearchSettings;
import com.remotecraft.app.infrastructure.provider.broadcastaddress.BroadcastAddressProvider;
import com.remotecraft.app.infrastructure.network.NetworkConnectionlessTransmitter;
import com.remotecraft.app.infrastructure.validation.ServerMessageValidator;
import io.reactivex.Observable;
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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    NetworkAddressEntity networkAddressEntity = NetworkAddressEntity.builder()
        .ip("192.168.1.15")
        .port(8889)
        .build();
    mockServerSearchSettings = new ServerSearchSettings.Builder()
        .port(8889)
        .broadcastAddress(networkAddressEntity)
        .retryCount(5)
        .retryDelayMultiplier(0)
        .responseBufferSize(15000)
        .timeout(1500)
        .subscribers(1)
        .build();

    when(mockNetworkConnectionlessTransmitter.receive(any(NetworkPacket.class))).thenReturn(
        new NetworkPacket(""));

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

    Observable<ServerEntity> serverEntityObservable = serverSearchManager.searchServer();

    ServerEntity serverEntity = serverEntityObservable.blockingFirst();
    assertThat(serverEntity, notNullValue());
    assertThat(serverEntity.worldName(), is("Za warudo"));

    verify(mockNetworkConnectionlessTransmitter, times(1)).setBroadcast(true);
    verify(mockProtocolMessageComposer, atLeastOnce()).composeGetServerInfoCommand();
    verify(mockMessageJsonMapper, atLeastOnce()).transformMessage(any(CommandMessage.class));
    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).send(any(NetworkPacket.class));

    verify(mockBroadcastAddressProvider, only()).getBroadcastAddresses();

    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).setTimeout(
        mockServerSearchSettings.getTimeout());
    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).receive(any(NetworkPacket.class));
    verify(mockMessageJsonMapper).transformMessage(anyString());
  }

  @Test(expected = RuntimeException.class)
  public void shouldNotFindServerGivenAnUnsuccessfulResponse() throws Exception {
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

    Observable<ServerEntity> serverEntityObservable = serverSearchManager.searchServer();

    ServerEntity serverEntity = serverEntityObservable.blockingFirst();
  }

  @Test(expected = RuntimeException.class) public void shouldNotFindServerGivenANonServerResponse()
      throws Exception {
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

    Observable<ServerEntity> serverEntityObservable = serverSearchManager.searchServer();

    ServerEntity serverEntity = serverEntityObservable.blockingFirst();
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

    NetworkAddressEntity networkAddressEntity = NetworkAddressEntity.builder()
        .ip("127.0.0.1")
        .port(9999)
        .build();
    Observable<ServerEntity> serverEntityObservable = serverSearchManager.searchServer(networkAddressEntity);

    ServerEntity serverEntity = serverEntityObservable.blockingFirst();
    assertThat(serverEntity, notNullValue());
    assertThat(serverEntity.worldName(), is("Za warudo"));

    verify(mockNetworkConnectionlessTransmitter, never()).setBroadcast(true);
    verify(mockProtocolMessageComposer, atLeastOnce()).composeGetServerInfoCommand();
    verify(mockMessageJsonMapper, atLeastOnce()).transformMessage(any(CommandMessage.class));
    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).send(any(NetworkPacket.class));

    verify(mockBroadcastAddressProvider, never()).getBroadcastAddresses();

    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).setTimeout(
        mockServerSearchSettings.getTimeout());
    verify(mockNetworkConnectionlessTransmitter, atLeastOnce()).receive(any(NetworkPacket.class));
    verify(mockMessageJsonMapper).transformMessage(anyString());
  }

  private ServerProtocol getMockServerProtocol() {
    return new ServerProtocol.Builder()
        .ssid("WLAN_C33C")
        .ip("127.0.0.1")
        .hostname("iMac")
        .os("Mac OS X")
        .version("v1.8")
        .seed("123456789")
        .worldName("Za warudo")
        .playerName("Etho")
        .encodedWorldImage("base64image")
        .build();
  }

  private ServerEntity getMockServerEntity() {
    return ServerEntity.builder()
        .ssid("WLAN_C33C")
        .ip("127.0.0.1")
        .hostname("iMac")
        .os("Mac OS X")
        .version("v1.8")
        .seed("123456789")
        .worldName("Za warudo")
        .playerName("Etho")
        .encodedWorldImage("base64image")
        .build();
  }
}