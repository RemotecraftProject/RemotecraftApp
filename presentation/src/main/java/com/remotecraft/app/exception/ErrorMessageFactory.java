package com.remotecraft.app.exception;

import android.content.Context;
import com.remotecraft.app.R;
import com.remotecraft.app.infrastructure.exception.InvalidServerException;
import com.remotecraft.app.infrastructure.exception.NoInternetConnectionException;
import com.remotecraft.app.infrastructure.exception.NoResponseException;

public class ErrorMessageFactory {

  private ErrorMessageFactory() {

  }

  public static String create(Context context, Exception exception) {
    String errorMessage = context.getString(R.string.exception_generic);

    if (exception instanceof NoInternetConnectionException) {
      errorMessage = context.getString(R.string.exception_no_internet_connection);
    } else if (exception instanceof NoResponseException) {
      errorMessage = context.getString(R.string.exception_no_response);
    } else if (exception instanceof InvalidServerException) {
      errorMessage = context.getString(R.string.exception_invalid_server);
    } else if (exception.getMessage() != null && !exception.getMessage().isEmpty()) {
      errorMessage = exception.getMessage();
    }

    return errorMessage;
  }
}
