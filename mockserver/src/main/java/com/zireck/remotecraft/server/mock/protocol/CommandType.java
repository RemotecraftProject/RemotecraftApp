package com.zireck.remotecraft.server.mock.protocol;

public enum CommandType {
  GET_WORLD_INFO ("get_world_info");

  private final String command;

  private CommandType(String command) {
    this.command = command;
  }

  @Override public String toString() {
    return command;
  }
}
