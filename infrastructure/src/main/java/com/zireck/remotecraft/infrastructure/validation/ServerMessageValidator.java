package com.zireck.remotecraft.infrastructure.validation;

import android.text.TextUtils;
import com.zireck.remotecraft.infrastructure.protocol.base.Message;
import com.zireck.remotecraft.infrastructure.protocol.data.Server;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ServerMessageValidator implements MessageValidator<Server> {

  private List<InvalidData> invalidData;

  @Inject
  public ServerMessageValidator() {

  }

  @Override public boolean isValid(Message message) {
    invalidData = validate(message);
    return invalidData.size() == 0;
  }

  @Override public Server cast(Message message) {
    return message.getServer();
  }

  @Override public List<InvalidData> getInvalidData() {
    return invalidData;
  }

  private List<InvalidData> validate(Message message) {
    List<InvalidData> invalidData = new ArrayList<>();

    if (message == null || !message.isSuccess() || !message.isServer()) {
      invalidData.add(InvalidData.SERVER_NULL);
      return invalidData;
    }

    Server server = cast(message);

    if (TextUtils.isEmpty(server.getIp())) {
      invalidData.add(InvalidData.SERVER_NO_IP);
    }

    if (TextUtils.isEmpty(server.getSeed())) {
      invalidData.add(InvalidData.SERVER_NO_SEED);
    }

    return invalidData;
  }
}
