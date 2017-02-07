package com.zireck.remotecraft.infrastructure.provider;

import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.zireck.remotecraft.infrastructure.manager.ServerSearchManager;
import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class NetworkDataProviderTest {

  private NetworkProvider networkProvider;

  @Mock private ServerSearchManager mockServerSearchManager;
  @Mock private ServerEntityDataMapper mockServerEntityDataMapper;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    networkProvider = new NetworkDataProvider(mockServerSearchManager, mockServerEntityDataMapper);
  }

  @Test public void shouldReturnValidWorld() throws Exception {
    ServerEntity serverEntity = getWorldEntity();
    Server server = getWorld();
    Maybe<ServerEntity> maybe = Maybe.create(subscriber -> subscriber.onSuccess(serverEntity));
    when(mockServerSearchManager.searchServer()).thenReturn(maybe);
    when(mockServerEntityDataMapper.transform(serverEntity)).thenReturn(server);

    Maybe<Server> worldMaybe = networkProvider.searchServer();

    TestObserver<Server> testObserver = worldMaybe.test();
    testObserver.assertNoErrors();
    testObserver.assertComplete();
    testObserver.assertResult(server);
    verify(mockServerSearchManager, times(1)).searchServer();
    verify(mockServerEntityDataMapper, times(1)).transform(serverEntity);
    verifyNoMoreInteractions(mockServerSearchManager, mockServerEntityDataMapper);
  }

  @Test public void shouldNotReturnAnyWorld() throws Exception {
    when(mockServerSearchManager.searchServer()).thenReturn(Maybe.never());

    Maybe<Server> worldMaybe = networkProvider.searchServer();

    TestObserver<Server> testObserver = worldMaybe.test();
    testObserver.assertEmpty();
    testObserver.assertNotComplete();
    verify(mockServerSearchManager, times(1)).searchServer();
    verifyZeroInteractions(mockServerEntityDataMapper);
    verifyNoMoreInteractions(mockServerSearchManager);
  }

  @Test public void shouldReturnWorldForAGivenIpAddress() throws Exception {
    String ipAddress = "192.168.1.1";
    ServerEntity serverEntity = getWorldEntity();
    Server server = getWorld();
    Maybe<ServerEntity> maybe = Maybe.create(subscriber -> subscriber.onSuccess(serverEntity));
    when(mockServerSearchManager.searchServer(ipAddress)).thenReturn(maybe);
    when(mockServerEntityDataMapper.transform(serverEntity)).thenReturn(server);

    Maybe<Server> worldMaybe = networkProvider.searchServer(ipAddress);

    TestObserver<Server> testObserver = worldMaybe.test();
    testObserver.assertNoErrors();
    testObserver.assertComplete();
    testObserver.assertResult(server);
    verify(mockServerSearchManager, times(1)).searchServer(ipAddress);
    verify(mockServerEntityDataMapper, times(1)).transform(serverEntity);
    verifyNoMoreInteractions(mockServerSearchManager, mockServerEntityDataMapper);
  }

  @Test public void shouldNotReturnAnyWorldForACertainIpAddress() throws Exception {
    String ipAddress = "192.168.1.435";
    when(mockServerSearchManager.searchServer(ipAddress)).thenReturn(Maybe.never());

    Maybe<Server> worldMaybe = networkProvider.searchServer(ipAddress);

    TestObserver<Server> testObserver = worldMaybe.test();
    testObserver.assertEmpty();
    testObserver.assertNotComplete();
    verify(mockServerSearchManager, times(1)).searchServer(ipAddress);
    verifyZeroInteractions(mockServerEntityDataMapper);
    verifyNoMoreInteractions(mockServerSearchManager);
  }

  private ServerEntity getWorldEntity() {
    return new ServerEntity.Builder()
        .ssid("WLAN_346Q")
        .ip("192.168.1.1")
        .version("1.8")
        .seed("354523456")
        .worldName("The name")
        .playerName("The player")
        .build();
  }

  private Server getWorld() {
    return new Server.Builder()
        .ssid("WLAN_346Q")
        .ip("192.168.1.1")
        .version("1.8")
        .seed("354523456")
        .worldName("The name")
        .playerName("The player")
        .build();
  }
}