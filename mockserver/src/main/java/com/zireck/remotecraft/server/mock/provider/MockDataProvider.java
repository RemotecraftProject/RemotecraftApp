package com.zireck.remotecraft.server.mock.provider;

import com.zireck.remotecraft.server.mock.protocol.data.ServerProtocol;
import com.zireck.remotecraft.server.mock.protocol.messages.ServerMessage;

public class MockDataProvider implements ServerProvider {

  @Override public ServerMessage getServerData() {
    ServerProtocol serverProtocol =
        new ServerProtocol("MOVISTAR_C33", "85.215.47.129", "iMac", "macOS Sierra", "2.8.14",
            "4346234563458034", "Reign of Giants", "Etho");

    return new ServerMessage.Builder()
        .with(serverProtocol)
        .build();
  }
}
