package com.zireck.remotecraft.view.activity;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.zireck.remotecraft.FabMenuClose;
import com.zireck.remotecraft.FabMenuOpen;
import com.zireck.remotecraft.R;
import com.zireck.remotecraft.TimerIdlingResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class) public class SearchServerActivityInstrumentationTest {

  private static final long FAB_MENU_DELAY_IN_MILLIS = 300;

  @Rule public ActivityTestRule<SearchServerActivity> activityTestRule =
      new ActivityTestRule<SearchServerActivity>(SearchServerActivity.class);

  @Test public void shouldProperlyOpenTheFabMenu() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpen());
    TimerIdlingResource timerIdlingResource = startTiming(FAB_MENU_DELAY_IN_MILLIS);
    onView(withId(R.id.fab_qrcode)).check(matches(isDisplayed()));
    stopTiming(timerIdlingResource);
  }

  @Test public void shouldProperlyCloseTheFabMenu() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuClose());
    TimerIdlingResource timerIdlingResource = startTiming(FAB_MENU_DELAY_IN_MILLIS);
    onView(withId(R.id.fab_qrcode)).check(matches(not(isDisplayed())));
    stopTiming(timerIdlingResource);
  }

  @Test public void shouldProperlyOpenAndCloseTheFabMenu() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpen());
    TimerIdlingResource timerIdlingResource = startTiming(FAB_MENU_DELAY_IN_MILLIS);
    onView(withId(R.id.fab_qrcode)).check(matches(isDisplayed()));
    stopTiming(timerIdlingResource);
    onView(withId(R.id.menu)).perform(new FabMenuClose());
    TimerIdlingResource secondTimerIdlingResource = startTiming(FAB_MENU_DELAY_IN_MILLIS);
    onView(withId(R.id.fab_qrcode)).check(matches(not(isDisplayed())));
    stopTiming(secondTimerIdlingResource);
  }

  @Test public void shouldDisplayEnterAddressDialog() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpen());
    TimerIdlingResource timerIdlingResource = startTiming(FAB_MENU_DELAY_IN_MILLIS);
    onView(withId(R.id.fab_ip)).check(matches(isDisplayed()));
    stopTiming(timerIdlingResource);
    onView(withId(R.id.fab_ip)).perform(click());
    onView(withText(R.string.enter_network_address_dialog_button_accept)).check(
        matches(not(isDisplayed())));
  }

  @Test public void shouldDismissEnterAddressDialogWhenCancel() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpen());
    TimerIdlingResource timerIdlingResource = startTiming(FAB_MENU_DELAY_IN_MILLIS);
    onView(withId(R.id.fab_ip)).check(matches(isDisplayed()));
    stopTiming(timerIdlingResource);
    onView(withId(R.id.fab_ip)).check(matches(isDisplayed())).perform(click());
    Espresso.registerIdlingResources(timerIdlingResource);
    onView(withText(R.string.enter_network_address_dialog_button_accept)).check(
        matches(isDisplayed()));
    onView(withText(R.string.enter_network_address_dialog_button_cancel)).check(
        matches(isDisplayed())).perform(click());
    onView(withText(R.string.enter_network_address_dialog_button_accept)).check(doesNotExist());
  }

  private TimerIdlingResource startTiming(final long timeInMillis) {
    TimerIdlingResource timerIdlingResource = new TimerIdlingResource(timeInMillis);
    Espresso.registerIdlingResources(timerIdlingResource);

    return timerIdlingResource;
  }

  private void stopTiming(TimerIdlingResource timerIdlingResource) {
    Espresso.unregisterIdlingResources(timerIdlingResource);
  }
}