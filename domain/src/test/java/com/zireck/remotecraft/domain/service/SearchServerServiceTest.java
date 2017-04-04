package com.zireck.remotecraft.domain.service;

import com.zireck.remotecraft.domain.NetworkAddress;
import com.zireck.remotecraft.domain.provider.NetworkActionProvider;
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

@RunWith(MockitoJUnitRunner.class) public class SearchServerServiceTest {

  private SearchServerService searchServerService;

  @Mock private NetworkActionProvider mockNetworkActionProvider;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    searchServerService = new SearchServerService(mockNetworkActionProvider);
  }

  @Test public void shouldSearchServer() throws Exception {
    searchServerService.searchServer();

    verify(mockNetworkActionProvider, times(1)).searchServer();
    verifyNoMoreInteractions(mockNetworkActionProvider);
  }

  @Test public void shouldSearchServerForNetworkAddress() throws Exception {
    NetworkAddress mockNetworkAddress = mock(NetworkAddress.class);

    searchServerService.searchServer(mockNetworkAddress);

    verify(mockNetworkActionProvider, times(1)).searchServer(mockNetworkAddress);
    verifyNoMoreInteractions(mockNetworkActionProvider);
  }
}