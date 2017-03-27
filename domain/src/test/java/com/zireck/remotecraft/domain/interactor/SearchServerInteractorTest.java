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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class SearchServerInteractorTest {

  private SearchServerInteractor searchServerInteractor;

  @Mock private NetworkProvider mockNetworkProvider;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    searchServerInteractor =
        new SearchServerInteractor(mockNetworkProvider, mockThreadExecutor, mockPostExecutionThread);
  }

  @Test public void shouldBuildReactiveStreamProperly() throws Exception {
    searchServerInteractor.buildReactiveStream(null);

    verify(mockNetworkProvider).searchServer();
    verifyNoMoreInteractions(mockNetworkProvider);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }

  @Test public void shoulNotReturnInvalidReactiveStreamWhenValidWorldFound() throws Exception {
    when(mockNetworkProvider.searchServer()).thenReturn(getValidWorldReactiveStream());

    Maybe reactiveStream = searchServerInteractor.buildReactiveStream(null);

    assertThat(reactiveStream).isNotNull();
  }

  @Test public void shouldNotReturnInvalidReactiveStreamWhenNoWorldFound() throws Exception {
    when(mockNetworkProvider.searchServer()).thenReturn(Maybe.empty());

    Maybe reactiveStream = searchServerInteractor.buildReactiveStream(null);

    assertThat(reactiveStream).isNotNull();
  }

  @Test public void shouldReturnEmptyReactiveStreamWhenNoWorldFound() throws Exception {
    when(mockNetworkProvider.searchServer()).thenReturn(Maybe.empty());

    Maybe reactiveStream = searchServerInteractor.buildReactiveStream(null);

    assertThat(getEmptyReactiveStream()).isEqualTo(reactiveStream);
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