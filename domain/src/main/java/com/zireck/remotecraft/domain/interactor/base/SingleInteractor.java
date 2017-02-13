package com.zireck.remotecraft.domain.interactor.base;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.params.BaseParams;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class SingleInteractor<T, P extends BaseParams>
    implements Interactor<DisposableSingleObserver, P> {

  private final ThreadExecutor threadExecutor;
  private final PostExecutionThread postExecutionThread;
  private final CompositeDisposable disposables;

  public SingleInteractor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
    this.disposables = new CompositeDisposable();
  }

  protected abstract Single<T> buildReactiveStream(P params);

  @SuppressWarnings("unchecked")
  @Override public void execute(DisposableSingleObserver observer, P params) {
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
