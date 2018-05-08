package com.remotecraft.app.domain.interactor;

import com.remotecraft.app.domain.executor.PostExecutionThread;
import com.remotecraft.app.domain.executor.ThreadExecutor;
import com.remotecraft.app.domain.provider.ReceiverActionProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetWifiStateInteractorTest {

  private GetWifiStateInteractor getWifiStateInteractor;

  @Mock private ReceiverActionProvider mockReceiverActionProvider;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    getWifiStateInteractor = new GetWifiStateInteractor(mockReceiverActionProvider, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void shouldBuildReactiveStreamProperly() throws Exception {
    getWifiStateInteractor.buildReactiveStream(null);

    verify(mockReceiverActionProvider).getWifiState();
    verifyNoMoreInteractions(mockReceiverActionProvider);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }
}