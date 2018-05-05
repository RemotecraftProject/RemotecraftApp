package com.remotecraft.app.espresso.matcher;

import android.view.View;
import com.github.clans.fab.FloatingActionMenu;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class FabMenuOpenMatcher extends TypeSafeMatcher<View> {

  @Override protected boolean matchesSafely(View target) {
    if (!(target instanceof FloatingActionMenu)) {
      return false;
    }

    FloatingActionMenu floatingActionMenu = (FloatingActionMenu) target;

    return floatingActionMenu.isOpened();
  }

  @Override public void describeTo(Description description) {
    description.appendText("FabMenu is open");
  }
}
