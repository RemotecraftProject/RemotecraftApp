package com.remotecraft.app.tools;

import android.support.annotation.NonNull;
import com.remotecraft.app.R;
import javax.inject.Inject;

public class OsIconProvider {

  @Inject
  public OsIconProvider() {

  }

  public int getIconForOs(@NonNull String osName) {
    if (osName.contains("indows")) {
      return R.drawable.ic_os_windows_dark;
    } else if (osName.contains("inux")) {
      return R.drawable.ic_os_linux_dark;
    } else if (osName.contains("ac")) {
      return R.drawable.ic_os_mac_dark;
    } else {
      return R.drawable.ic_mac_desktop;
    }
  }
}
