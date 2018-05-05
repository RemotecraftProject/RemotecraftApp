package com.remotecraft.app.domain.service;

import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.provider.NotificationActionProvider;
import javax.inject.Inject;

public class NotifyServerFoundService implements DomainService {

  private final NotificationActionProvider notificationActionProvider;

  @Inject public NotifyServerFoundService(NotificationActionProvider notificationActionProvider) {
    this.notificationActionProvider = notificationActionProvider;
  }

  public void notifyServerFound(Server server) {
    notificationActionProvider.notifyServerFound(server);
  }
}
