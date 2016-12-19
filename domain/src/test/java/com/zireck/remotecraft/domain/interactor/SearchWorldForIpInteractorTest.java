package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.World;
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

public class SearchWorldForIpInteractorTest {

  private SearchWorldForIpInteractor searchWorldForIpInteractor;

  @Mock private NetworkProvider mockNetworkProvider;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    searchWorldForIpInteractor =
        new SearchWorldForIpInteractor(mockNetworkProvider, mockThreadExecutor,
            mockPostExecutionThread);
  }

  @Test public void shouldBuildReactiveStreamProperly() throws Exception {
    searchWorldForIpInteractor.setIpAddress("127.0.0.1");
    searchWorldForIpInteractor.buildReactiveStream();

    verify(mockNetworkProvider, only()).searchWorld("127.0.0.1");
    verifyNoMoreInteractions(mockNetworkProvider);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test public void shoulNotReturnInvalidReactiveStreamWhenValidWorldFound() throws Exception {
    when(mockNetworkProvider.searchWorld("192.168.1.40")).thenReturn(getValidWorldReactiveStream());

    searchWorldForIpInteractor.setIpAddress("192.168.1.40");
    Maybe reactiveStream = searchWorldForIpInteractor.buildReactiveStream();

    assertNotNull(reactiveStream);
  }

  @Test public void shouldNotReturnInvalidReactiveStreamWhenNoWorldFound() throws Exception {
    when(mockNetworkProvider.searchWorld("192.168.1.40")).thenReturn(Maybe.empty());

    searchWorldForIpInteractor.setIpAddress("192.168.1.40");
    Maybe reactiveStream = searchWorldForIpInteractor.buildReactiveStream();

    assertNotNull(reactiveStream);
  }

  @Test public void shouldReturnEmptyReactiveStreamWhenNoWorldFound() throws Exception {
    when(mockNetworkProvider.searchWorld("192.168.1.40")).thenReturn(Maybe.empty());

    searchWorldForIpInteractor.setIpAddress("192.168.1.40");
    Maybe reactiveStream = searchWorldForIpInteractor.buildReactiveStream();

    assertEquals(getEmptyReactiveStream(), reactiveStream);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenNullIpAddressIsSet() throws Exception {
    searchWorldForIpInteractor.setIpAddress(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionWhenEmptyIpAddressIsSet() throws Exception {
    searchWorldForIpInteractor.setIpAddress("");
  }

  @Test(expected = RuntimeException.class)
  public void shouldThrowExceptionWhenBuildingReactiveStreamWithoutPreviouslySettingAnIpAddress()
      throws Exception {
    searchWorldForIpInteractor.buildReactiveStream();
  }

  private Maybe<World> getValidWorldReactiveStream() {
    return Maybe.just(getFakeWorld());
  }

  private World getFakeWorld() {
    return new World.Builder().build();
  }

  private Maybe<World> getEmptyReactiveStream() {
    return Maybe.empty();
  }
}