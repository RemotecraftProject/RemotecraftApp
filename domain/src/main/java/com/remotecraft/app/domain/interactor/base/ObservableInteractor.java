package com.remotecraft.app.domain.interactor.base;

import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.interactor.params.BaseParams;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class ObservableInteractor<T, P extends BaseParams>
    implements Interactor<DisposableObserver, P> {

  protected final ThreadExecutor threadExecutor;
  protected final PostExecutionThread postExecutionThread;
  protected final CompositeDisposable disposables;

  public ObservableInteractor(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
    this.disposables = new CompositeDisposable();
  }

  protected abstract Observable<T> buildReactiveStream(P params);

  @SuppressWarnings("unchecked")
  @Override
  public void execute(DisposableObserver observer, P params) {
    DisposableObserver disposableObserver = buildReactiveStream(params)
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.getScheduler())
        .subscribeWith(observer);
    disposables.add(disposableObserver);
  }

  @Override
  public void dispose() {
    disposables.clear();
  }
}
