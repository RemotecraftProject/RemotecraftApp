package com.zireck.remotecraft.infrastructure.validation;

import android.text.TextUtils;
import com.zireck.remotecraft.infrastructure.entity.WorldEntity;
import java.util.ArrayList;
import java.util.List;

public class WorldValidator implements Validator<WorldEntity> {

  private WorldEntity worldEntity;
  private List<InvalidData> invalidData;

  public WorldValidator() {

  }

  @Override public boolean isValid(WorldEntity wo) {
    validate();
    return invalidData.size() == 0;
  }

  @Override public List<InvalidData> getInvalidData() {
    return invalidData;
  }

  private void validate() {
    invalidData = new ArrayList<>();

    if (worldEntity == null) {
      invalidData.add(InvalidData.SERVER_NULL);
      return;
    }

    if (TextUtils.isEmpty(worldEntity.getIp())) {
      invalidData.add(InvalidData.SERVER_NO_IP);
    }

    if (TextUtils.isEmpty(worldEntity.getSeed())) {
      invalidData.add(InvalidData.SERVER_NO_SEED);
    }
  }
}
