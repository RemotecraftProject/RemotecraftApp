package com.zireck.remotecraft.domain.validation;

import com.zireck.remotecraft.domain.NetworkAddress;
import javax.inject.Inject;
import org.apache.commons.validator.routines.InetAddressValidator;

public class NetworkAddressValidator implements Validator<NetworkAddress> {

  @Inject public NetworkAddressValidator() {

  }

  @Override public boolean isValid(NetworkAddress networkAddress) {
    if (networkAddress == null) {
      return false;
    }

    String ip = networkAddress.getIp();
    int port = networkAddress.getPort();
    if (ip == null || ip.isEmpty() || port <= 0 || port > 65535) {
      return false;
    }

    InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
    return inetAddressValidator.isValid(ip);
  }
}
