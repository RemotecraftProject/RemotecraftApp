package com.zireck.remotecraft.tools;

import android.net.Uri;
import javax.inject.Inject;

public class UriParser {

  @Inject public UriParser() {

  }

  public Uri parse(String uriString) {
    return Uri.parse(uriString);
  }

  public String getQueryParameter(Uri uri, String parameter) {
    return uri.getQueryParameter(parameter);
  }
}
