package com.remotecraft.app.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.remotecraft.app.model.ServerModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class) public class NavigatorTest {

  private Navigator navigator;

  @Mock private Context mockContext;
  @Mock private Activity mockActivity;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    navigator = new Navigator();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionNavigatingToServerSearchActivityGivenNullContext()
      throws Exception {
    navigator.navigateToServerSearchActivity(null);
  }

  @Test public void shouldProperlyNavigateToServerSearchActivity() throws Exception {
    navigator.navigateToServerSearchActivity(mockContext);

    verify(mockContext, times(1)).startActivity(any(Intent.class));
    verifyNoMoreInteractions(mockContext);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionNavigatingToServerFoundActivityGivenNullActivity()
      throws Exception {
    ServerModel serverModel = ServerModel.builder()
        .ssid("WLAN_C33")
        .ip("192.168.1.34")
        .hostname("iMac")
        .os("macOS")
        .version("1.48")
        .seed("34640505599934")
        .worldName("Reign of Giants")
        .playerName("Etho")
        .encodedWorldImage("base64image")
        .build();

    navigator.navigateToServerFoundActivity(null, serverModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionNavigatingToServerFoundActivityGivenNullServerModel()
      throws Exception {
    navigator.navigateToServerFoundActivity(mockActivity, null);
  }

  // TODO use PowerMock
/*  @Test public void shouldProperlyNavigateToServerFoundActivity() throws Exception {
    ServerModel serverModel = new ServerModel.Builder()
        .worldName("Reign of Giants")
        .build();

    navigator.navigateToServerFoundActivity(mockActivity, serverModel);

    verify(mockActivity, times(1)).startActivityForResult(any(Intent.class),
        Navigator.RequestCode.SERVER_FOUND);
  }*/

  @Test public void shouldProperlyFinishActivity() throws Exception {
    navigator.finishActivity(mockActivity);

    verify(mockActivity, times(1)).finish();
    verifyNoMoreInteractions(mockActivity);
  }
}