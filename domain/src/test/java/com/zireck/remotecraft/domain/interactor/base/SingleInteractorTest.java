package com.zireck.remotecraft.domain.interactor.base;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.params.EmptyParams;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

public class SingleInteractorTest {

  private SingleInteractorTestClass singleInteractor;
  private TestDisposableSingleObserver<Integer> testDisposableSingleObserver;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    singleInteractor = new SingleInteractorTestClass(mockThreadExecutor, mockPostExecutionThread);
    testDisposableSingleObserver = new TestDisposableSingleObserver<Integer>();

    given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
  }

  // TODO test stream containing values

  @Test public void shouldProperlyDisposeObserver() throws Exception {
    singleInteractor.execute(testDisposableSingleObserver, null);
    singleInteractor.dispose();

    assertTrue(testDisposableSingleObserver.isDisposed());
  }

  @Test(expected = NullPointerException.class) public void shouldFailWhenNullObserver()
      throws Exception {
    singleInteractor.execute(null, null);
  }

  private static final class SingleInteractorTestClass
      extends SingleInteractor<Integer, EmptyParams> {
    public SingleInteractorTestClass(ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {
      super(threadExecutor, postExecutionThread);
    }

    @Override protected Single<Integer> buildReactiveStream(EmptyParams params) {
      return Single.just(1);
    }
  }

  private static final class TestDisposableSingleObserver<T> extends DisposableSingleObserver<T> {

    private int valuesCount = 0;

    @Override public void onSuccess(T value) {
      valuesCount++;
    }

    @Override public void onError(Throwable e) {

    }
  }
}