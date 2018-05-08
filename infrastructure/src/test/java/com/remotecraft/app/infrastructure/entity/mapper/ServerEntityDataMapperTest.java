package com.remotecraft.app.infrastructure.entity.mapper;

import com.remotecraft.app.domain.Server;
import com.remotecraft.app.infrastructure.entity.ServerEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class ServerEntityDataMapperTest {

  private ServerEntityDataMapper serverEntityDataMapper;

  @Before
  public void setUp() throws Exception {
    serverEntityDataMapper = new ServerEntityDataMapper();
  }

  @Test
  public void shouldReturnNullValueGivenANullServerEntity() throws Exception {
    Server server = serverEntityDataMapper.transform(null);

    assertThat(server, nullValue());
  }

  @Test
  public void shouldProperlyMapServerEntityIntoServer() throws Exception {
    ServerEntity serverEntity = ServerEntity.builder()
        .ssid("WLAN_CCQ4")
        .ip("127.0.0.1")
        .hostname("iMac")
        .os("Mac OS X")
        .version("1.4.2")
        .seed("1234567890")
        .worldName("Etho's Server")
        .playerName("Etho")
        .encodedWorldImage("base64image")
        .build();

    Server server = serverEntityDataMapper.transform(serverEntity);

    assertThat(server, notNullValue());
    assertThat(server, instanceOf(Server.class));
    assertThat(server.ssid(), is("WLAN_CCQ4"));
    assertThat(server.ip(), is("127.0.0.1"));
    assertThat(server.hostname(), is("iMac"));
    assertThat(server.os(), is("Mac OS X"));
    assertThat(server.version(), is("1.4.2"));
    assertThat(server.seed(), is("1234567890"));
    assertThat(server.worldName(), is("Etho's Server"));
    assertThat(server.playerName(), is("Etho"));
    assertThat(server.encodedWorldImage(), is("base64image"));
  }
}