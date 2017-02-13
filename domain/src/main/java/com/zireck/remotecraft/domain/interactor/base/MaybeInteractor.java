package com.zireck.remotecraft.domain.interactor.base;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.params.BaseParams;
import io.reactivex.Maybe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class MaybeInteractor<T, P extends BaseParams>
    implements Interactor<DisposableMaybeObserver, P> {

  private final ThreadExecutor threadExecutor;
  private final PostExecutionThread postExecutionThread;
  private final CompositeDisposable disposables;

  public MaybeInteractor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
    this.disposables = new CompositeDisposable();
  }

  protected abstract Maybe<T> buildReactiveStream(P params);

  @SuppressWarnings("unchecked")
  @Override public void execute(DisposableMaybeObserver observer, P params) {
    buildReactiveStream(params)
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.getScheduler())
        .subscribeWith(observer);

    disposables.add(observer);
  }

  @Override public void dispose() {
    disposables.clear();
  }


}
