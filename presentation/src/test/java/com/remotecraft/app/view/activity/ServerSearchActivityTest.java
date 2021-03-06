package com.remotecraft.app.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import com.remotecraft.app.BuildConfig;
import com.remotecraft.app.RemotecraftMockApp;
import com.remotecraft.app.dagger.components.ServerSearchComponent;
import com.remotecraft.app.dagger.modules.activitymodules.ServerSearchModule;
import com.remotecraft.app.infrastructure.tool.ImageLoader;
import com.remotecraft.app.model.ServerModel;
import com.remotecraft.app.navigation.Navigator;
import com.remotecraft.app.presenter.ServerSearchPresenter;
import com.remotecraft.app.robostuff.camera.CameraRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowPackageManager;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(application = RemotecraftMockApp.class, constants = BuildConfig.class)
public class ServerSearchActivityTest {

  private ServerSearchActivity serverSearchActivity;

  @Rule public CameraRule cameraRule = new CameraRule();

  @Mock private ServerSearchComponent.Builder mockBuilder;
  @Mock private Navigator mockNavigator;
  @Mock private ServerSearchPresenter mockServerSearchPresenter;
  @Mock private ImageLoader mockImageLoader;

  private ServerSearchComponent serverSearchComponent = new ServerSearchComponent() {
    @Override
    public void injectMembers(ServerSearchActivity instance) {
      instance.navigator = mockNavigator;
      instance.presenter = mockServerSearchPresenter;
      instance.imageLoader = mockImageLoader;
    }
  };

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(mockBuilder.build()).thenReturn(serverSearchComponent);
    when(mockBuilder.activityModule(any(ServerSearchModule.class))).thenReturn(mockBuilder);
    getApp().putActivityComponentBuilder(mockBuilder, ServerSearchActivity.class);

    setUpCameraFeature();

    serverSearchActivity = Robolectric.setupActivity(ServerSearchActivity.class);
    ButterKnife.bind(serverSearchActivity);
  }

  @Test
  public void shouldResumePresenter() throws Exception {
    serverSearchActivity.onResume();

    verify(mockServerSearchPresenter, atLeastOnce()).resume();
  }

  @Test
  public void shouldPausePresenter() throws Exception {
    serverSearchActivity.onPause();

    verify(mockServerSearchPresenter).pause();
  }

  @Test
  public void shouldDestroyPresenter() throws Exception {
    serverSearchActivity.onDestroy();

    verify(mockServerSearchPresenter).destroy();
  }

  @Test
  public void shouldProcessActivityResult() throws Exception {
    Intent mockIntent = mock(Intent.class);
    Bundle mockBundle = mock(Bundle.class);
    ServerModel mockServerModel = mock(ServerModel.class);
    when(mockBundle.getParcelable("server")).thenReturn(mockServerModel);
    when(mockIntent.getExtras()).thenReturn(mockBundle);

    serverSearchActivity.onActivityResult(12345, -1, mockIntent);

    verify(mockBundle, times(1)).getParcelable("server");
    verify(mockIntent, times(1)).getExtras();
    verify(mockServerSearchPresenter).onNavigationResult(12345, true, mockServerModel);
  }

  @Test
  public void shouldNavigateToServerDetail() throws Exception {
    ServerModel mockServerModel = mock(ServerModel.class);

    serverSearchActivity.navigateToServerDetail(mockServerModel);

    verify(mockNavigator).navigateToServerFoundActivity(serverSearchActivity, mockServerModel);
  }

  @Test
  public void shouldCloseFabMenu() throws Exception {
    serverSearchActivity.closeMenu();

    assertThat(serverSearchActivity.fabMenuView.isOpened(), is(false));
  }

  @Test
  public void shouldShowLoading() throws Exception {
    serverSearchActivity.showLoading();

    assertThat(serverSearchActivity.loadingView.getVisibility(), is(View.VISIBLE));
  }

  @Test
  public void shouldHideLoading() throws Exception {
    serverSearchActivity.hideLoading();

    assertThat(serverSearchActivity.loadingView.getVisibility(), is(View.GONE));
  }

  @Test
  public void shouldStartQrScanner() throws Exception {
    serverSearchActivity.startQrScanner();

    assertThat(serverSearchActivity.closeCameraView.getVisibility(), is(View.VISIBLE));
    assertThat(serverSearchActivity.qrReaderView.getVisibility(), is(View.VISIBLE));
  }

  @Test
  public void shouldStopQrScanner() throws Exception {
    serverSearchActivity.stopQrScanner();

    assertThat(serverSearchActivity.qrReaderView.getVisibility(), is(View.GONE));
    assertThat(serverSearchActivity.closeCameraView.getVisibility(), is(View.GONE));
  }

  @Test
  public void shouldDelegateClickFabWifiToPresenter() throws Exception {
    serverSearchActivity.onFabWifiButtonClick(mock(View.class));

    verify(mockServerSearchPresenter, times(1)).onClickScanWifi();
  }

  @Test
  public void shouldDelegateClickFabQrCodeToPresenter() throws Exception {
    serverSearchActivity.onFabQrButtonClick(mock(View.class));

    verify(mockServerSearchPresenter, times(1)).onClickScanQrCode();
  }

  @Test
  public void shouldDelegateClickFabIpToPresenter() throws Exception {
    serverSearchActivity.onFabIpButtonClick(mock(View.class));

    verify(mockServerSearchPresenter, times(1)).onClickEnterNetworkAddress();
  }

  @Test
  public void shouldDelegateClickCloseCameraToPresenter() throws Exception {
    serverSearchActivity.onCloseCameraButtonClick(mock(View.class));

    verify(mockServerSearchPresenter, times(1)).onClickCloseCamera();
  }

  private void setUpCameraFeature() {
    ShadowPackageManager robolectricPackageManager = shadowOf(getApp().getPackageManager());
    robolectricPackageManager.setSystemFeature(PackageManager.FEATURE_CAMERA, true);
  }

  private RemotecraftMockApp getApp() {
    return (RemotecraftMockApp) RuntimeEnvironment.application;
  }
}