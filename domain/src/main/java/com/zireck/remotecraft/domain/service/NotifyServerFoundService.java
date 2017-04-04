package com.zireck.remotecraft.domain.service;

import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.domain.provider.NotificationActionProvider;
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
