package com.remotecraft.app.infrastructure.validation;

import java.net.NetworkInterface;
import java.net.SocketException;
import javax.inject.Inject;

public class NetworkInterfaceValidator implements Validator<NetworkInterface> {

  @Inject public NetworkInterfaceValidator() {

  }

  @Override public boolean isValid(NetworkInterface networkInterface) {
    boolean isValid = false;

    try {
      isValid =
          networkInterface != null && !networkInterface.isLoopback() && networkInterface.isUp();
    } catch (SocketException e) {
      e.printStackTrace();
    }

    return isValid;
  }
}
