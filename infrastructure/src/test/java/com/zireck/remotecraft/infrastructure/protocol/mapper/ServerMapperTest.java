package com.zireck.remotecraft.infrastructure.protocol.mapper;

import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import com.zireck.remotecraft.infrastructure.protocol.data.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class ServerMapperTest {

  private ServerMapper serverMapper;

  @Before public void setUp() throws Exception {
    serverMapper = new ServerMapper();
  }

  @Test public void shouldReturnNullWorldEntityGivenNullServer() throws Exception {
    WorldEntity worldEntity = serverMapper.transform(null);

    assertThat(worldEntity, nullValue());
  }

  @Test public void shouldProperlyMapServerToWorldEntity() throws Exception {
    Server server =
        new Server("WLAN_C33C", "127.0.0.1", "2.4.9", "34344343", "Za warudo", "Da beasto");

    WorldEntity worldEntity = serverMapper.transform(server);

    assertThat(worldEntity, notNullValue());
    assertThat(worldEntity, is(instanceOf(WorldEntity.class)));
    assertThat(worldEntity.getSsid(), is("WLAN_C33C"));
    assertThat(worldEntity.getIp(), is("127.0.0.1"));
    assertThat(worldEntity.getVersion(), is("2.4.9"));
    assertThat(worldEntity.getSeed(), is("34344343"));
    assertThat(worldEntity.getName(), is("Za warudo"));
    assertThat(worldEntity.getPlayer(), is("Da beasto"));
  }
}