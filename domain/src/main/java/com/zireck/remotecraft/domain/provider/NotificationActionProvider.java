package com.zireck.remotecraft.domain.provider;

import com.zireck.remotecraft.domain.Server;

public interface NotificationActionProvider {
  void notifyServerFound(Server server);
}
