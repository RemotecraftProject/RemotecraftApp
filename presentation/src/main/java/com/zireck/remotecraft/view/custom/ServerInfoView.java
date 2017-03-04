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
import com.zireck.remotecraft.imageloader.ImageLoader;
import com.zireck.remotecraft.model.ServerModel;

public class ServerInfoView extends RelativeLayout {

  private static final int LAYOUT_RESOURCE = R.layout.server_info_view;
  private static final String AVATAR_URL = "https://minotar.net/helm/%s/%s.png";

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

  public void renderServer(Context context, ServerModel serverModel, ImageLoader imageLoader) {
    inflateView(context);

    loadAvatarFor(serverModel.getPlayerName(), imageLoader);
    playerNameView.setText(serverModel.getPlayerName());
    worldNameView.setText(serverModel.getWorldName());
    ssidAndIpView.setText(String.format("%s (%s)", serverModel.getSsid(), serverModel.getIp()));
    hostnameAndOsView.setText(
        String.format("%s (%s)", serverModel.getHostname(), serverModel.getOs()));
  }

  private View inflateView(Context context) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View view = layoutInflater.inflate(LAYOUT_RESOURCE, this, true);
    ButterKnife.bind(this, view);

    return view;
  }

  private void loadAvatarFor(String playerName, ImageLoader imageLoader) {
    final String avatarSize = "100";
    final String avatarUrl = String.format(AVATAR_URL, playerName, avatarSize);
    imageLoader.load(avatarUrl, playerAvatarView);
  }
}
