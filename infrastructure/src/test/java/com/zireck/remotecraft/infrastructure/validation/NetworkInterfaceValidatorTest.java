package com.zireck.remotecraft.infrastructure.validation;

import java.net.NetworkInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class) @PrepareForTest({ NetworkInterface.class })
public class NetworkInterfaceValidatorTest {

  private NetworkInterfaceValidator networkInterfaceValidator;

  private NetworkInterface mockNetworkInterface;

  @Before public void setUp() throws Exception {
    networkInterfaceValidator = new NetworkInterfaceValidator();

    mockNetworkInterface = PowerMockito.mock(NetworkInterface.class);
  }

  @Test public void shouldEvaluateAsInvalidGivenNullInterface() throws Exception {
    boolean isValid = networkInterfaceValidator.isValid(null);

    assertThat(isValid, is(false));
  }

  @Test public void shouldEvaluateAsInvalidGivenLoopbackInterface() throws Exception {
    when(mockNetworkInterface.isLoopback()).thenReturn(true);

    boolean isValid = networkInterfaceValidator.isValid(mockNetworkInterface);

    assertThat(isValid, is(false));
  }

  @Test public void shouldEvaluateAsInvalidGivenDownInterface() throws Exception {
    when(mockNetworkInterface.isLoopback()).thenReturn(false);
    when(mockNetworkInterface.isUp()).thenReturn(false);

    boolean isValid = networkInterfaceValidator.isValid(mockNetworkInterface);

    assertThat(isValid, is(false));
  }

  @Test public void shouldEvaluateAsValidGivenValidInterface() throws Exception {
    when(mockNetworkInterface.isLoopback()).thenReturn(false);
    when(mockNetworkInterface.isUp()).thenReturn(true);

    boolean isValid = networkInterfaceValidator.isValid(mockNetworkInterface);

    assertThat(isValid, is(true));
  }
}