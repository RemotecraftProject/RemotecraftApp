package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.params.EmptyParams;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.provider.ReceiverActionProvider;
import io.reactivex.Maybe;

public class GetWifiStateInteractor extends MaybeInteractor<Integer, EmptyParams> {

  private final ReceiverActionProvider receiverActionProvider;

  public GetWifiStateInteractor(ReceiverActionProvider receiverActionProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.receiverActionProvider = receiverActionProvider;
  }

  @Override protected Maybe<Integer> buildReactiveStream(EmptyParams params) {
    return receiverActionProvider.getWifiState();
  }
}
