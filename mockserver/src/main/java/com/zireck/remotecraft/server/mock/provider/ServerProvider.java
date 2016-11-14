package com.zireck.remotecraft.server.mock.provider;

import com.zireck.remotecraft.server.mock.protocol.messages.ServerMessage;

public interface ServerProvider {
  ServerMessage getServerData();
}
