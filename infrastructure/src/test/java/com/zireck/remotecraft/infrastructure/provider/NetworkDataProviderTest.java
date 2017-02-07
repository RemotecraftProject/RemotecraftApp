package com.zireck.remotecraft.infrastructure.provider;

import com.zireck.remotecraft.domain.World;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.WorldEntityDataMapper;
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
  @Mock private WorldEntityDataMapper mockWorldEntityDataMapper;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    networkProvider = new NetworkDataProvider(mockServerSearchManager, mockWorldEntityDataMapper);
  }

  @Test public void shouldReturnValidWorld() throws Exception {
    WorldEntity worldEntity = getWorldEntity();
    World world = getWorld();
    Maybe<WorldEntity> maybe = Maybe.create(subscriber -> subscriber.onSuccess(worldEntity));
    when(mockServerSearchManager.searchWorld()).thenReturn(maybe);
    when(mockWorldEntityDataMapper.transform(worldEntity)).thenReturn(world);

    Maybe<World> worldMaybe = networkProvider.searchWorld();

    TestObserver<World> testObserver = worldMaybe.test();
    testObserver.assertNoErrors();
    testObserver.assertComplete();
    testObserver.assertResult(world);
    verify(mockServerSearchManager, times(1)).searchWorld();
    verify(mockWorldEntityDataMapper, times(1)).transform(worldEntity);
    verifyNoMoreInteractions(mockServerSearchManager, mockWorldEntityDataMapper);
  }

  @Test public void shouldNotReturnAnyWorld() throws Exception {
    when(mockServerSearchManager.searchWorld()).thenReturn(Maybe.never());

    Maybe<World> worldMaybe = networkProvider.searchWorld();

    TestObserver<World> testObserver = worldMaybe.test();
    testObserver.assertEmpty();
    testObserver.assertNotComplete();
    verify(mockServerSearchManager, times(1)).searchWorld();
    verifyZeroInteractions(mockWorldEntityDataMapper);
    verifyNoMoreInteractions(mockServerSearchManager);
  }

  @Test public void shouldReturnWorldForAGivenIpAddress() throws Exception {
    String ipAddress = "192.168.1.1";
    WorldEntity worldEntity = getWorldEntity();
    World world = getWorld();
    Maybe<WorldEntity> maybe = Maybe.create(subscriber -> subscriber.onSuccess(worldEntity));
    when(mockServerSearchManager.searchWorld(ipAddress)).thenReturn(maybe);
    when(mockWorldEntityDataMapper.transform(worldEntity)).thenReturn(world);

    Maybe<World> worldMaybe = networkProvider.searchWorld(ipAddress);

    TestObserver<World> testObserver = worldMaybe.test();
    testObserver.assertNoErrors();
    testObserver.assertComplete();
    testObserver.assertResult(world);
    verify(mockServerSearchManager, times(1)).searchWorld(ipAddress);
    verify(mockWorldEntityDataMapper, times(1)).transform(worldEntity);
    verifyNoMoreInteractions(mockServerSearchManager, mockWorldEntityDataMapper);
  }

  @Test public void shouldNotReturnAnyWorldForACertainIpAddress() throws Exception {
    String ipAddress = "192.168.1.435";
    when(mockServerSearchManager.searchWorld(ipAddress)).thenReturn(Maybe.never());

    Maybe<World> worldMaybe = networkProvider.searchWorld(ipAddress);

    TestObserver<World> testObserver = worldMaybe.test();
    testObserver.assertEmpty();
    testObserver.assertNotComplete();
    verify(mockServerSearchManager, times(1)).searchWorld(ipAddress);
    verifyZeroInteractions(mockWorldEntityDataMapper);
    verifyNoMoreInteractions(mockServerSearchManager);
  }

  private WorldEntity getWorldEntity() {
    return new WorldEntity.Builder()
        .ip("192.168.1.1")
        .name("The name")
        .player("The player")
        .seed("354523456")
        .ssid("WLAN_346Q")
        .version("1.8")
        .build();
  }

  private World getWorld() {
    return new World.Builder()
        .ip("192.168.1.1")
        .name("The name")
        .player("The player")
        .ssid("WLAN_346Q")
        .version("1.8")
        .build();
  }
}