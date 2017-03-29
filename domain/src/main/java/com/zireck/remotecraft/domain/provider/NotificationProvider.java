package com.zireck.remotecraft.domain.provider;

import com.zireck.remotecraft.domain.Notification;

public interface NotificationProvider {
  void displayNotification(Notification notification);
}
