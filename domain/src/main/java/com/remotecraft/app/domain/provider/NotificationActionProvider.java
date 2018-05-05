package com.remotecraft.app.domain.provider;

import com.remotecraft.app.domain.Server;

public interface NotificationActionProvider {
  void notifyServerFound(Server server);
}
