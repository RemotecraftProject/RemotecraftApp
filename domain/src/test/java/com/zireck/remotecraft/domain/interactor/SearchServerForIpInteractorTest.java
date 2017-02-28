package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.domain.validation.NetworkAddressValidator;
import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class SearchServerForIpInteractorTest {

  private SearchServerForIpInteractor searchServerForIpInteractor;

  @Mock private NetworkProvider mockNetworkProvider;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;
  @Mock private NetworkAddressValidator mockNetworkAddressValidator;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    searchServerForIpInteractor =
        new SearchServerForIpInteractor(mockNetworkProvider, mockNetworkAddressValidator,
            mockThreadExecutor, mockPostExecutionThread);
  }

  @Test public void shouldBuildReactiveStreamProperly() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("127.0.0.1")
        .build();
    when(mockNetworkAddressValidator.isValid(networkAddress)).thenReturn(true);

    SearchServerForIpInteractor.Params params =
        SearchServerForIpInteractor.Params.forNetworkAddress(networkAddress);
    searchServerForIpInteractor.buildReactiveStream(params);

    verify(mockNetworkProvider, only()).searchServer(networkAddress);
    verifyNoMoreInteractions(mockNetworkProvider);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test public void shoulNotReturnInvalidReactiveStreamWhenValidWorldFound() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("192.168.1.40")
        .build();
    when(mockNetworkProvider.searchServer(networkAddress)).thenReturn(
        getValidWorldReactiveStream());

    SearchServerForIpInteractor.Params params =
        SearchServerForIpInteractor.Params.forNetworkAddress(networkAddress);
    Maybe reactiveStream = searchServerForIpInteractor.buildReactiveStream(params);

    assertNotNull(reactiveStream);
  }

  @Test public void shouldNotReturnInvalidReactiveStreamWhenNoWorldFound() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("192.168.1.40")
        .build();
    when(mockNetworkProvider.searchServer(networkAddress)).thenReturn(Maybe.empty());

    SearchServerForIpInteractor.Params params =
        SearchServerForIpInteractor.Params.forNetworkAddress(networkAddress);
    Maybe reactiveStream = searchServerForIpInteractor.buildReactiveStream(params);

    assertNotNull(reactiveStream);
  }

  @Test public void shouldReturnEmptyReactiveStreamWhenNoWorldFound() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("192.168.1.40")
        .build();
    when(mockNetworkAddressValidator.isValid(networkAddress)).thenReturn(true);
    when(mockNetworkProvider.searchServer(networkAddress)).thenReturn(Maybe.empty());

    SearchServerForIpInteractor.Params params =
        SearchServerForIpInteractor.Params.forNetworkAddress(networkAddress);
    Maybe reactiveStream = searchServerForIpInteractor.buildReactiveStream(params);

    assertEquals(getEmptyReactiveStream(), reactiveStream);
  }

  @Test public void shouldThrowExceptionWhenNullNetworkAddressIsSet() throws Exception {
    Maybe<Server> serverMaybe = searchServerForIpInteractor.buildReactiveStream(null);

    TestObserver<Object> testObserver = new TestObserver<>();
    serverMaybe.subscribe(testObserver);
    assertThat(testObserver.errorCount(), is(1));
    testObserver.assertErrorMessage("Invalid IP Address");
  }

  @Test public void shouldThrowExceptionWhenEmptyNetworkAddressIsSet() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("")
        .build();
    SearchServerForIpInteractor.Params params =
        SearchServerForIpInteractor.Params.forNetworkAddress(networkAddress);

    Maybe<Server> serverMaybe = searchServerForIpInteractor.buildReactiveStream(params);

    TestObserver<Object> testObserver = new TestObserver<>();
    serverMaybe.subscribe(testObserver);
    assertThat(testObserver.errorCount(), is(1));
    testObserver.assertErrorMessage("Invalid IP Address");
  }

  private Maybe<Server> getValidWorldReactiveStream() {
    return Maybe.just(getFakeWorld());
  }

  private Server getFakeWorld() {
    return new Server.Builder()
        .ssid("WLAN_C33C")
        .ip("192.168.1.34")
        .hostname("iMac")
        .os("macOS")
        .version("1.47")
        .seed("9356454564578")
        .worldName("Brown Island")
        .playerName("Etho")
        .build();
  }

  private Maybe<Server> getEmptyReactiveStream() {
    return Maybe.empty();
  }
}