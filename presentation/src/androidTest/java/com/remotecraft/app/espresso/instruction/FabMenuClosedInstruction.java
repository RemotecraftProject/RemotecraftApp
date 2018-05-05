package com.remotecraft.app.espresso.instruction;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import com.azimolabs.conditionwatcher.Instruction;
import com.github.clans.fab.FloatingActionMenu;
import com.remotecraft.app.R;
import com.remotecraft.app.RemotecraftApp;

public class FabMenuClosedInstruction extends Instruction {

  @Override public String getDescription() {
    return "FloatingActionMenu should be closed";
  }

  @Override public boolean checkCondition() {
    Activity currentActivity = ((RemotecraftApp) InstrumentationRegistry.getTargetContext()
        .getApplicationContext()).getCurrentActivity();
    if (currentActivity == null) {
      return false;
    }

    FloatingActionMenu floatingActionMenu =
        (FloatingActionMenu) currentActivity.findViewById(R.id.menu);

    return floatingActionMenu != null && !floatingActionMenu.isOpened();
  }
}
