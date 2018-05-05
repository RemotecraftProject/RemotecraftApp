package com.remotecraft.app.domain.service;

import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.provider.NetworkActionProvider;
import io.reactivex.Observable;
import javax.inject.Inject;

public class SearchServerService implements DomainService {

  private final NetworkActionProvider networkActionProvider;

  @Inject public SearchServerService(NetworkActionProvider networkActionProvider) {
    this.networkActionProvider = networkActionProvider;
  }

  public Observable<Server> searchServer() {
    return networkActionProvider.searchServer();
  }

  public Observable<Server> searchServer(NetworkAddress networkAddress) {
    return networkActionProvider.searchServer(networkAddress);
  }
}
