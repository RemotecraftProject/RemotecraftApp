package com.zireck.remotecraft.mapper;

import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.model.ServerModel;
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

public class ServerModelDataMapperTest {

  private static final String FAKE_SSID = "WLAN_C33C";
  private static final String FAKE_IP = "127.0.0.1";
  private static final String FAKE_VERSION = "1.7.12";
  private static final String FAKE_SEED = "1234567890";
  private static final String FAKE_WORLD_NAME = "Super Server";
  private static final String FAKE_PLAYER_NAME = "Etho";

  private ServerModelDataMapper serverModelDataMapper;

  @Before public void setUp() throws Exception {
    serverModelDataMapper = new ServerModelDataMapper();
  }

  @Test public void shouldNotReturnNullWorldModelWhenMappingValidWorld() throws Exception {
    Server server = getFakeWorld();

    ServerModel serverModel = serverModelDataMapper.transform(server);

    assertNotNull(serverModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenMappingNullWorld() throws Exception {
    Server server = null;
    serverModelDataMapper.transform(server);
  }

  @Test public void shouldReturnWorldModelInstanceWhenMappingWorld() throws Exception {
    Server server = getFakeWorld();

    ServerModel serverModel = serverModelDataMapper.transform(server);

    assertThat(serverModel, is(instanceOf(ServerModel.class)));
  }

  @Test public void shouldTransformProperlyAllFieldsWhenMappingWorld() throws Exception {
    Server server = getFakeWorld();

    ServerModel serverModel = serverModelDataMapper.transform(server);

    assertThat(FAKE_SSID, is(serverModel.getSsid()));
    assertThat(FAKE_IP, is(serverModel.getIp()));
    assertThat(FAKE_VERSION, is(serverModel.getVersion()));
    assertThat(FAKE_SEED, is(serverModel.getSeed()));
    assertThat(FAKE_WORLD_NAME, is(serverModel.getWorldName()));
    assertThat(FAKE_PLAYER_NAME, is(serverModel.getPlayerName()));
  }

  @Test public void shouldReturnEmptyWorldModelCollectionWhenMappingEmptyWorldCollection()
      throws Exception {
    Collection<ServerModel> serverModels = serverModelDataMapper.transform(Collections.emptyList());

    assertThat(serverModels, is(Collections.emptyList()));
    assertTrue(serverModels.isEmpty());
  }

  @Test public void shouldReturnTheSameAmountOfElementsWhenMappingWorldCollection()
      throws Exception {
    ArrayList<Server> servers = new ArrayList<>();
    servers.add(mock(Server.class));
    servers.add(mock(Server.class));
    servers.add(mock(Server.class));

    Collection<ServerModel> serverModels = serverModelDataMapper.transform(servers);

    assertNotNull(serverModels);
    assertThat(serverModels.size(), is(3));
  }

  @Test public void shouldReturnWorldModelInstanceCollectionWhenMappingWorldCollection()
      throws Exception {
    ArrayList<Server> servers = new ArrayList<>();
    servers.add(mock(Server.class));
    servers.add(mock(Server.class));
    servers.add(mock(Server.class));

    ArrayList<ServerModel> serverModels =
        (ArrayList<ServerModel>) serverModelDataMapper.transform(servers);

    assertThat(serverModels.get(0), is(instanceOf(ServerModel.class)));
    assertThat(serverModels.get(1), is(instanceOf(ServerModel.class)));
    assertThat(serverModels.get(2), is(instanceOf(ServerModel.class)));
  }

  private Server getFakeWorld() {
    return new Server.Builder()
        .ssid(FAKE_SSID)
        .ip(FAKE_IP)
        .version(FAKE_VERSION)
        .seed(FAKE_SEED)
        .worldName(FAKE_WORLD_NAME)
        .playerName(FAKE_PLAYER_NAME)
        .build();
  }
}