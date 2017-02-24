package com.zireck.remotecraft.infrastructure.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.single.EmptyPermissionListener;

/**
 * Utility listener that shows a {@link Snackbar} with a custom text whenever a permission has been
 * permanently denied.
 *
 * Credits to Karumi.
 */
public class SnackbarOnPermanentlyDeniedPermissionListener extends EmptyPermissionListener {

  private final ViewGroup rootView;
  private final String text;
  private final String buttonText;
  private final View.OnClickListener onButtonClickListener;

  /**
   * @param rootView Parent view to show the snackbar
   * @param text Message displayed in the snackbar
   * @param buttonText Message displayed in the snackbar button
   * @param onButtonClickListener Action performed when the user clicks the snackbar button
   */
  private SnackbarOnPermanentlyDeniedPermissionListener(ViewGroup rootView, String text,
      String buttonText, View.OnClickListener onButtonClickListener) {
    this.rootView = rootView;
    this.text = text;
    this.buttonText = buttonText;
    this.onButtonClickListener = onButtonClickListener;
  }

  @Override public void onPermissionDenied(PermissionDeniedResponse response) {
    super.onPermissionDenied(response);

    if (!response.isPermanentlyDenied()) {
      return;
    }

    Snackbar snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
    if (buttonText != null && onButtonClickListener != null) {
      snackbar.setAction(buttonText, onButtonClickListener);
    }
    snackbar.show();
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

    private Builder(ViewGroup rootView, String text) {
      this.rootView = rootView;
      this.text = text;
    }

    public static SnackbarOnPermanentlyDeniedPermissionListener.Builder with(ViewGroup rootView,
        String text) {
      return new SnackbarOnPermanentlyDeniedPermissionListener.Builder(rootView, text);
    }

    public static SnackbarOnPermanentlyDeniedPermissionListener.Builder with(ViewGroup rootView,
        @StringRes int textResourceId) {
      return SnackbarOnPermanentlyDeniedPermissionListener.Builder.with(rootView,
          rootView.getContext().getString(textResourceId));
    }

    /**
     * Adds a text button with the provided click listener
     */
    public SnackbarOnPermanentlyDeniedPermissionListener.Builder withButton(String buttonText,
        View.OnClickListener onClickListener) {
      this.buttonText = buttonText;
      this.onClickListener = onClickListener;
      return this;
    }

    /**
     * Adds a text button with the provided click listener
     */
    public SnackbarOnPermanentlyDeniedPermissionListener.Builder withButton(
        @StringRes int buttonTextResourceId, View.OnClickListener onClickListener) {
      return withButton(rootView.getContext().getString(buttonTextResourceId), onClickListener);
    }

    /**
     * Adds a button that opens the application settings when clicked
     */
    public SnackbarOnPermanentlyDeniedPermissionListener.Builder withOpenSettingsButton(
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
    public SnackbarOnPermanentlyDeniedPermissionListener.Builder withOpenSettingsButton(
        @StringRes int buttonTextResourceId) {
      return withOpenSettingsButton(rootView.getContext().getString(buttonTextResourceId));
    }

    /**
     * Builds a new instance of {@link SnackbarOnPermanentlyDeniedPermissionListener}
     */
    public SnackbarOnPermanentlyDeniedPermissionListener build() {
      return new SnackbarOnPermanentlyDeniedPermissionListener(rootView, text, buttonText,
          onClickListener);
    }
  }
}
