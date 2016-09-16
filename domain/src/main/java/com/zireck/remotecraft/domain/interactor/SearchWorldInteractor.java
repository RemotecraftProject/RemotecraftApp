package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.manager.NetworkManager;
import rx.Observable;

public class SearchWorldInteractor extends Interactor {

  private final NetworkManager networkManager;

  public SearchWorldInteractor(NetworkManager networkManager, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.networkManager = networkManager;
  }

  @Override protected Observable buildInteractorObservable() {
    return networkManager.searchWorld();
  }
}
