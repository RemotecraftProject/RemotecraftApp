package com.zireck.remotecraft.infrastructure.protocol.mapper;

import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.protocol.base.type.ServerProtocol;
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

  @Test public void shouldReturnNullWorldEntityGivenNullServer() throws Exception {
    ServerEntity serverEntity = serverProtocolMapper.transform(null);

    assertThat(serverEntity).isNull();
  }

  @Test public void shouldProperlyMapServerToWorldEntity() throws Exception {
    ServerProtocol serverProtocol =
        new ServerProtocol("WLAN_C33C", "127.0.0.1", "iMac", "Mac OS X", "2.4.9", "34344343",
            "Za warudo", "Da beasto");

    ServerEntity serverEntity = serverProtocolMapper.transform(serverProtocol);

    assertThat(serverEntity).isNotNull();
    assertThat(serverEntity).isInstanceOf(ServerEntity.class);
    assertThat(serverEntity.ssid()).isEqualTo("WLAN_C33C");
    assertThat(serverEntity.ip()).isEqualTo("127.0.0.1");
    assertThat(serverEntity.hostname()).isEqualTo("iMac");
    assertThat(serverEntity.os()).isEqualTo("Mac OS X");
    assertThat(serverEntity.version()).isEqualTo("2.4.9");
    assertThat(serverEntity.seed()).isEqualTo("34344343");
    assertThat(serverEntity.worldName()).isEqualTo("Za warudo");
    assertThat(serverEntity.playerName()).isEqualTo("Da beasto");
  }
}