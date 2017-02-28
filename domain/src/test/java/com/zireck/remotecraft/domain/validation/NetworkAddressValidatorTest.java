package com.zireck.remotecraft.domain.validation;

import com.zireck.remotecraft.domain.NetworkAddress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class) public class NetworkAddressValidatorTest {

  private NetworkAddressValidator networkAddressValidator;

  @Before public void setUp() throws Exception {
    networkAddressValidator = new NetworkAddressValidator();
  }

  @Test public void shouldCheckInvalidWhenNullGiven() throws Exception {
    boolean valid = networkAddressValidator.isValid(null);

    assertThat(valid, is(false));
  }

  @Test public void shouldCheckInvalidWhenNullIpGiven() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with(null)
        .and(4356)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid, is(false));
  }

  @Test public void shouldCheckInvalidWhenEmptyIpGiven() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("")
        .and(4356)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid, is(false));
  }

  @Test public void shouldCheckInvalidWhenNegativePortNumberGiven() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("192.168.1.45")
        .and(-4356)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid, is(false));
  }

  @Test public void shouldCheckInvalidWhenPortNumberExceedingLimitGiven() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("192.168.1.45")
        .and(4356545)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid, is(false));
  }

  @Test public void shouldCheckInvalidWhenInvalidIpGiven() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("945.333.0.257")
        .and(4356)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid, is(false));
  }

  @Test public void shouldCheckValidWhenValidIpAndPortGiven() throws Exception {
    NetworkAddress networkAddress = new NetworkAddress.Builder()
        .with("192.168.1.45")
        .and(4356)
        .build();

    boolean valid = networkAddressValidator.isValid(networkAddress);

    assertThat(valid, is(true));
  }
}