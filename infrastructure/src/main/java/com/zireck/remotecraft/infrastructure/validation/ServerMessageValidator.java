package com.zireck.remotecraft.infrastructure.validation;

import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.base.type.ServerProtocol;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ServerMessageValidator implements MessageValidator<ServerProtocol> {

  private List<InvalidServerData> invalidServerData;

  @Inject
  public ServerMessageValidator() {

  }

  @Override public boolean isValid(Message message) {
    invalidServerData = validate(message);
    return invalidServerData.size() == 0;
  }

  @Override public ServerProtocol cast(Message message) {
    return message.getServer();
  }

  public List<InvalidServerData> getInvalidServerData() {
    return invalidServerData;
  }

  private List<InvalidServerData> validate(Message message) {
    List<InvalidServerData> invalidServerData = new ArrayList<>();

    if (message == null || !message.isSuccess() || !message.isServer()) {
      invalidServerData.add(InvalidServerData.SERVER_NULL);
      return invalidServerData;
    }

    ServerProtocol serverProtocol = cast(message);

    if (serverProtocol.getIp() == null || serverProtocol.getIp().length() <= 0) {
      invalidServerData.add(InvalidServerData.SERVER_NO_IP);
    }

    if (serverProtocol.getSeed() == null || serverProtocol.getSeed().length() <= 0) {
      invalidServerData.add(InvalidServerData.SERVER_NO_SEED);
    }

    return invalidServerData;
  }
}
