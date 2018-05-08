package com.remotecraft.app.view;

import android.support.annotation.NonNull;
import com.remotecraft.app.model.ServerModel;

public interface ServerFoundView extends BaseView {
  void renderServer(@NonNull ServerModel serverModel);
  void showError(@NonNull String errorMessage);
  void navigateBack(boolean isSuccess, ServerModel serverModel);
}
