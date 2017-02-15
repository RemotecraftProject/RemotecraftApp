package com.zireck.remotecraft.espresso.action;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import com.github.clans.fab.FloatingActionMenu;
import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;

public class FabMenuCloseAction implements ViewAction {
  @Override public Matcher<View> getConstraints() {
    return isAssignableFrom(FloatingActionMenu.class);
  }

  @Override public String getDescription() {
    return "FloatingActionMenu Close";
  }

  @Override public void perform(UiController uiController, View view) {
    FloatingActionMenu floatingActionMenu = (FloatingActionMenu) view;
    floatingActionMenu.close(true);
  }
}
