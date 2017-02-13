package com.zireck.remotecraft.infrastructure.provider;

import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.infrastructure.entity.NetworkAddressEntity;
import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.NetworkAddressEntityDataMapper;
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
  @Mock private NetworkAddressEntityDataMapper mockNetworkAddressEntityDataMapper;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    networkProvider = new NetworkDataProvider(mockServerSearchManager, mockServerEntityDataMapper,
        mockNetworkAddressEntityDataMapper);
  }

  @Test public void shouldReturnValidServer() throws Exception {
    ServerEntity serverEntity = getServerEntity();
    Server server = getServer();
    Maybe<ServerEntity> maybe = Maybe.create(subscriber -> subscriber.onSuccess(serverEntity));
    when(mockServerSearchManager.searchServer()).thenReturn(maybe);
    when(mockServerEntityDataMapper.transform(serverEntity)).thenReturn(server);

    Maybe<Server> serverMaybe = networkProvider.searchServer();

    TestObserver<Server> testObserver = serverMaybe.test();
    testObserver.assertNoErrors();
    testObserver.assertComplete();
    testObserver.assertResult(server);
    verify(mockServerSearchManager, times(1)).searchServer();
    verify(mockServerEntityDataMapper, times(1)).transform(serverEntity);
    verifyNoMoreInteractions(mockServerSearchManager, mockServerEntityDataMapper);
  }

  @Test public void shouldNotReturnAnyServer() throws Exception {
    when(mockServerSearchManager.searchServer()).thenReturn(Maybe.never());

    Maybe<Server> serverMaybe = networkProvider.searchServer();

    TestObserver<Server> testObserver = serverMaybe.test();
    testObserver.assertEmpty();
    testObserver.assertNotComplete();
    verify(mockServerSearchManager, times(1)).searchServer();
    verifyZeroInteractions(mockServerEntityDataMapper);
    verifyNoMoreInteractions(mockServerSearchManager);
  }

  @Test public void shouldReturnServerForAGivenNetworkAddress() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("192.168.1.1")
        .build();
    NetworkAddressEntity networkAddressEntity = new NetworkAddressEntity.Builder()
        .with("192.168.1.1")
        .build();
    ServerEntity serverEntity = getServerEntity();
    Server server = getServer();
    Maybe<ServerEntity> maybe = Maybe.create(subscriber -> subscriber.onSuccess(serverEntity));
    when(mockNetworkAddressEntityDataMapper.transformInverse(networkAddress)).thenReturn(
        networkAddressEntity);
    when(mockServerSearchManager.searchServer(networkAddressEntity)).thenReturn(maybe);
    when(mockServerEntityDataMapper.transform(serverEntity)).thenReturn(server);

    Maybe<Server> serverMaybe = networkProvider.searchServer(networkAddress);

    TestObserver<Server> testObserver = serverMaybe.test();
    testObserver.assertNoErrors();
    testObserver.assertComplete();
    testObserver.assertResult(server);
    verify(mockServerSearchManager, times(1)).searchServer(networkAddressEntity);
    verify(mockServerEntityDataMapper, times(1)).transform(serverEntity);
    verifyNoMoreInteractions(mockServerSearchManager, mockServerEntityDataMapper);
  }

  @Test public void shouldNotReturnAnyServerForACertainNetworkAddress() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("192.168.1.435")
        .build();
    NetworkAddressEntity networkAddressEntity = new NetworkAddressEntity.Builder()
        .with("192.168.1.435")
        .build();
    when(mockNetworkAddressEntityDataMapper.transformInverse(networkAddress)).thenReturn(
        networkAddressEntity);
    when(mockServerSearchManager.searchServer(networkAddressEntity)).thenReturn(Maybe.never());

    Maybe<Server> serverMaybe = networkProvider.searchServer(networkAddress);

    TestObserver<Server> testObserver = serverMaybe.test();
    testObserver.assertEmpty();
    testObserver.assertNotComplete();
    verify(mockServerSearchManager, times(1)).searchServer(networkAddressEntity);
    verifyZeroInteractions(mockServerEntityDataMapper);
    verifyNoMoreInteractions(mockServerSearchManager);
  }

  private ServerEntity getServerEntity() {
    return new ServerEntity.Builder()
        .ssid("WLAN_346Q")
        .ip("192.168.1.1")
        .hostname("iMac")
        .os("Mac OS X")
        .version("1.8")
        .seed("354523456")
        .worldName("The name")
        .playerName("The player")
        .build();
  }

  private Server getServer() {
    return new Server.Builder()
        .ssid("WLAN_346Q")
        .ip("192.168.1.1")
        .hostname("iMac")
        .os("Mac OS X")
        .version("1.8")
        .seed("354523456")
        .worldName("The name")
        .playerName("The player")
        .build();
  }
}