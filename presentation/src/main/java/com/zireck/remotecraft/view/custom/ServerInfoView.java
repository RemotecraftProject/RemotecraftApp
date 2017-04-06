package com.zireck.remotecraft.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.infrastructure.tool.ImageLoader;
import com.zireck.remotecraft.model.ServerModel;

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
      String playerAvatarUrl) {
    inflateView(context);

    loadAvatarFor(serverModel.playerName(), imageLoader, playerAvatarUrl);
    playerNameView.setText(serverModel.playerName());
    worldNameView.setText(serverModel.worldName());
    ssidAndIpView.setText(String.format("%s (%s)", serverModel.ssid(), serverModel.ip()));
    hostnameAndOsView.setText(
        String.format("%s (%s)", serverModel.hostname(), serverModel.os()));
  }

  private View inflateView(Context context) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View view = layoutInflater.inflate(LAYOUT_RESOURCE, this, true);
    ButterKnife.bind(this, view);

    return view;
  }

  private void loadAvatarFor(String playerName, ImageLoader imageLoader, String playerAvatarUrl) {
    final String avatarSize = "100";
    final String avatarUrl = String.format(playerAvatarUrl, playerName, avatarSize);
    imageLoader.load(avatarUrl, playerAvatarView);
  }
}
