package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.manager.ReceiversManager;
import rx.Observable;

public class GetWifiStateInteractor extends Interactor {

  private final ReceiversManager receiversManager;

  public GetWifiStateInteractor(ReceiversManager receiversManager, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.receiversManager = receiversManager;
  }

  @Override protected Observable buildInteractorObservable() {
    return receiversManager.getWifiState();
  }
}
