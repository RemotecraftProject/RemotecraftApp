package com.remotecraft.app.domain.interactor.base;

import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.interactor.params.EmptyParams;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

public class ObservableInteractorTest {

  private ObservableInteractorTestClass observableInteractor;
  private EmptyObservableInteractorTestClass emptyObservableInteractor;
  private TestDisposableObserver<Object> testDisposableObserver;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    observableInteractor =
        new ObservableInteractorTestClass(mockThreadExecutor, mockPostExecutionThread);
    emptyObservableInteractor =
        new EmptyObservableInteractorTestClass(mockThreadExecutor, mockPostExecutionThread);
    testDisposableObserver = new TestDisposableObserver<>();

    given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
  }

  // TODO test non-empty stream

  @Test public void shouldReturnEmptyStream() throws Exception {
    emptyObservableInteractor.execute(testDisposableObserver, null);

    assertEquals(0, testDisposableObserver.valuesCount);
  }

  @Test public void shouldProperlyDisposeObserver() throws Exception {
    observableInteractor.execute(testDisposableObserver, null);
    observableInteractor.dispose();

    assertTrue(testDisposableObserver.isDisposed());
  }

  @Test(expected = NullPointerException.class) public void shouldFailWhenNullObserver()
      throws Exception {
    observableInteractor.execute(null, null);
  }

  private static final class ObservableInteractorTestClass
      extends ObservableInteractor<Integer, EmptyParams> {

    public ObservableInteractorTestClass(ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {
      super(threadExecutor, postExecutionThread);
    }

    @Override protected Observable<Integer> buildReactiveStream(EmptyParams params) {
      return Observable.just(1, 1, 1, 1);
    }
  }

  private static final class EmptyObservableInteractorTestClass
      extends ObservableInteractor<Integer, EmptyParams> {

    public EmptyObservableInteractorTestClass(ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {
      super(threadExecutor, postExecutionThread);
    }

    @Override protected Observable<Integer> buildReactiveStream(EmptyParams params) {
      return Observable.empty();
    }
  }

  private static final class TestDisposableObserver<T> extends DisposableObserver<T> {

    private int valuesCount = 0;

    @Override public void onNext(T value) {
      valuesCount++;
    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onComplete() {

    }
  }
}