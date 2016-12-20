package com.zireck.remotecraft.mapper;

import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.model.WorldModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class WorldModelDataMapperTest {

  private static final String FAKE_WORLD_NAME = "Super World";
  private static final String FAKE_PLAYER_NAME = "Etho";
  private static final String FAKE_IP = "127.0.0.1";
  private static final String FAKE_SSID = "WLAN_C33C";
  private static final String FAKE_VERSION = "1.7.12";

  private WorldModelDataMapper worldModelDataMapper;

  @Before public void setUp() throws Exception {
    worldModelDataMapper = new WorldModelDataMapper();
  }

  @Test public void shouldNotReturnNullWorldModelWhenMappingValidWorld() throws Exception {
    World world = getFakeWorld();

    WorldModel worldModel = worldModelDataMapper.transform(world);

    assertNotNull(worldModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenMappingNullWorld() throws Exception {
    World world = null;
    worldModelDataMapper.transform(world);
  }

  @Test public void shouldReturnWorldModelInstanceWhenMappingWorld() throws Exception {
    World world = getFakeWorld();

    WorldModel worldModel = worldModelDataMapper.transform(world);

    assertThat(worldModel, is(instanceOf(WorldModel.class)));
  }

  @Test public void shouldTransformProperlyAllFieldsWhenMappingWorld() throws Exception {
    World world = getFakeWorld();

    WorldModel worldModel = worldModelDataMapper.transform(world);

    assertThat(FAKE_WORLD_NAME, is(worldModel.getName()));
    assertThat(FAKE_PLAYER_NAME, is(worldModel.getPlayer()));
    assertThat(FAKE_IP, is(worldModel.getIp()));
    assertThat(FAKE_SSID, is(worldModel.getSsid()));
    assertThat(FAKE_VERSION, is(worldModel.getVersion()));
  }

  @Test public void shouldReturnEmptyWorldModelCollectionWhenMappingEmptyWorldCollection()
      throws Exception {
    Collection<WorldModel> worldModels = worldModelDataMapper.transform(Collections.emptyList());

    assertThat(worldModels, is(Collections.emptyList()));
    assertTrue(worldModels.isEmpty());
  }

  @Test public void shouldReturnTheSameAmountOfElementsWhenMappingWorldCollection()
      throws Exception {
    ArrayList<World> worlds = new ArrayList<>();
    worlds.add(mock(World.class));
    worlds.add(mock(World.class));
    worlds.add(mock(World.class));

    Collection<WorldModel> worldModels = worldModelDataMapper.transform(worlds);

    assertNotNull(worldModels);
    assertThat(worldModels.size(), is(3));
  }

  @Test public void shouldReturnWorldModelInstanceCollectionWhenMappingWorldCollection()
      throws Exception {
    ArrayList<World> worlds = new ArrayList<>();
    worlds.add(mock(World.class));
    worlds.add(mock(World.class));
    worlds.add(mock(World.class));

    ArrayList<WorldModel> worldModels =
        (ArrayList<WorldModel>) worldModelDataMapper.transform(worlds);

    assertThat(worldModels.get(0), is(instanceOf(WorldModel.class)));
    assertThat(worldModels.get(1), is(instanceOf(WorldModel.class)));
    assertThat(worldModels.get(2), is(instanceOf(WorldModel.class)));
  }

  private World getFakeWorld() {
    return new World.Builder()
        .name(FAKE_WORLD_NAME)
        .player(FAKE_PLAYER_NAME)
        .ip(FAKE_IP)
        .ssid(FAKE_SSID)
        .version(FAKE_VERSION)
        .build();
  }
}