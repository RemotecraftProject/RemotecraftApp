package com.zireck.remotecraft.infrastructure.helper;

public class NetworkProtocolHelper {
  public static final int DISCOVERY_PORT = 9998;
  public static final String DISCOVERY_REQUEST = "REMOTECRAFT_DISCOVERY_REQUEST";
  public static final String DISCOVERY_RESPONSE = "REMOTECRAFT_DISCOVERY_RESPONSE";
  public static final int SOCKET_TIMEOUT = 1000;

  public static final String COMMAND_SEPARATOR = ":";
  public static final String COMMAND_SEPARATOR_SECONDARY = "_";

  public static String getCommand(String message) {
    String command = "";

    try {
      command = message.split(NetworkProtocolHelper.COMMAND_SEPARATOR)[0];
    } catch (Exception e) {
      e.printStackTrace();
    }

    return command;
  }

  public static String getArg(String message) {
    String arg = "";

    try {
      if (message.split(NetworkProtocolHelper.COMMAND_SEPARATOR).length > 1) {
        arg = message.split(NetworkProtocolHelper.COMMAND_SEPARATOR)[1];
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return arg;
  }
}
