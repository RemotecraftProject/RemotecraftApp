package com.remotecraft.app.infrastructure.provider;

import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.provider.NetworkActionProvider;
import com.remotecraft.app.infrastructure.entity.NetworkAddressEntity;
import com.remotecraft.app.infrastructure.entity.ServerEntity;
import com.remotecraft.app.infrastructure.entity.mapper.NetworkAddressEntityDataMapper;
import com.remotecraft.app.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.remotecraft.app.infrastructure.manager.ServerSearchManager;
import io.reactivex.Observable;
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

  private NetworkActionProvider networkActionProvider;

  @Mock private ServerSearchManager mockServerSearchManager;
  @Mock private ServerEntityDataMapper mockServerEntityDataMapper;
  @Mock private NetworkAddressEntityDataMapper mockNetworkAddressEntityDataMapper;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    networkActionProvider = new NetworkDataProvider(mockServerSearchManager, mockServerEntityDataMapper,
        mockNetworkAddressEntityDataMapper);
  }

  @Test public void shouldReturnValidServer() throws Exception {
    ServerEntity serverEntity = getServerEntity();
    Server server = getServer();
    Observable<ServerEntity> observable = Observable.create(subscriber -> {
      subscriber.onNext(serverEntity);
      subscriber.onComplete();
    });
    when(mockServerSearchManager.searchServer()).thenReturn(observable);
    when(mockServerEntityDataMapper.transform(serverEntity)).thenReturn(server);

    Observable<Server> serverObservable = networkActionProvider.searchServer();

    TestObserver<Server> testObserver = serverObservable.test();
    testObserver.assertNoErrors();
    testObserver.assertComplete();
    testObserver.assertResult(server);
    verify(mockServerSearchManager, times(1)).searchServer();
    verify(mockServerEntityDataMapper, times(1)).transform(serverEntity);
    verifyNoMoreInteractions(mockServerSearchManager, mockServerEntityDataMapper);
  }

  @Test public void shouldNotReturnAnyServer() throws Exception {
    when(mockServerSearchManager.searchServer()).thenReturn(Observable.never());

    Observable<Server> serverObservable = networkActionProvider.searchServer();

    TestObserver<Server> testObserver = serverObservable.test();
    testObserver.assertEmpty();
    testObserver.assertNotComplete();
    verify(mockServerSearchManager, times(1)).searchServer();
    verifyZeroInteractions(mockServerEntityDataMapper);
    verifyNoMoreInteractions(mockServerSearchManager);
  }

  @Test public void shouldReturnServerForAGivenNetworkAddress() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("192.168.1.1")
        .port(9998)
        .build();
    NetworkAddressEntity networkAddressEntity = NetworkAddressEntity.builder()
        .ip("192.168.1.1")
        .port(9998)
        .build();
    ServerEntity serverEntity = getServerEntity();
    Server server = getServer();
    Observable<ServerEntity> observable = Observable.create(subscriber -> {
      subscriber.onNext(serverEntity);
      subscriber.onComplete();
    });
    when(mockNetworkAddressEntityDataMapper.transformInverse(networkAddress)).thenReturn(
        networkAddressEntity);
    when(mockServerSearchManager.searchServer(networkAddressEntity)).thenReturn(observable);
    when(mockServerEntityDataMapper.transform(serverEntity)).thenReturn(server);

    Observable<Server> serverObservable = networkActionProvider.searchServer(networkAddress);

    TestObserver<Server> testObserver = serverObservable.test();
    testObserver.assertNoErrors();
    testObserver.assertComplete();
    testObserver.assertResult(server);
    verify(mockServerSearchManager, times(1)).searchServer(networkAddressEntity);
    verify(mockServerEntityDataMapper, times(1)).transform(serverEntity);
    verifyNoMoreInteractions(mockServerSearchManager, mockServerEntityDataMapper);
  }

  @Test public void shouldNotReturnAnyServerForACertainNetworkAddress() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("192.168.1.435")
        .port(9998)
        .build();
    NetworkAddressEntity networkAddressEntity = NetworkAddressEntity.builder()
        .ip("192.168.1.435")
        .port(9998)
        .build();
    when(mockNetworkAddressEntityDataMapper.transformInverse(networkAddress)).thenReturn(
        networkAddressEntity);
    when(mockServerSearchManager.searchServer(networkAddressEntity)).thenReturn(Observable.never());

    Observable<Server> serverObservable = networkActionProvider.searchServer(networkAddress);

    TestObserver<Server> testObserver = serverObservable.test();
    testObserver.assertEmpty();
    testObserver.assertNotComplete();
    verify(mockServerSearchManager, times(1)).searchServer(networkAddressEntity);
    verifyZeroInteractions(mockServerEntityDataMapper);
    verifyNoMoreInteractions(mockServerSearchManager);
  }

  private ServerEntity getServerEntity() {
    return ServerEntity.builder()
        .ssid("WLAN_346Q")
        .ip("192.168.1.1")
        .hostname("iMac")
        .os("Mac OS X")
        .version("1.8")
        .seed("354523456")
        .worldName("The name")
        .playerName("The player")
        .encodedWorldImage("base64image")
        .build();
  }

  private Server getServer() {
    return Server.builder()
        .ssid("WLAN_346Q")
        .ip("192.168.1.1")
        .hostname("iMac")
        .os("Mac OS X")
        .version("1.8")
        .seed("354523456")
        .worldName("The name")
        .playerName("The player")
        .encodedWorldImage("base64image")
        .build();
  }
}