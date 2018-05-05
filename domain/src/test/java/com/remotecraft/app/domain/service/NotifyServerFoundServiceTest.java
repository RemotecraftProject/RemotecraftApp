package com.remotecraft.app.domain.service;

import com.remotecraft.app.domain.Server;
import com.remotecraft.app.domain.provider.NotificationActionProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class) public class NotifyServerFoundServiceTest {

  private NotifyServerFoundService notifyServerFoundService;

  @Mock private NotificationActionProvider mockNotificationActionProvider;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    notifyServerFoundService = new NotifyServerFoundService(mockNotificationActionProvider);
  }

  @Test public void shouldNotifyServerFound() throws Exception {
    Server mockServer = mock(Server.class);

    notifyServerFoundService.notifyServerFound(mockServer);

    verify(mockNotificationActionProvider, times(1)).notifyServerFound(mockServer);
    verifyNoMoreInteractions(mockNotificationActionProvider);
  }
}