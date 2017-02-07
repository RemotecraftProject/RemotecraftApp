package com.zireck.remotecraft.infrastructure.validation;

import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.data.Server;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ServerMessageValidator implements MessageValidator<Server> {

  private List<InvalidServerData> invalidServerData;

  @Inject
  public ServerMessageValidator() {

  }

  @Override public boolean isValid(Message message) {
    invalidServerData = validate(message);
    return invalidServerData.size() == 0;
  }

  @Override public Server cast(Message message) {
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

    Server server = cast(message);

    if (server.getIp() == null || server.getIp().length() <= 0) {
      invalidServerData.add(InvalidServerData.SERVER_NO_IP);
    }

    if (server.getSeed() == null || server.getSeed().length() <= 0) {
      invalidServerData.add(InvalidServerData.SERVER_NO_SEED);
    }

    return invalidServerData;
  }
}
