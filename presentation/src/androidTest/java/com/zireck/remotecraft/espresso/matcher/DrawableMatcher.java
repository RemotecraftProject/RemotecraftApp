package com.zireck.remotecraft.espresso.matcher;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DrawableMatcher extends TypeSafeMatcher<View> {

  private final int expectedDrawableId;
  private String resourceName;

  public DrawableMatcher(int expectedDrawableId) {
    super(View.class);
    this.expectedDrawableId = expectedDrawableId;
  }

  @Override protected boolean matchesSafely(View target) {
    if (!(target instanceof ImageView)) {
      return false;
    }

    ImageView targetImageView = (ImageView) target;
    if (expectedDrawableId < 0) {
      return targetImageView.getDrawable() == null;
    }

    Resources resources = targetImageView.getContext().getResources();
    Drawable expectedDrawable = resources.getDrawable(expectedDrawableId);
    resourceName = resources.getResourceEntryName(expectedDrawableId);
    if (expectedDrawable == null) {
      return false;
    }

    BitmapDrawable targetBitmapDrawable = (BitmapDrawable) targetImageView.getDrawable();
    Bitmap targetBitmap = targetBitmapDrawable.getBitmap();

    BitmapDrawable expectedBitmapDrawable = (BitmapDrawable) expectedDrawable;
    Bitmap expectedBitmap = expectedBitmapDrawable.getBitmap();

    return targetBitmap.sameAs(expectedBitmap);
  }

  @Override public void describeTo(Description description) {
    description.appendText("with drawable from resource id: ");
    description.appendValue(expectedDrawableId);
    if (resourceName != null) {
      description.appendText("[");
      description.appendText(resourceName);
      description.appendText("]");
    }
  }
}
