package com.zireck.remotecraft.server.mock.provider;

import com.zireck.remotecraft.server.mock.protocol.data.Server;
import com.zireck.remotecraft.server.mock.protocol.messages.ServerMessage;

public class MockDataProvider implements ServerProvider {

  @Override public ServerMessage getServerData() {
    ServerMessage serverMessage = new ServerMessage.Builder().success(true)
        .server(new Server("MOVISTAR_C33", "85.215.47.129", "2.8.14", "4346234563458034",
            "The New World", "Zireck"))
        .build();

    return serverMessage;
  }
}
