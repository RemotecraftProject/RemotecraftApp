package com.zireck.remotecraft.infrastructure.manager;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import com.zireck.remotecraft.domain.Notification;
import com.zireck.remotecraft.infrastructure.R;
import javax.inject.Inject;

public class AndroidNotificationManager {

  @Inject Context context;

  @Inject public AndroidNotificationManager() {

  }

  public void displayNotification(Notification notification) {
    NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_wifi_white) // TODO: somehow set the proper icon...
        .setContentTitle(notification.title())
        .setContentText(notification.content())
        .setContentIntent(null);

    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(1, notificationCompatBuilder.build());
  }
}
