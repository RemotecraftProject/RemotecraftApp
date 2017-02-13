package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.base.MaybeInteractor;
import com.zireck.remotecraft.domain.interactor.params.BaseParams;
import com.zireck.remotecraft.domain.provider.NetworkProvider;
import com.zireck.remotecraft.domain.validation.Validator;
import io.reactivex.Maybe;

public class SearchServerForIpInteractor
    extends MaybeInteractor<Server, SearchServerForIpInteractor.Params> {

  private final NetworkProvider networkProvider;
  private final Validator<NetworkAddress> networkAddressValidator;

  public SearchServerForIpInteractor(NetworkProvider networkProvider,
      Validator<NetworkAddress> networkAddressValidator, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.networkProvider = networkProvider;
    this.networkAddressValidator = networkAddressValidator;
  }

  @Override protected Maybe<Server> buildReactiveStream(Params params) {
    if (params == null || !networkAddressValidator.isValid(params.networkAddress)) {
      return Maybe.error(new IllegalArgumentException("Invalid IP Address"));
    }

    return networkProvider.searchServer(params.networkAddress);
  }

  public static final class Params implements BaseParams {
    private final NetworkAddress networkAddress;

    private Params(NetworkAddress networkAddress) {
      this.networkAddress = networkAddress;
    }

    public static Params forNetworkAddress(NetworkAddress networkAddress) {
      return new Params(networkAddress);
    }
  }
}
