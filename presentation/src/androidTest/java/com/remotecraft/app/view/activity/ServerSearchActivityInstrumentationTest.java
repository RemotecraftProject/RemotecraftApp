package com.remotecraft.app.view.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.azimolabs.conditionwatcher.ConditionWatcher;
import com.remotecraft.app.R;
import com.remotecraft.app.espresso.action.FabMenuCloseAction;
import com.remotecraft.app.espresso.action.FabMenuOpenAction;
import com.remotecraft.app.espresso.instruction.FabMenuClosedInstruction;
import com.remotecraft.app.espresso.instruction.FabMenuOpenInstruction;
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
import static com.remotecraft.app.espresso.matcher.EspressoCustomMatchers.fabMenuOpen;
import static com.remotecraft.app.espresso.matcher.EspressoCustomMatchers.withDrawable;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class) public class ServerSearchActivityInstrumentationTest {

  @Rule public ActivityTestRule<ServerSearchActivity> activityTestRule =
      new ActivityTestRule<>(ServerSearchActivity.class);

  @Test public void shouldDisplayTheDesertBackground() throws Exception {
    onView(withId(R.id.background)).check(matches(withDrawable(R.drawable.desert)));
  }

  @Test public void shouldDisplayClosedFabMenuInitially() throws Exception {
    onView(withId(R.id.menu)).check(matches(not(fabMenuOpen())));
  }

  @Test public void shouldProperlyOpenTheFabMenu() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpenAction());
    ConditionWatcher.waitForCondition(new FabMenuOpenInstruction());

    onView(withId(R.id.menu)).check(matches(fabMenuOpen()));
  }

  @Test public void shouldProperlyCloseTheFabMenu() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuCloseAction());
    ConditionWatcher.waitForCondition(new FabMenuClosedInstruction());

    onView(withId(R.id.menu)).check(matches(not(fabMenuOpen())));
  }

  @Test public void shouldProperlyOpenAndThenCloseTheFabMenu() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpenAction());
    ConditionWatcher.waitForCondition(new FabMenuOpenInstruction());
    onView(withId(R.id.menu)).check(matches(fabMenuOpen()));


    onView(withId(R.id.menu)).perform(new FabMenuCloseAction());
    ConditionWatcher.waitForCondition(new FabMenuClosedInstruction());
    onView(withId(R.id.menu)).check(matches(not(fabMenuOpen())));
  }

  @Test public void shouldDisplayAllFabItemsWhenFabMenuIsOpen() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpenAction());
    ConditionWatcher.waitForCondition(new FabMenuOpenInstruction());

    onView(withId(R.id.fab_wifi)).check(matches(isDisplayed()));
    onView(withId(R.id.fab_qrcode)).check(matches(isDisplayed()));
    onView(withId(R.id.fab_ip)).check(matches(isDisplayed()));
  }

  @Test public void shouldDimTheBackgroundWhenTheFabMenuIsOpen() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpenAction());
    ConditionWatcher.waitForCondition(new FabMenuOpenInstruction());

    onView(withId(R.id.dimmer_view)).check(matches(isDisplayed()));
  }

  @Test public void shouldNotDimTheBackgroundWhenTheFabMenuIsClosed() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuCloseAction());
    ConditionWatcher.waitForCondition(new FabMenuClosedInstruction());

    onView(withId(R.id.dimmer_view)).check(matches(not(isDisplayed())));
  }

  @Test public void shouldDisplayEnterAddressDialog() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpenAction());
    ConditionWatcher.waitForCondition(new FabMenuOpenInstruction());
    onView(withId(R.id.fab_ip)).check(matches(isDisplayed()));
    onView(withId(R.id.fab_ip)).perform(click());

    onView(withText(R.string.enter_network_address_dialog_title)).check(
        matches(isDisplayed()));
  }

  @Test public void shouldDismissEnterAddressDialogWhenCancel() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpenAction());
    ConditionWatcher.waitForCondition(new FabMenuOpenInstruction());
    onView(withId(R.id.fab_ip)).check(matches(isDisplayed())).perform(click());
    onView(withText(R.string.enter_network_address_dialog_title)).check(
        matches(isDisplayed()));
    onView(withText(R.string.enter_network_address_dialog_button_cancel)).check(
        matches(isDisplayed())).perform(click());

    onView(withText(R.string.enter_network_address_dialog_button_accept)).check(doesNotExist());
  }

  @Test public void shouldNotifyInvalidAddressWhenEnteringEmptyIpAndPortInEnterAddressDialog()
      throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpenAction());
    ConditionWatcher.waitForCondition(new FabMenuOpenInstruction());
    onView(withId(R.id.fab_ip)).check(matches(isDisplayed())).perform(click());
    onView(withText(R.string.enter_network_address_dialog_title)).check(matches(isDisplayed()));
    onView(withText(R.string.enter_network_address_dialog_button_accept)).check(
        matches(isDisplayed())).perform(click());

    onView(withId(android.support.design.R.id.snackbar_text)).check(matches(isDisplayed()));
  }

  @Test public void shouldDisplayCameraPreviewWhenScanningQrCode() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpenAction());
    ConditionWatcher.waitForCondition(new FabMenuOpenInstruction());
    onView(withId(R.id.fab_qrcode)).check(matches(isDisplayed()));
    onView(withId(R.id.fab_qrcode)).perform(click());

    onView(withId(R.id.qrCodeReaderView)).check(matches(isDisplayed()));
    onView(withId(R.id.close_camera_button)).check(matches(isDisplayed()));
  }

  @Test public void shouldCloseCameraPreviewWhenClickingCloseButton() throws Exception {
    onView(withId(R.id.menu)).perform(new FabMenuOpenAction());
    ConditionWatcher.waitForCondition(new FabMenuOpenInstruction());
    onView(withId(R.id.fab_qrcode)).check(matches(isDisplayed()));
    onView(withId(R.id.fab_qrcode)).perform(click());
    onView(withId(R.id.close_camera_button)).check(matches(isDisplayed())).perform(click());

    onView(withId(R.id.qrCodeReaderView)).check(matches(not(isDisplayed())));
    onView(withId(R.id.close_camera_button)).check(matches(not(isDisplayed())));
  }
}