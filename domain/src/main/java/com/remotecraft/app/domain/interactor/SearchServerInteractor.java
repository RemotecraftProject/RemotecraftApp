package com.remotecraft.app.domain.interactor;

import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.interactor.base.ObservableInteractor;
import com.remotecraft.app.domain.interactor.params.BaseParams;
import com.remotecraft.app.domain.observer.DefaultObservableObserver;
import com.remotecraft.app.domain.service.NotifyServerFoundService;
import com.remotecraft.app.domain.service.SearchServerService;
import com.remotecraft.app.domain.validation.Validator;
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

  @Override
  protected Observable<Server> buildReactiveStream(Params params) {
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
  @Override
  public void execute(DisposableObserver observer, Params params) {
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

  @Override
  public void dispose() {
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
    @Override
    public void onNext(Server server) {
      processFoundServer(server);
    }

    @Override
    public void onComplete() {
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
