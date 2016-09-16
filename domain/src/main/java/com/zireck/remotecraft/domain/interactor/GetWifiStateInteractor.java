package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.provider.ReceiversProvider;
import rx.Observable;

public class GetWifiStateInteractor extends Interactor {

  private final ReceiversProvider receiversProvider;

  public GetWifiStateInteractor(ReceiversProvider receiversProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.receiversProvider = receiversProvider;
  }

  @Override protected Observable buildInteractorObservable() {
    return receiversProvider.getWifiState();
  }
}
