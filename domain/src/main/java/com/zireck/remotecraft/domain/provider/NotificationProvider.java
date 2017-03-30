package com.zireck.remotecraft.domain.provider;

import com.zireck.remotecraft.domain.Server;

public interface NotificationProvider {
  void notifyServerFound(Server server);
}
