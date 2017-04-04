package com.zireck.remotecraft.domain.provider;

import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.Server;
import io.reactivex.Observable;

public interface NetworkActionProvider {
  Observable<Server> searchServer();
  Observable<Server> searchServer(NetworkAddress networkAddress);
}
