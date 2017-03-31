package com.zireck.remotecraft.infrastructure.protocol.mapper;

import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.protocol.base.type.ServerProtocol;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class ServerProtocolMapperTest {

  private ServerProtocolMapper serverProtocolMapper;

  @Before public void setUp() throws Exception {
    serverProtocolMapper = new ServerProtocolMapper();
  }

  @Test public void shouldReturnNullWorldEntityGivenNullServer() throws Exception {
    ServerEntity serverEntity = serverProtocolMapper.transform(null);

    assertThat(serverEntity, nullValue());
  }

  @Test public void shouldProperlyMapServerToWorldEntity() throws Exception {
    ServerProtocol serverProtocol =
        new ServerProtocol("WLAN_C33C", "127.0.0.1", "iMac", "Mac OS X", "2.4.9", "34344343",
            "Za warudo", "Da beasto");

    ServerEntity serverEntity = serverProtocolMapper.transform(serverProtocol);

    assertThat(serverEntity, notNullValue());
    assertThat(serverEntity, is(instanceOf(ServerEntity.class)));
    assertThat(serverEntity.ssid(), is("WLAN_C33C"));
    assertThat(serverEntity.ip(), is("127.0.0.1"));
    assertThat(serverEntity.hostname(), is("iMac"));
    assertThat(serverEntity.os(), is("Mac OS X"));
    assertThat(serverEntity.version(), is("2.4.9"));
    assertThat(serverEntity.seed(), is("34344343"));
    assertThat(serverEntity.worldName(), is("Za warudo"));
    assertThat(serverEntity.playerName(), is("Da beasto"));
  }
}