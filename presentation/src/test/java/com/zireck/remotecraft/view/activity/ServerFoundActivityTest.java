package com.zireck.remotecraft.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.zireck.remotecraft.BuildConfig;
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
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 24, application = RemotecraftApp.class)
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
    ServerModel serverModel = ServerModel.builder()
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
    assertThat(actualServerModel.ssid(), is("WLAN_C33"));
    assertThat(actualServerModel.ip(), is("192.168.15.47"));
    assertThat(actualServerModel.hostname(), is("iMac"));
    assertThat(actualServerModel.os(), is("macOS Sierra"));
    assertThat(actualServerModel.version(), is("2.4"));
    assertThat(actualServerModel.seed(), is("4955335923"));
    assertThat(actualServerModel.worldName(), is("Reign of Giants"));
    assertThat(actualServerModel.playerName(), is("GenerikB"));
  }

  @Test public void shouldDisplayError() throws Exception {
    serverFoundActivity.showError("Something came up");

    Toast latestToast = ShadowToast.getLatestToast();
    String textOfLatestToast = ShadowToast.getTextOfLatestToast();
    assertThat(latestToast, notNullValue());
    assertThat(latestToast.getDuration(), is(Toast.LENGTH_LONG));
    assertThat(textOfLatestToast, notNullValue());
    assertThat(textOfLatestToast, is("Something came up"));
  }

  private RemotecraftApp getApp() {
    return (RemotecraftApp) RuntimeEnvironment.application;
  }
}