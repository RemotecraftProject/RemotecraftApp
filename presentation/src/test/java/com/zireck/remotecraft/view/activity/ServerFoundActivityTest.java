package com.zireck.remotecraft.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.zireck.remotecraft.BuildConfig;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.RemotecraftApp;
import com.zireck.remotecraft.dagger.components.ApplicationComponent;
import com.zireck.remotecraft.dagger.modules.ApplicationModule;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.presenter.ServerFoundPresenter;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class) @Config(constants = BuildConfig.class, sdk = 24)
public class ServerFoundActivityTest {

  private ServerFoundActivity serverFoundActivity;

  @Rule public DaggerMockRule<ApplicationComponent> daggerMockRule =
      new DaggerMockRule<>(ApplicationComponent.class, new ApplicationModule(getApp())).set(
          component -> getApp().setApplicationComponent(component));

  @Mock private ServerFoundPresenter mockServerFoundPresenter;

  @Before public void setUp() throws Exception {
    serverFoundActivity = Robolectric.setupActivity(ServerFoundActivity.class);
  }

  @Test public void shouldGetValidCallingIntent() throws Exception {
    ServerModel serverModel = new ServerModel.Builder()
        .ssid("WLAN_C33")
        .ip("192.168.15.47")
        .hostname("iMac")
        .os("macOS Sierra")
        .version("2.4")
        .seed("4955335923")
        .worldName("Reign of Giants")
        .playerName("GenerikB")
        .build();

    Intent callingIntent = ServerFoundActivity.getCallingIntent(getApp(), serverModel);

    assertThat(callingIntent, notNullValue());
    Bundle extras = callingIntent.getExtras();
    assertThat(extras, notNullValue());
    assertThat(extras.containsKey(ServerFoundActivity.KEY_SERVER), is(true));
    ServerModel actualServerModel = extras.getParcelable(ServerFoundActivity.KEY_SERVER);
    assertThat(actualServerModel, notNullValue());
    assertThat(actualServerModel.getSsid(), is("WLAN_C33"));
    assertThat(actualServerModel.getIp(), is("192.168.15.47"));
    assertThat(actualServerModel.getHostname(), is("iMac"));
    assertThat(actualServerModel.getOs(), is("macOS Sierra"));
    assertThat(actualServerModel.getVersion(), is("2.4"));
    assertThat(actualServerModel.getSeed(), is("4955335923"));
    assertThat(actualServerModel.getWorldName(), is("Reign of Giants"));
    assertThat(actualServerModel.getPlayerName(), is("GenerikB"));
  }

  @Test public void shouldProperlyRenderWorldName() throws Exception {
    serverFoundActivity.renderWorldName("Reign of Giants");

    TextView worldNameView = (TextView) serverFoundActivity.findViewById(R.id.world_name);
    assertThat(worldNameView.getText().toString(), is("Reign of Giants"));
  }

  @Test public void shouldProperlyRenderPlayerName() throws Exception {
    serverFoundActivity.renderPlayerName("GenerikB");

    TextView playerNameView = (TextView) serverFoundActivity.findViewById(R.id.player_name);
    assertThat(playerNameView.getText().toString(), is("GenerikB"));
  }

  @Test public void shouldProperlyRenderNetworkInfo() throws Exception {
    serverFoundActivity.renderNetworkInfo("192.168.15.47 @ WLAN_C33");

    TextView networkInfoView = (TextView) serverFoundActivity.findViewById(R.id.network_info);
    assertThat(networkInfoView.getText().toString(), is("192.168.15.47 @ WLAN_C33"));
  }

  private RemotecraftApp getApp() {
    return (RemotecraftApp) RuntimeEnvironment.application;
  }
}