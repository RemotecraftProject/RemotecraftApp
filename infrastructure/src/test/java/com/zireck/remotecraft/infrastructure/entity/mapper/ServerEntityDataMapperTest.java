package com.zireck.remotecraft.infrastructure.entity.mapper;

import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class ServerEntityDataMapperTest {

  private ServerEntityDataMapper serverEntityDataMapper;

  @Before public void setUp() throws Exception {
    serverEntityDataMapper = new ServerEntityDataMapper();
  }

  @Test public void shouldReturnNullValueGivenANullServerEntity() throws Exception {
    Server server = serverEntityDataMapper.transform(null);

    assertThat(server, nullValue());
  }

  @Test public void shouldProperlyMapServerEntityIntoServer() throws Exception {
    ServerEntity serverEntity = new ServerEntity.Builder()
        .ssid("WLAN_CCQ4")
        .ip("127.0.0.1")
        .version("1.4.2")
        .seed("1234567890")
        .worldName("Etho's Server")
        .playerName("Etho")
        .build();

    Server server = serverEntityDataMapper.transform(serverEntity);

    assertThat(server, notNullValue());
    assertThat(server, is(Server.class));
    assertThat(server.getSsid(), is("WLAN_CCQ4"));
    assertThat(server.getIp(), is("127.0.0.1"));
    assertThat(server.getVersion(), is("1.4.2"));
    assertThat(server.getSeed(), is("1234567890"));
    assertThat(server.getWorldName(), is("Etho's Server"));
    assertThat(server.getPlayerName(), is("Etho"));
  }
}