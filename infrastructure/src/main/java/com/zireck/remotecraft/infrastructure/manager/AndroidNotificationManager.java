package com.zireck.remotecraft.infrastructure.manager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.zireck.remotecraft.infrastructure.R;
import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import javax.inject.Inject;
import javax.inject.Named;

public class AndroidNotificationManager {

  private static final int NOTIFICATION_SERVER_FOUND = 1;

  @Inject Context context;
  @Inject @Named("ServerSearchActivityClass") Class serverSearchActivityClass;

  @Inject public AndroidNotificationManager() {

  }

  public void notifyServerFound(ServerEntity serverEntity) {
    Intent notificationIntent = new Intent(context, serverSearchActivityClass);
    // TODO: remove this and send the server instead
    notificationIntent.putExtra("key_hello", "Hello, " + serverEntity.playerName() + "!");
    PendingIntent notificationPendingIntent =
        PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_wifi_white) // TODO: somehow set the proper icon...
        .setContentTitle(serverEntity.worldName())
        .setContentText(String.format("Hey, %s. We found your world!", serverEntity.playerName()))
        .setAutoCancel(true)
        .setContentIntent(notificationPendingIntent);

    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(NOTIFICATION_SERVER_FOUND, notificationCompatBuilder.build());
  }
}
