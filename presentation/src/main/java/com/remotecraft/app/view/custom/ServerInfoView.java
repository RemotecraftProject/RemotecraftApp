package com.remotecraft.app.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.remotecraft.app.R;
import com.remotecraft.app.infrastructure.tool.ImageLoader;
import com.remotecraft.app.model.ServerModel;

public class ServerInfoView extends RelativeLayout {

  private static final int LAYOUT_RESOURCE = R.layout.view_server_info;

  @BindView(R.id.image_player_avatar) ImageView playerAvatarView;
  @BindView(R.id.text_player_name) TextView playerNameView;
  @BindView(R.id.image_world) ImageView worldImageView;
  @BindView(R.id.text_world_name) TextView worldNameView;
  @BindView(R.id.text_network_info) TextView networkInfoView;
  @BindView(R.id.image_os_icon) ImageView iconOsView;
  @BindView(R.id.text_os_info) TextView osInfoView;

  public ServerInfoView(Context context) {
    super(context);
  }

  public ServerInfoView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ServerInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  // TODO remove context and use getContext() instead
  public void renderServer(Context context, ServerModel serverModel, ImageLoader imageLoader,
      Bitmap worldImage, String playerAvatarUrl, int playerAvatarSize, int osIconResource) {
    inflateView(context);

    worldImageView.setImageBitmap(worldImage);
    loadPlayerAvatar(serverModel.playerName(), imageLoader, playerAvatarUrl, playerAvatarSize);

    playerNameView.setText(serverModel.playerName());
    worldNameView.setText(serverModel.worldName());
    String networkInfoText = context.getString(R.string.server_found_network_info,
        serverModel.ssid(), serverModel.ip());
    networkInfoView.setText(networkInfoText);
    String osInfoText = context.getString(R.string.server_found_os_info, serverModel.os(),
        serverModel.hostname());
    osInfoView.setText(osInfoText);
    iconOsView.setImageResource(osIconResource);
  }

  private View inflateView(Context context) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View view = layoutInflater.inflate(LAYOUT_RESOURCE, this, true);
    ButterKnife.bind(this, view);
    return view;
  }

  private void loadPlayerAvatar(String playerName, ImageLoader imageLoader, String playerAvatarUrl,
      int playerAvatarSize) {
    final String avatarSize = String.valueOf(playerAvatarSize);
    final String avatarUrl = String.format(playerAvatarUrl, playerName, avatarSize);
    imageLoader.load(avatarUrl, playerAvatarView);
  }
}
