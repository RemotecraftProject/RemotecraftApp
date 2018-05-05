package com.remotecraft.app.infrastructure.protocol.mapper;

import com.remotecraft.app.infrastructure.entity.ServerEntity;
import com.remotecraft.app.infrastructure.protocol.base.type.ServerProtocol;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class) public class ServerProtocolMapperTest {

  private ServerProtocolMapper serverProtocolMapper;

  @Before public void setUp() throws Exception {
    serverProtocolMapper = new ServerProtocolMapper();
  }

  @Test public void shouldReturnNullServerEntityGivenNullServerProtocol() throws Exception {
    ServerProtocol serverProtocol = null;

    ServerEntity serverEntity = serverProtocolMapper.transform(serverProtocol);

    assertThat(serverEntity).isNull();
  }

  @Test public void shouldProperlyMapServerProtocolToServerEntity() throws Exception {
    ServerProtocol serverProtocol = new ServerProtocol.Builder()
        .ssid("WLAN_C33C")
        .ip("127.0.0.1")
        .hostname("iMac")
        .os("macOS Sierra")
        .version("2.4.9")
        .seed("34344343")
        .worldName("Za warudo")
        .playerName("Da beasto")
        .encodedWorldImage("base64image")
        .build();

    ServerEntity serverEntity = serverProtocolMapper.transform(serverProtocol);

    assertThat(serverEntity).isNotNull();
    assertThat(serverEntity).isInstanceOf(ServerEntity.class);
    assertThat(serverEntity.ssid()).isEqualTo("WLAN_C33C");
    assertThat(serverEntity.ip()).isEqualTo("127.0.0.1");
    assertThat(serverEntity.hostname()).isEqualTo("iMac");
    assertThat(serverEntity.os()).isEqualTo("macOS Sierra");
    assertThat(serverEntity.version()).isEqualTo("2.4.9");
    assertThat(serverEntity.seed()).isEqualTo("34344343");
    assertThat(serverEntity.worldName()).isEqualTo("Za warudo");
    assertThat(serverEntity.playerName()).isEqualTo("Da beasto");
    assertThat(serverEntity.encodedWorldImage()).isEqualTo("base64image");
  }

  @Test public void shouldReturnNullServerProtocolGivenNullServerEntity() throws Exception {
    ServerEntity serverEntity = null;

    ServerProtocol serverProtocol = serverProtocolMapper.transform(serverEntity);

    assertThat(serverProtocol).isNull();
  }

  @Test public void shouldProperlyMapServerEntityToServerProtocol() throws Exception {
    ServerEntity serverEntity = ServerEntity.builder()
        .ssid("WLAN_C33C")
        .ip("127.0.0.1")
        .hostname("iMac")
        .os("macOS Sierra")
        .version("2.4.9")
        .seed("34344343")
        .worldName("Za warudo")
        .playerName("Da beasto")
        .encodedWorldImage("base64image")
        .build();

    ServerProtocol serverProtocol = serverProtocolMapper.transform(serverEntity);

    assertThat(serverProtocol).isNotNull();
    assertThat(serverProtocol).isInstanceOf(ServerProtocol.class);
    assertThat(serverProtocol.getSsid()).isEqualTo("WLAN_C33C");
    assertThat(serverProtocol.getIp()).isEqualTo("127.0.0.1");
    assertThat(serverProtocol.getHostname()).isEqualTo("iMac");
    assertThat(serverProtocol.getOs()).isEqualTo("macOS Sierra");
    assertThat(serverProtocol.getVersion()).isEqualTo("2.4.9");
    assertThat(serverProtocol.getSeed()).isEqualTo("34344343");
    assertThat(serverProtocol.getWorldName()).isEqualTo("Za warudo");
    assertThat(serverProtocol.getPlayerName()).isEqualTo("Da beasto");
    assertThat(serverProtocol.getEncodedWorldImage()).isEqualTo("base64image");
  }
}