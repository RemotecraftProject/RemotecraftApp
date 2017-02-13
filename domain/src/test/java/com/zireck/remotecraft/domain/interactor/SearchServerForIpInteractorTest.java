package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.domain.validation.NetworkAddressValidator;
import io.reactivex.Maybe;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    when(mockNetworkProvider.searchServer(networkAddress)).thenReturn(Maybe.empty());

    SearchServerForIpInteractor.Params params =
        SearchServerForIpInteractor.Params.forNetworkAddress(networkAddress);
    Maybe reactiveStream = searchServerForIpInteractor.buildReactiveStream(params);

    assertEquals(getEmptyReactiveStream(), reactiveStream);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenNullIpAddressIsSet() throws Exception {
    searchServerForIpInteractor.buildReactiveStream(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenEmptyIpAddressIsSet() throws Exception {
    searchServerForIpInteractor.buildReactiveStream(null);
  }

  @Test(expected = RuntimeException.class)
  public void shouldThrowExceptionWhenBuildingReactiveStreamWithoutPreviouslySettingAnIpAddress()
      throws Exception {
    searchServerForIpInteractor.buildReactiveStream(null);
  }

  private Maybe<Server> getValidWorldReactiveStream() {
    return Maybe.just(getFakeWorld());
  }

  private Server getFakeWorld() {
    return new Server.Builder().build();
  }

  private Maybe<Server> getEmptyReactiveStream() {
    return Maybe.empty();
  }
}