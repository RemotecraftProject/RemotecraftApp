package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.params.EmptyParams;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import io.reactivex.Maybe;

public class SearchServerInteractor extends MaybeInteractor<Server, EmptyParams> {

  private final NetworkProvider networkProvider;

  public SearchServerInteractor(NetworkProvider networkProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.networkProvider = networkProvider;
  }

  @Override protected Maybe<Server> buildReactiveStream(EmptyParams params) {
    return networkProvider.searchServer();
  }
}
