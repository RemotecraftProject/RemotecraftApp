package com.zireck.remotecraft.infrastructure.manager;

import com.google.gson.Gson;
import com.zireck.remotecraft.infrastructure.protocol.CommandType;
import com.zireck.remotecraft.infrastructure.tool.GsonSerializer;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class NetworkProtocolManagerTest {

  private NetworkProtocolManager networkProtocolManager;

  private Gson gson;
  private JsonSerializer jsonSerializer;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    gson = new Gson();
    jsonSerializer = new GsonSerializer(gson);
    networkProtocolManager = new NetworkProtocolManager(jsonSerializer);
  }

  @Test public void shouldReturnValidSerializedRequest() throws Exception {
    String serverSearchRequest = networkProtocolManager.composeServerSearchRequest();

    assertThat(serverSearchRequest, notNullValue());
    assertThat(serverSearchRequest, is(instanceOf(String.class)));
    assertThat(serverSearchRequest, containsString(CommandType.GET_WORLD_INFO.toString()));
  }
}