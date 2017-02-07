package com.zireck.remotecraft.infrastructure.tool;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class) public class NetworkConnectionlessDatagramTransmitterTest {

  private NetworkConnectionlessDatagramTransmitter networkConnectionlessDatagramTransmitter;

  @Mock private DatagramSocket mockDatagramSocket;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    networkConnectionlessDatagramTransmitter =
        new NetworkConnectionlessDatagramTransmitter(mockDatagramSocket);
  }

  @Test public void shouldNotBeReadyGivenANullDatagramSocket() throws Exception {
    NetworkConnectionlessDatagramTransmitter
        networkConnectionlessDatagramTransmitterWithNullDatagramSocket =
        new NetworkConnectionlessDatagramTransmitter(null);
    boolean isReady = networkConnectionlessDatagramTransmitterWithNullDatagramSocket.isReady();

    assertThat(isReady, is(false));
  }

  @Test public void shouldBeReadyGivenANonNullDatagramSocket() throws Exception {
    boolean isReady = networkConnectionlessDatagramTransmitter.isReady();

    assertThat(isReady, is(true));
  }

  @Test public void shouldSetBroadcastProperly() throws Exception {
    networkConnectionlessDatagramTransmitter.setBroadcast(true);

    verify(mockDatagramSocket, times(1)).setBroadcast(true);
    verifyNoMoreInteractions(mockDatagramSocket);
  }

  @Test public void shouldProperlySetTimeout() throws Exception {
    networkConnectionlessDatagramTransmitter.setTimeout(60);

    verify(mockDatagramSocket, times(1)).setSoTimeout(60);
    verifyNoMoreInteractions(mockDatagramSocket);
  }

  @Test public void shouldProperlySendDatagramPacket() throws Exception {
    String packetValue = "something";
    DatagramPacket datagramPacket =
        new DatagramPacket(packetValue.getBytes(), packetValue.length());
    networkConnectionlessDatagramTransmitter.send(datagramPacket);

    verify(mockDatagramSocket, times(1)).send(datagramPacket);
    verifyNoMoreInteractions(mockDatagramSocket);
  }

  @Test public void shouldProperlyReceiveDatagramPacket() throws Exception {
    String packetValue = "something";
    DatagramPacket datagramPacket =
        new DatagramPacket(packetValue.getBytes(), packetValue.length());
    networkConnectionlessDatagramTransmitter.receive(datagramPacket);

    verify(mockDatagramSocket, times(1)).receive(datagramPacket);
    verifyNoMoreInteractions(mockDatagramSocket);
  }

  @Test public void shouldProperlyShutdownTheSocket() throws Exception {
    networkConnectionlessDatagramTransmitter.shutdown();

    verify(mockDatagramSocket, times(1)).disconnect();
    verify(mockDatagramSocket, times(1)).close();
    verifyNoMoreInteractions(mockDatagramSocket);
  }
}