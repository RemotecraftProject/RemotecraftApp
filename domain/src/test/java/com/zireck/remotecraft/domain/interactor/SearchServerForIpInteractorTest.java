package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
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

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    searchServerForIpInteractor =
        new SearchServerForIpInteractor(mockNetworkProvider, mockThreadExecutor,
            mockPostExecutionThread);
  }

  @Test public void shouldBuildReactiveStreamProperly() throws Exception {
    searchServerForIpInteractor.setIpAddress("127.0.0.1");
    searchServerForIpInteractor.buildReactiveStream();

    verify(mockNetworkProvider, only()).searchServer("127.0.0.1");
    verifyNoMoreInteractions(mockNetworkProvider);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test public void shoulNotReturnInvalidReactiveStreamWhenValidWorldFound() throws Exception {
    when(mockNetworkProvider.searchServer("192.168.1.40")).thenReturn(getValidWorldReactiveStream());

    searchServerForIpInteractor.setIpAddress("192.168.1.40");
    Maybe reactiveStream = searchServerForIpInteractor.buildReactiveStream();

    assertNotNull(reactiveStream);
  }

  @Test public void shouldNotReturnInvalidReactiveStreamWhenNoWorldFound() throws Exception {
    when(mockNetworkProvider.searchServer("192.168.1.40")).thenReturn(Maybe.empty());

    searchServerForIpInteractor.setIpAddress("192.168.1.40");
    Maybe reactiveStream = searchServerForIpInteractor.buildReactiveStream();

    assertNotNull(reactiveStream);
  }

  @Test public void shouldReturnEmptyReactiveStreamWhenNoWorldFound() throws Exception {
    when(mockNetworkProvider.searchServer("192.168.1.40")).thenReturn(Maybe.empty());

    searchServerForIpInteractor.setIpAddress("192.168.1.40");
    Maybe reactiveStream = searchServerForIpInteractor.buildReactiveStream();

    assertEquals(getEmptyReactiveStream(), reactiveStream);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenNullIpAddressIsSet() throws Exception {
    searchServerForIpInteractor.setIpAddress(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenEmptyIpAddressIsSet() throws Exception {
    searchServerForIpInteractor.setIpAddress("");
  }

  @Test(expected = RuntimeException.class)
  public void shouldThrowExceptionWhenBuildingReactiveStreamWithoutPreviouslySettingAnIpAddress()
      throws Exception {
    searchServerForIpInteractor.buildReactiveStream();
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