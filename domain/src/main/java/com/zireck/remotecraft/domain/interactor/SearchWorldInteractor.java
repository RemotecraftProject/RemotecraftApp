package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import rx.Observable;

public class SearchWorldInteractor extends Interactor {

  private final NetworkProvider networkProvider;

  public SearchWorldInteractor(NetworkProvider networkProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.networkProvider = networkProvider;
  }

  @Override protected Observable buildInteractorObservable() {
    return networkProvider.searchWorld();
  }
}
