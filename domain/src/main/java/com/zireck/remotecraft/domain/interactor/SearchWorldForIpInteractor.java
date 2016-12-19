package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.provider.NetworkProvider;

import io.reactivex.Maybe;

public class SearchWorldForIpInteractor extends MaybeInteractor {

  private final NetworkProvider networkProvider;
  private String ipAddress;

  public SearchWorldForIpInteractor(NetworkProvider networkProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.networkProvider = networkProvider;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  @Override protected Maybe buildReactiveStream() {
    return networkProvider.searchWorld(ipAddress);
  }
}
