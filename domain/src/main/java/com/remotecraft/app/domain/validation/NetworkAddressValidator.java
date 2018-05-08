package com.remotecraft.app.domain.validation;

import com.remotecraft.app.domain.NetworkAddress;
import javax.inject.Inject;
import org.apache.commons.validator.routines.InetAddressValidator;

public class NetworkAddressValidator implements Validator<NetworkAddress> {

  @Inject
  public NetworkAddressValidator() {

  }

  @Override
  public boolean isValid(NetworkAddress networkAddress) {
    if (networkAddress == null) {
      return false;
    }

    String ip = networkAddress.ip();
    int port = networkAddress.port();
    if (ip == null || ip.isEmpty() || port <= 0 || port > 65535) {
      return false;
    }

    InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
    return inetAddressValidator.isValid(ip);
  }
}
