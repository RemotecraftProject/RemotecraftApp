package com.zireck.remotecraft.infrastructure.provider;

import com.zireck.remotecraft.domain.Notification;
import com.zireck.remotecraft.domain.provider.NotificationProvider;
import com.zireck.remotecraft.infrastructure.manager.AndroidNotificationManager;
import javax.inject.Inject;

public class NotificationDataProvider implements NotificationProvider {

  private final AndroidNotificationManager androidNotificationManager;

  @Inject public NotificationDataProvider(AndroidNotificationManager androidNotificationManager) {
    this.androidNotificationManager = androidNotificationManager;
  }

  @Override public void displayNotification(Notification notification) {
    // TODO add Notification mapper
    androidNotificationManager.displayNotification(notification);
  }
}
