package com.remotecraft.app.domain.interactor;

import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.interactor.params.EmptyParams;
import com.remotecraft.app.domain.interactor.base.MaybeInteractor;
import com.remotecraft.app.domain.provider.ReceiverActionProvider;
import io.reactivex.Maybe;

public class GetWifiStateInteractor extends MaybeInteractor<Integer, EmptyParams> {

  private final ReceiverActionProvider receiverActionProvider;

  public GetWifiStateInteractor(ReceiverActionProvider receiverActionProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.receiverActionProvider = receiverActionProvider;
  }

  @Override
  protected Maybe<Integer> buildReactiveStream(EmptyParams params) {
    return receiverActionProvider.getWifiState();
  }
}
