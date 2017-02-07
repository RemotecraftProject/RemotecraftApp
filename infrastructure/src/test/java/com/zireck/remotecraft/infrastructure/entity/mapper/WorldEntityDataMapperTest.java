package com.zireck.remotecraft.infrastructure.entity.mapper;

import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class WorldEntityDataMapperTest {

  private WorldEntityDataMapper worldEntityDataMapper;

  @Before public void setUp() throws Exception {
    worldEntityDataMapper = new WorldEntityDataMapper();
  }

  @Test public void shouldReturnNullValueGivenANullWorldEntity() throws Exception {
    World world = worldEntityDataMapper.transform(null);

    assertThat(world, nullValue());
  }

  @Test public void shouldProperlyMapWorldEntityIntoWorld() throws Exception {
    WorldEntity worldEntity = new WorldEntity.Builder()
        .version("1.4.2")
        .ssid("WLAN_CCQ4")
        .ip("127.0.0.1")
        .name("Etho's World")
        .player("Etho")
        .build();

    World world = worldEntityDataMapper.transform(worldEntity);

    assertThat(world, notNullValue());
    assertThat(world, is(World.class));
    assertThat(world.getVersion(), is("1.4.2"));
    assertThat(world.getSsid(), is("WLAN_CCQ4"));
    assertThat(world.getIp(), is("127.0.0.1"));
    assertThat(world.getName(), is("Etho's World"));
    assertThat(world.getPlayer(), is("Etho"));
  }
}