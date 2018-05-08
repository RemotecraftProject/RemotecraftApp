package com.remotecraft.app.infrastructure.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.remotecraft.app.domain.Server;
import com.remotecraft.app.infrastructure.R;
import com.remotecraft.app.infrastructure.entity.ServerEntity;
import com.remotecraft.app.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.remotecraft.app.infrastructure.tool.ImageLoader;
import com.remotecraft.app.infrastructure.tool.JsonSerializer;

public class AndroidNotificationManager {

  private static final int NOTIFICATION_SERVER_FOUND = 1;
  private static final String KEY_DOMAIN_SERVER_FOUND_SERIALIZED =
      "key_domain_server_found_serialized";

  private final Context context;
  private final ImageLoader imageLoader;
  private final Class serverSearchActivityClass;
  private final ServerEntityDataMapper serverEntityDataMapper;
  private final JsonSerializer jsonSerializer;
  private final String playerAvatarUrl;
  private final int playerAvatarSize;
  private Target notificationLargeIconTarget;

  public AndroidNotificationManager(Context context, ImageLoader imageLoader,
      Class serverSearchActivityClass, ServerEntityDataMapper serverEntityDataMapper,
      JsonSerializer jsonSerializer, String playerAvatarUrl, int playerAvatarSize) {
    this.context = context;
    this.imageLoader = imageLoader;
    this.serverSearchActivityClass = serverSearchActivityClass;
    this.serverEntityDataMapper = serverEntityDataMapper;
    this.jsonSerializer = jsonSerializer;
    this.playerAvatarUrl = playerAvatarUrl;
    this.playerAvatarSize = playerAvatarSize;
  }

  public void notifyServerFound(ServerEntity serverEntity) {
    NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_wifi_white) // TODO: set the proper icon...
        .setContentTitle(serverEntity.worldName())
        .setContentText(String.format("Hey, %s. We found your world!", serverEntity.playerName()))
        .setAutoCancel(true)
        .setDefaults(0);

    Intent notificationIntent = new Intent(context, serverSearchActivityClass);
    Server server = serverEntityDataMapper.transform(serverEntity);
    String serializedServer = jsonSerializer.toJson(server);
    notificationIntent.putExtra(KEY_DOMAIN_SERVER_FOUND_SERIALIZED, serializedServer);
    PendingIntent notificationPendingIntent = makePendingIntent(context, 0, notificationIntent);
    notificationCompatBuilder.setContentIntent(notificationPendingIntent);

    String playerAvatarFullUrl =
        composePlayerAvatarFullUrl(serverEntity, playerAvatarUrl, playerAvatarSize);
    updateNotificationLargeIcon(NOTIFICATION_SERVER_FOUND, notificationCompatBuilder,
        playerAvatarFullUrl);

    displayNotification(NOTIFICATION_SERVER_FOUND, notificationCompatBuilder.build());
  }

  private String composePlayerAvatarFullUrl(ServerEntity serverEntity, String playerAvatarUrl,
      int playerAvatarSize) {
    String playerAvatarSizeText = String.valueOf(playerAvatarSize);
    return String.format(playerAvatarUrl, serverEntity.playerName(), playerAvatarSizeText);
  }

  private PendingIntent makePendingIntent(Context context, int requestCode,
      Intent notificationIntent) {
    return PendingIntent.getActivity(context, requestCode, notificationIntent,
        PendingIntent.FLAG_UPDATE_CURRENT);
  }

  private void displayNotification(int id, Notification notification) {
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(id, notification);
  }

  private void updateNotificationLargeIcon(int id,
      NotificationCompat.Builder notificationCompatBuilder, String largeIconUrl) {
    notificationLargeIconTarget = new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        notificationCompatBuilder
            .setLargeIcon(bitmap)
            .setDefaults(Notification.DEFAULT_ALL);

        displayNotification(id, notificationCompatBuilder.build());
      }

      @Override
      public void onBitmapFailed(Drawable errorDrawable) {

      }

      @Override
      public void onPrepareLoad(Drawable placeHolderDrawable) {

      }
    };

    imageLoader.load(largeIconUrl, notificationLargeIconTarget);
  }
}
