package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.params.BaseParams;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import io.reactivex.Maybe;

public class SearchServerForIpInteractor
    extends MaybeInteractor<Server, SearchServerForIpInteractor.Params> {

  private final NetworkProvider networkProvider;

  public SearchServerForIpInteractor(NetworkProvider networkProvider, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.networkProvider = networkProvider;
  }

  @Override protected Maybe<Server> buildReactiveStream(Params params) {
    // TODO check if it's a valid ip address
    if (params.ipAddress == null || params.ipAddress.isEmpty()) {
      throw new RuntimeException("Invalid IP Address");
    }

    return networkProvider.searchServer(params.ipAddress);
  }

  public static final class Params implements BaseParams {
    private final String ipAddress;

    private Params(String ipAddress) {
      this.ipAddress = ipAddress;
    }

    public static Params forIpAddress(String ipAddress) {
      return new Params(ipAddress);
    }
  }
}
