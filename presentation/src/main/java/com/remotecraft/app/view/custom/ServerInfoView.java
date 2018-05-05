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
import com.remotecraft.app.tools.ImageDecoder;

public class ServerInfoView extends RelativeLayout {

  private static final int LAYOUT_RESOURCE = R.layout.server_info_view;

  @BindView(R.id.player_avatar) ImageView playerAvatarView;
  @BindView(R.id.player_name) TextView playerNameView;
  @BindView(R.id.world_name) TextView worldNameView;
  @BindView(R.id.ssid_and_ip) TextView ssidAndIpView;
  @BindView(R.id.hostname_and_os) TextView hostnameAndOsView;

  public ServerInfoView(Context context) {
    super(context);
  }

  public ServerInfoView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ServerInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void renderServer(Context context, ServerModel serverModel, ImageLoader imageLoader,
      ImageDecoder imageDecoder, String playerAvatarUrl, int playerAvatarSize) {
    inflateView(context);

    loadAvatarFor(serverModel.playerName(), imageLoader, playerAvatarUrl, playerAvatarSize);
    playerNameView.setText(serverModel.playerName());
    worldNameView.setText(serverModel.worldName());
    ssidAndIpView.setText(String.format("%s (%s)", serverModel.ssid(), serverModel.ip()));
    hostnameAndOsView.setText(
        String.format("%s (%s)", serverModel.hostname(), serverModel.os()));
    Bitmap worldImage = imageDecoder.decode(serverModel.encodedWorldImage());
    // TODO set world image
  }

  private View inflateView(Context context) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View view = layoutInflater.inflate(LAYOUT_RESOURCE, this, true);
    ButterKnife.bind(this, view);

    return view;
  }

  private void loadAvatarFor(String playerName, ImageLoader imageLoader, String playerAvatarUrl,
      int playerAvatarSize) {
    final String avatarSize = String.valueOf(playerAvatarSize);
    final String avatarUrl = String.format(playerAvatarUrl, playerName, avatarSize);
    imageLoader.load(avatarUrl, playerAvatarView);
  }
}
