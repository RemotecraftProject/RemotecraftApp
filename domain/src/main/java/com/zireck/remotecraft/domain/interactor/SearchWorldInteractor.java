package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.repository.NetworkRepository;
import rx.Observable;

public class SearchWorldInteractor extends Interactor {

  private final NetworkRepository networkRepository;

  public SearchWorldInteractor(NetworkRepository networkRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.networkRepository = networkRepository;
  }

  @Override protected Observable buildInteractorObservable() {
    return networkRepository.searchWorld();
  }
}
