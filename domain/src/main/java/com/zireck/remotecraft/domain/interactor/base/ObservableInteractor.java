package com.zireck.remotecraft.domain.interactor.base;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.params.BaseParams;
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
  @Override public void execute(DisposableObserver observer, P params) {
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
