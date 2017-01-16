package com.zireck.remotecraft.domain.interactor.base;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import io.reactivex.Completable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

public class CompletableInteractorTest {

  private CompletableInteractorTestClass completableInteractor;
  private NotCompletableInteractorTestClass notCompletableInteractor;
  private TestDisposableCompletableObserver testDisposableCompletableObserver;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    completableInteractor =
        new CompletableInteractorTestClass(mockThreadExecutor, mockPostExecutionThread);
    notCompletableInteractor =
        new NotCompletableInteractorTestClass(mockThreadExecutor, mockPostExecutionThread);
    testDisposableCompletableObserver = new TestDisposableCompletableObserver();

    given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
  }

  // TODO test should complete stream

  @Test public void shouldNeverComplete() throws Exception {
    notCompletableInteractor.execute(testDisposableCompletableObserver);

    assertFalse(testDisposableCompletableObserver.isCompleted);
  }

  @Test public void shouldProperlyDisposeObserver() throws Exception {
    completableInteractor.execute(testDisposableCompletableObserver);
    completableInteractor.dispose();

    assertTrue(testDisposableCompletableObserver.isDisposed());
  }

  @Test(expected = NullPointerException.class) public void shouldFailWhenNullObserver()
      throws Exception {
    completableInteractor.execute(null);
  }

  private static final class CompletableInteractorTestClass extends CompletableInteractor {

    public CompletableInteractorTestClass(ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {
      super(threadExecutor, postExecutionThread);
    }

    @Override protected Completable buildReactiveStream() {
      return Completable.complete();
    }
  }

  private static final class NotCompletableInteractorTestClass extends CompletableInteractor {

    public NotCompletableInteractorTestClass(ThreadExecutor threadExecutor,
        PostExecutionThread postExecutionThread) {
      super(threadExecutor, postExecutionThread);
    }

    @Override protected Completable buildReactiveStream() {
      return Completable.never();
    }
  }

  private static final class TestDisposableCompletableObserver
      extends DisposableCompletableObserver {

    private boolean isCompleted = false;

    @Override public void onComplete() {
      isCompleted = true;
    }

    @Override public void onError(Throwable e) {

    }
  }
}