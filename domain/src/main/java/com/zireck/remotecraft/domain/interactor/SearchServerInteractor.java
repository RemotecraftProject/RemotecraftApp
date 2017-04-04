package com.zireck.remotecraft.domain.interactor;

import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.executor.PostExecutionThread;
import com.zireck.remotecraft.domain.executor.ThreadExecutor;
import com.zireck.remotecraft.domain.interactor.base.ObservableInteractor;
import com.zireck.remotecraft.domain.interactor.params.BaseParams;
import com.zireck.remotecraft.domain.observer.DefaultObservableObserver;
import com.zireck.remotecraft.domain.service.NotifyServerFoundService;
import com.zireck.remotecraft.domain.service.SearchServerService;
import com.zireck.remotecraft.domain.validation.Validator;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchServerInteractor
    extends ObservableInteractor<Server, SearchServerInteractor.Params> {

  private final SearchServerService searchServerService;
  private final NotifyServerFoundService notifyServerFoundService;
  private final Validator<NetworkAddress> networkAddressValidator;
  private DisposableObserver domainObserver;
  private DisposableObserver presentationObserver;

  public SearchServerInteractor(SearchServerService searchServerService,
      NotifyServerFoundService notifyServerFoundService,
      Validator<NetworkAddress> networkAddressValidator, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.searchServerService = searchServerService;
    this.notifyServerFoundService = notifyServerFoundService;
    this.networkAddressValidator = networkAddressValidator;
  }

  @Override protected Observable<Server> buildReactiveStream(Params params) {
    if (params == null) {
      return Observable.error(new IllegalArgumentException("Params must be provided"));
    }

    NetworkAddress networkAddress = params.networkAddress;
    if (networkAddress != null && !networkAddressValidator.isValid(networkAddress)) {
      return Observable.error(new IllegalArgumentException("Invalid IP Address"));
    }

    return networkAddress != null ? searchServerService.searchServer(networkAddress)
        : searchServerService.searchServer();
  }

  @SuppressWarnings("unchecked")
  @Override public void execute(DisposableObserver observer, Params params) {
    Observable searchServerObservable = buildReactiveStream(params)
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.getScheduler());

    domainObserver = new SearchServerDomainObserver();
    presentationObserver = observer;

    searchServerObservable.subscribe(domainObserver);
    searchServerObservable.subscribe(presentationObserver);

    disposables.add(domainObserver);
    disposables.add(presentationObserver);
  }

  @Override public void dispose() {
    if (presentationObserver != null && !presentationObserver.isDisposed()) {
      disposables.remove(presentationObserver);
    }
  }

  private void processFoundServer(Server server) {
    if (presentationObserver != null && !presentationObserver.isDisposed()) {
      return;
    }

    displayNotificationForServer(server);
  }

  private void displayNotificationForServer(Server server) {
    notifyServerFoundService.notifyServerFound(server);
  }

  private final class SearchServerDomainObserver extends DefaultObservableObserver<Server> {
    @Override public void onNext(Server server) {
      // TODO: reenable this:
      //processFoundServer(server);

      displayNotificationForServer(server);
    }

    @Override public void onComplete() {
      disposables.remove(domainObserver);
    }
  }

  public static final class Params implements BaseParams {
    private final NetworkAddress networkAddress;

    private Params(NetworkAddress networkAddress) {
      this.networkAddress = networkAddress;
    }

    public static SearchServerInteractor.Params empty() {
      return new SearchServerInteractor.Params(null);
    }

    public static SearchServerInteractor.Params forNetworkAddress(NetworkAddress networkAddress) {
      return new SearchServerInteractor.Params(networkAddress);
    }
  }
}
