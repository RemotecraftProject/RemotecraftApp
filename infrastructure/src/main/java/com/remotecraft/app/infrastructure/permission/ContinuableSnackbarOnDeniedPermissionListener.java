package com.remotecraft.app.infrastructure.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;

/**
 * Credits to Karumi for SnackbarOnDeniedPermissionListener.java
 */
public class ContinuableSnackbarOnDeniedPermissionListener extends BasePermissionListener {

  private final ViewGroup rootView;
  private final String text;
  private final String buttonText;
  private final View.OnClickListener onButtonClickListener;
  private final Snackbar.Callback snackbarCallback;
  private final int duration;

  /**
   * @param rootView Parent view to show the snackbar
   * @param text Message displayed in the snackbar
   * @param buttonText Message displayed in the snackbar button
   * @param onButtonClickListener Action performed when the user clicks the snackbar button
   */
  private ContinuableSnackbarOnDeniedPermissionListener(ViewGroup rootView, String text,
      String buttonText, View.OnClickListener onButtonClickListener,
      Snackbar.Callback snackbarCallback, int duration) {
    this.rootView = rootView;
    this.text = text;
    this.buttonText = buttonText;
    this.onButtonClickListener = onButtonClickListener;
    this.snackbarCallback = snackbarCallback;
    this.duration = duration;
  }

  @Override public void onPermissionDenied(PermissionDeniedResponse response) {
    super.onPermissionDenied(response);

    Snackbar snackbar = Snackbar.make(rootView, text, duration);
    if (buttonText != null && onButtonClickListener != null) {
      snackbar.setAction(buttonText, onButtonClickListener);
    }
    if (snackbarCallback != null) {
      snackbar.setCallback(snackbarCallback);
    }
    snackbar.show();
  }

  @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
      PermissionToken token) {
    // do nothing
  }

  /**
   * Builder class to configure the displayed snackbar
   * Non set fields will not be shown
   */
  public static class Builder {
    private final ViewGroup rootView;
    private final String text;
    private String buttonText;
    private View.OnClickListener onClickListener;
    private Snackbar.Callback snackbarCallback;
    private int duration = Snackbar.LENGTH_LONG;

    private Builder(ViewGroup rootView, String text) {
      this.rootView = rootView;
      this.text = text;
    }

    public static ContinuableSnackbarOnDeniedPermissionListener.Builder with(ViewGroup rootView,
        String text) {
      return new ContinuableSnackbarOnDeniedPermissionListener.Builder(rootView, text);
    }

    public static ContinuableSnackbarOnDeniedPermissionListener.Builder with(ViewGroup rootView,
        @StringRes int textResourceId) {
      return ContinuableSnackbarOnDeniedPermissionListener.Builder.with(rootView,
          rootView.getContext().getString(textResourceId));
    }

    /**
     * Adds a text button with the provided click listener
     */
    public ContinuableSnackbarOnDeniedPermissionListener.Builder withButton(String buttonText,
        View.OnClickListener onClickListener) {
      this.buttonText = buttonText;
      this.onClickListener = onClickListener;
      return this;
    }

    /**
     * Adds a text button with the provided click listener
     */
    public ContinuableSnackbarOnDeniedPermissionListener.Builder withButton(
        @StringRes int buttonTextResourceId, View.OnClickListener onClickListener) {
      return withButton(rootView.getContext().getString(buttonTextResourceId), onClickListener);
    }

    /**
     * Adds a button that opens the application settings when clicked
     */
    public ContinuableSnackbarOnDeniedPermissionListener.Builder withOpenSettingsButton(
        String buttonText) {
      this.buttonText = buttonText;
      this.onClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
          Context context = rootView.getContext();
          Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
              Uri.parse("package:" + context.getPackageName()));
          myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
          myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          context.startActivity(myAppSettings);
        }
      };
      return this;
    }

    /**
     * Adds a button that opens the application settings when clicked
     */
    public ContinuableSnackbarOnDeniedPermissionListener.Builder withOpenSettingsButton(
        @StringRes int buttonTextResourceId) {
      return withOpenSettingsButton(rootView.getContext().getString(buttonTextResourceId));
    }

    /**
     * Adds a callback to handle the snackbar {@code onDismissed} and {@code onShown} events
     */
    public ContinuableSnackbarOnDeniedPermissionListener.Builder withCallback(
        Snackbar.Callback callback) {
      this.snackbarCallback = callback;
      return this;
    }

    /**
     * Adds the duration of the snackbar on the screen
     */
    public ContinuableSnackbarOnDeniedPermissionListener.Builder withDuration(int duration) {
      this.duration = duration;
      return this;
    }

    /**
     * Builds a new instance of {@link SnackbarOnDeniedPermissionListener}
     */
    public ContinuableSnackbarOnDeniedPermissionListener build() {
      return new ContinuableSnackbarOnDeniedPermissionListener(rootView, text, buttonText,
          onClickListener, snackbarCallback, duration);
    }
  }
}
