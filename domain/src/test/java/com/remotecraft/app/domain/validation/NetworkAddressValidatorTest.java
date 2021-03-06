package com.remotecraft.app.domain.validation;

import com.remotecraft.app.domain.NetworkAddress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class NetworkAddressValidatorTest {

  private NetworkAddressValidator networkAddressValidator;

  @Before
  public void setUp() throws Exception {
    networkAddressValidator = new NetworkAddressValidator();
  }

  @Test
  public void shouldCheckInvalidWhenNullGiven() throws Exception {
    boolean valid = networkAddressValidator.isValid(null);

    assertThat(valid).isFalse();
  }

  @Test
  public void shouldCheckInvalidWhenNullIpGiven() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("")
        .port(4356)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid).isFalse();
  }

  @Test
  public void shouldCheckInvalidWhenEmptyIpGiven() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("")
        .port(4356)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid).isFalse();
  }

  @Test
  public void shouldCheckInvalidWhenNegativePortNumberGiven() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("192.168.1.45")
        .port(-4356)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid).isFalse();
  }

  @Test
  public void shouldCheckInvalidWhenPortNumberExceedingLimitGiven() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("192.168.1.45")
        .port(4356545)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid).isFalse();
  }

  @Test
  public void shouldCheckInvalidWhenInvalidIpGiven() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("945.333.0.257")
        .port(4356)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid).isFalse();
  }

  @Test
  public void shouldCheckValidWhenValidIpAndPortGiven() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("192.168.1.45")
        .port(4356)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid).isTrue();
  }
}