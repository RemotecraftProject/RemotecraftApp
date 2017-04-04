package com.zireck.remotecraft.domain.service;

import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.provider.NetworkActionProvider;
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
