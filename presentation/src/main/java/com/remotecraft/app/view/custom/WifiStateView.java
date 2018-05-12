package com.remotecraft.app.view.custom;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.remotecraft.app.R;
import com.remotecraft.app.model.WifiStateModel;

public class WifiStateView extends RelativeLayout {

  private static final int LAYOUT_RESOURCE = R.layout.view_wifi_state;

  @BindView(R.id.layout_header) ViewGroup headerLayout;
  @BindView(R.id.image_wifi_state) ImageView wifiStateIconView;
  @BindView(R.id.text_wifi_state) TextView wifiStateView;
  @BindView(R.id.text_wifi_state_description) TextView wifiStateDescriptionView;

  public WifiStateView(Context context) {
    super(context);
  }

  public WifiStateView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public WifiStateView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void renderWifiState(final WifiStateModel wifiStateModel) {
    inflateView(getContext());

    if (wifiStateModel.strenghtLevel() == 0) {
      headerLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.md_red_500));
    } else if (wifiStateModel.strenghtLevel() >= 1 && wifiStateModel.strenghtLevel() <= 3) {
      headerLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.md_yellow_500));
    } else if (wifiStateModel.strenghtLevel() > 3) {
      headerLayout.setBackgroundColor(ContextCompat.getColor(getContext(),
          R.color.md_light_green_500));
    }

    wifiStateIconView.setImageResource(wifiStateModel.iconResource());
    wifiStateView.setText(wifiStateModel.strenght());
    wifiStateDescriptionView.setText(wifiStateModel.strenghtDescription());
  }

  private View inflateView(Context context) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View view = layoutInflater.inflate(LAYOUT_RESOURCE, this, true);
    ButterKnife.bind(this, view);
    return view;
  }
}
