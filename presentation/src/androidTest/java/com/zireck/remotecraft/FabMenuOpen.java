package com.zireck.remotecraft;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import com.github.clans.fab.FloatingActionMenu;
import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;

public class FabMenuOpen implements ViewAction {
  @Override public Matcher<View> getConstraints() {
    return isAssignableFrom(FloatingActionMenu.class);
  }

  @Override public String getDescription() {
    return "FloatingActionMenu Open";
  }

  @Override public void perform(UiController uiController, View view) {
    FloatingActionMenu floatingActionMenu = (FloatingActionMenu) view;
    floatingActionMenu.open(true);
  }
}
