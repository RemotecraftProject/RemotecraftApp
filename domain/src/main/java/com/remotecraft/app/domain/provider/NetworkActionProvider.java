package com.remotecraft.app.domain.provider;

import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.domain.Server;
import io.reactivex.Observable;

public interface NetworkActionProvider {
  Observable<Server> searchServer();
  Observable<Server> searchServer(NetworkAddress networkAddress);
}
