package com.remotecraft.app.domain.interactor.base;

import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.interactor.params.EmptyParams;
import io.reactivex.Maybe;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

public class MaybeInteractorTest {

  private MaybeInteractorTestClass maybeInteractor;
  private TestDisposableMaybeObserver testDisposableMaybeObserver;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    maybeInteractor = new MaybeInteractorTestClass(mockThreadExecutor, mockPostExecutionThread);
    testDisposableMaybeObserver = new TestDisposableMaybeObserver();

    given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
  }

  // TODO test non-empty stream

  @Test
  public void shouldReturnEmptyStream() throws Exception {
    maybeInteractor.execute(testDisposableMaybeObserver, null);

    assertEquals(0, testDisposableMaybeObserver.valuesCount);
  }

  @Test
  public void shouldProperlyDisposeObserver() throws Exception {
    maybeInteractor.execute(testDisposableMaybeObserver, null);
    maybeInteractor.dispose();

    assertTrue(testDisposableMaybeObserver.isDisposed());
  }

  @Test(expected = NullPointerException.class)
  public void shouldFailWhenNullObserver()
      throws Exception {
    maybeInteractor.execute(null, null);
  }

  private static final class MaybeInteractorTestClass
      extends MaybeInteractor<Integer, EmptyParams> {

    public MaybeInteractorTestClass(ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {
      super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Maybe<Integer> buildReactiveStream(EmptyParams emptyParams) {
      return Maybe.empty();
    }

    @Override
    public void execute(DisposableMaybeObserver observer, EmptyParams params) {
      super.execute(observer, params);
    }
  }

  private static final class TestDisposableMaybeObserver<T> extends DisposableMaybeObserver<T> {

    private int valuesCount = 0;

    @Override
    public void onSuccess(T value) {
      valuesCount++;
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
  }
}