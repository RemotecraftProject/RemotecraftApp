package com.zireck.remotecraft.espresso.matcher;

import android.view.View;
import org.hamcrest.Matcher;

public class EspressoCustomMatchers {

  public static Matcher<View> withDrawable(final int resourceId) {
    return new DrawableMatcher(resourceId);
  }

  public static Matcher<View> noDrawable() {
    return new DrawableMatcher(-1);
  }

  public static Matcher<View> fabMenuOpen() {
    return new FabMenuOpenMatcher();
  }
}
