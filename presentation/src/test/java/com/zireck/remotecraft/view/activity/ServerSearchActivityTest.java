package com.zireck.remotecraft.view.activity;

import android.content.pm.PackageManager;
import com.zireck.remotecraft.BuildConfig;
import com.zireck.remotecraft.RemotecraftApp;
import com.zireck.remotecraft.dagger.components.ApplicationComponent;
import com.zireck.remotecraft.dagger.modules.ApplicationModule;
import com.zireck.remotecraft.presenter.ServerSearchPresenter;
import com.zireck.remotecraft.robostuff.camera.CameraRule;
import com.zireck.remotecraft.robostuff.camera.EnableCamera;
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
import org.robolectric.res.builder.RobolectricPackageManager;

@RunWith(RobolectricTestRunner.class) @Config(constants = BuildConfig.class)
public class ServerSearchActivityTest {

  private ServerSearchActivity serverSearchActivity;

  @Rule public DaggerMockRule<ApplicationComponent> daggerMockRule =
      new DaggerMockRule<>(ApplicationComponent.class, new ApplicationModule(getApp())).set(
          component -> getApp().setApplicationComponent(component));
  @Rule public CameraRule cameraRule = new CameraRule();

  @Mock private ServerSearchPresenter mockServerSearchPresenter;

  @Before public void setUp() throws Exception {
    RobolectricPackageManager pm =
        (RobolectricPackageManager) RuntimeEnvironment.application.getPackageManager();
    pm.setSystemFeature(PackageManager.FEATURE_CAMERA, true);

    serverSearchActivity = Robolectric.setupActivity(ServerSearchActivity.class);
  }

  @Test @EnableCamera public void shouldName() throws Exception {
    // TODO
  }

  private RemotecraftApp getApp() {
    return (RemotecraftApp) RuntimeEnvironment.application;
  }
}