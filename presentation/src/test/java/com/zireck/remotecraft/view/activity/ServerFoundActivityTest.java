package com.zireck.remotecraft.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.zireck.remotecraft.BuildConfig;
import com.zireck.remotecraft.RemotecraftMockApp;
import com.zireck.remotecraft.dagger.components.ServerFoundComponent;
import com.zireck.remotecraft.dagger.modules.activitymodules.ServerFoundModule;
import com.zireck.remotecraft.infrastructure.tool.ImageLoader;
import com.zireck.remotecraft.model.ServerModel;
import com.zireck.remotecraft.navigation.Navigator;
import com.zireck.remotecraft.presenter.ServerFoundPresenter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(application = RemotecraftMockApp.class, constants = BuildConfig.class)
public class ServerFoundActivityTest {

  private ServerFoundActivity serverFoundActivity;

  @Mock private ServerFoundComponent.Builder mockBuilder;
  @Mock private Navigator mockNavigator;
  @Mock private ServerFoundPresenter mockServerFoundPresenter;
  @Mock private ImageLoader mockImageLoader;

  private ServerFoundComponent serverFoundComponent = new ServerFoundComponent() {
    @Override public void injectMembers(ServerFoundActivity instance) {
      instance.navigator = mockNavigator;
      instance.presenter = mockServerFoundPresenter;
      instance.imageLoader = mockImageLoader;
    }
  };

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(mockBuilder.build()).thenReturn(serverFoundComponent);
    when(mockBuilder.activityModule(any(ServerFoundModule.class))).thenReturn(mockBuilder);
    getApp().putActivityComponentBuilder(mockBuilder, ServerFoundActivity.class);

    serverFoundActivity = Robolectric.setupActivity(ServerFoundActivity.class);
    ButterKnife.bind(serverFoundActivity);
  }

  @Test public void shouldCreateValidCallingIntent() throws Exception {
    Context mockContext = mock(Context.class);
    ServerModel serverModel = getServerModel();

    Intent callingIntent = ServerFoundActivity.getCallingIntent(mockContext, serverModel);

    assertThat(callingIntent, notNullValue());
    Bundle extras = callingIntent.getExtras();
    assertThat(extras, notNullValue());
    assertThat(extras.containsKey(ServerFoundActivity.KEY_SERVER), is(true));
    assertThat(extras.getParcelable(ServerFoundActivity.KEY_SERVER), notNullValue());
    assertThat(extras.getParcelable(ServerFoundActivity.KEY_SERVER), instanceOf(ServerModel.class));
    ServerModel actualServerModel = extras.getParcelable(ServerFoundActivity.KEY_SERVER);
    assertThat(actualServerModel, notNullValue());
    assertThat(actualServerModel.ip(), notNullValue());
    assertThat(actualServerModel.ip(), is("192.168.15.47"));
  }

  @Test public void shouldProperlyAttachViewToPresenterWhenCreatingActivity() throws Exception {
    ServerFoundActivity serverFoundActivityNotCreated =
        Robolectric.buildActivity(ServerFoundActivity.class).get();

    serverFoundActivityNotCreated.onCreate(null);

    verify(mockServerFoundPresenter, times(1)).attachView(serverFoundActivityNotCreated);
  }

  @Test public void shouldPassServerModelToPresenterWhenGiven() throws Exception {
    Context mockContext = mock(Context.class);
    ServerModel serverModel = getServerModel();

    Intent callingIntent = ServerFoundActivity.getCallingIntent(mockContext, serverModel);
    ServerFoundActivity serverFoundActivityWithIntent =
        Robolectric.buildActivity(ServerFoundActivity.class)
        .withIntent(callingIntent)
        .create()
        .get();

    verify(mockServerFoundPresenter, times(1)).setServer(any(ServerModel.class));
  }

  @Test public void shouldNotPassServerModelToPresenterWhenNotGiven() throws Exception {
    Context mockContext = mock(Context.class);

    ServerFoundActivity serverFoundActivityWithoutServerModel =
        Robolectric.buildActivity(ServerFoundActivity.class).create().get();

    verify(mockServerFoundPresenter, never()).setServer(any(ServerModel.class));
  }

  @Test public void shouldCancelActivityWhenHomeButtonPressed() throws Exception {
    MenuItem mockMenuItem = mock(MenuItem.class);
    when(mockMenuItem.getItemId()).thenReturn(android.R.id.home);

    serverFoundActivity.onOptionsItemSelected(mockMenuItem);

    verify(mockServerFoundPresenter, times(1)).onClickCancel();
  }

  @Test public void shouldNotCancelActivityWhenHomeButtonNotPressed() throws Exception {
    MenuItem mockMenuItem = mock(MenuItem.class);
    when(mockMenuItem.getItemId()).thenReturn(-1);

    serverFoundActivity.onOptionsItemSelected(mockMenuItem);

    verify(mockServerFoundPresenter, never()).onClickCancel();
  }

  @Test public void shouldRenderServer() throws Exception {
    ServerModel serverModel = getServerModel();

    serverFoundActivity.renderServer(serverModel);

    assertThat(serverFoundActivity.getSupportActionBar().getTitle(), notNullValue());
    assertThat(serverFoundActivity.getSupportActionBar().getTitle(), is("Reign of Giants"));
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

  @Test public void shouldNavigateBack() throws Exception {
    ServerModel serverModel = getServerModel();
    serverFoundActivity.navigateBack(true, serverModel);

    verify(mockNavigator).finishActivity(serverFoundActivity, true, "server", serverModel);
  }

  @Test public void shouldNotifyPresenterWhenClickAccept() throws Exception {
    serverFoundActivity.onClickAccept(null);

    verify(mockServerFoundPresenter).onClickAccept();
  }

  private RemotecraftMockApp getApp() {
    return (RemotecraftMockApp) RuntimeEnvironment.application;
  }

  private ServerModel getServerModel() {
    return ServerModel.builder()
        .ssid("WLAN_C33")
        .ip("192.168.15.47")
        .hostname("iMac")
        .os("macOS Sierra")
        .version("2.4")
        .seed("4955335923")
        .worldName("Reign of Giants")
        .playerName("GenerikB")
        .build();
  }
}