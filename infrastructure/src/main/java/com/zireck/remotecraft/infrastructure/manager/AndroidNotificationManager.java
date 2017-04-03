package com.zireck.remotecraft.infrastructure.manager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.zireck.remotecraft.domain.Server;
import com.zireck.remotecraft.infrastructure.R;
import com.zireck.remotecraft.infrastructure.entity.ServerEntity;
import com.zireck.remotecraft.infrastructure.entity.mapper.ServerEntityDataMapper;
import com.zireck.remotecraft.infrastructure.tool.JsonSerializer;
import javax.inject.Inject;
import javax.inject.Named;

public class AndroidNotificationManager {

  private static final int NOTIFICATION_SERVER_FOUND = 1;
  private static final String KEY_DOMAIN_SERVER_FOUND_SERIALIZED =
      "key_domain_server_found_serialized";

  @Inject Context context;
  private Class serverSearchActivityClass;
  private ServerEntityDataMapper serverEntityDataMapper;
  private JsonSerializer jsonSerializer;

  @Inject public AndroidNotificationManager(
      @Named("ServerSearchActivityClass") Class serverSearchActivityClass,
      ServerEntityDataMapper serverEntityDataMapper, JsonSerializer jsonSerializer) {
    this.serverSearchActivityClass = serverSearchActivityClass;
    this.serverEntityDataMapper = serverEntityDataMapper;
    this.jsonSerializer = jsonSerializer;
  }

  public void notifyServerFound(ServerEntity serverEntity) {
    Intent notificationIntent = new Intent(context, serverSearchActivityClass);

    Server server = serverEntityDataMapper.transform(serverEntity);
    String serializedServer = jsonSerializer.toJson(server);
    notificationIntent.putExtra(KEY_DOMAIN_SERVER_FOUND_SERIALIZED, serializedServer);

    PendingIntent notificationPendingIntent =
        PendingIntent.getActivity(context, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);

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
