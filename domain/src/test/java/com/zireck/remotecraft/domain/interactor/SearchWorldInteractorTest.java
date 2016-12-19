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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class SearchWorldInteractorTest {

  private SearchWorldInteractor searchWorldInteractor;

  @Mock private NetworkProvider mockNetworkProvider;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    searchWorldInteractor =
        new SearchWorldInteractor(mockNetworkProvider, mockThreadExecutor, mockPostExecutionThread);
  }

  @Test public void shouldBuildReactiveStreamProperly() throws Exception {
    searchWorldInteractor.buildReactiveStream();

    verify(mockNetworkProvider).searchWorld();
    verifyNoMoreInteractions(mockNetworkProvider);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test public void shoulNotReturnInvalidReactiveStreamWhenValidWorldFound() throws Exception {
    when(mockNetworkProvider.searchWorld()).thenReturn(getValidWorldReactiveStream());

    Maybe reactiveStream = searchWorldInteractor.buildReactiveStream();

    assertNotNull(reactiveStream);
  }

  @Test public void shouldNotReturnInvalidReactiveStreamWhenNoWorldFound() throws Exception {
    when(mockNetworkProvider.searchWorld()).thenReturn(Maybe.empty());

    Maybe reactiveStream = searchWorldInteractor.buildReactiveStream();

    assertNotNull(reactiveStream);
  }

  @Test public void shouldReturnEmptyReactiveStreamWhenNoWorldFound() throws Exception {
    when(mockNetworkProvider.searchWorld()).thenReturn(Maybe.empty());

    Maybe reactiveStream = searchWorldInteractor.buildReactiveStream();

    assertEquals(getEmptyReactiveStream(), reactiveStream);
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