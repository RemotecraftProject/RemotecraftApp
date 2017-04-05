package com.zireck.remotecraft.infrastructure.tool;

import com.zireck.remotecraft.infrastructure.network.NetworkDatagramTransmitter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class) public class NetworkDatagramTransmitterTest {

  private NetworkDatagramTransmitter networkDatagramTransmitter;

  @Mock private DatagramSocket mockDatagramSocket;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    networkDatagramTransmitter =
        new NetworkDatagramTransmitter(mockDatagramSocket);
  }

  @Test public void shouldNotBeReadyGivenANullDatagramSocket() throws Exception {
    NetworkDatagramTransmitter networkDatagramTransmitterWithNullDatagramSocket =
        new NetworkDatagramTransmitter(null);
    boolean isReady = networkDatagramTransmitterWithNullDatagramSocket.isReady();

    assertThat(isReady).isFalse();
  }

  @Test public void shouldBeReadyGivenANonNullDatagramSocket() throws Exception {
    boolean isReady = networkDatagramTransmitter.isReady();

    assertThat(isReady).isTrue();
  }

  @Test public void shouldSetBroadcastProperly() throws Exception {
    networkDatagramTransmitter.setBroadcast(true);

    verify(mockDatagramSocket, times(1)).setBroadcast(true);
    verifyNoMoreInteractions(mockDatagramSocket);
  }

  @Test public void shouldProperlySetTimeout() throws Exception {
    networkDatagramTransmitter.setTimeout(60);

    verify(mockDatagramSocket, times(1)).setSoTimeout(60);
    verifyNoMoreInteractions(mockDatagramSocket);
  }

  @Test public void shouldProperlySendDatagramPacket() throws Exception {
    String packetValue = "something";
    DatagramPacket datagramPacket =
        new DatagramPacket(packetValue.getBytes(), packetValue.length());
    networkDatagramTransmitter.send(datagramPacket);

    verify(mockDatagramSocket, times(1)).send(datagramPacket);
    verifyNoMoreInteractions(mockDatagramSocket);
  }

  @Test public void shouldProperlyReceiveDatagramPacket() throws Exception {
    String packetValue = "something";
    DatagramPacket datagramPacket =
        new DatagramPacket(packetValue.getBytes(), packetValue.length());
    networkDatagramTransmitter.receive(datagramPacket);

    verify(mockDatagramSocket, times(1)).receive(datagramPacket);
    verifyNoMoreInteractions(mockDatagramSocket);
  }
}