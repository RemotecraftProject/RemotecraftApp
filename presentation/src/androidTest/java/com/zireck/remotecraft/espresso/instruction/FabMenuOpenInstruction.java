package com.zireck.remotecraft.espresso.instruction;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import com.azimolabs.conditionwatcher.Instruction;
import com.github.clans.fab.FloatingActionMenu;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.RemotecraftApp;

public class FabMenuOpenInstruction extends Instruction {

  @Override public String getDescription() {
    return "FloatingActionMenu should be open";
  }

  @Override public boolean checkCondition() {
    Activity currentActivity = ((RemotecraftApp) InstrumentationRegistry.getTargetContext()
        .getApplicationContext()).getCurrentActivity();
    if (currentActivity == null) {
      return false;
    }

    FloatingActionMenu floatingActionMenu =
        (FloatingActionMenu) currentActivity.findViewById(R.id.menu);

    return floatingActionMenu != null && floatingActionMenu.isOpened();
  }
}
