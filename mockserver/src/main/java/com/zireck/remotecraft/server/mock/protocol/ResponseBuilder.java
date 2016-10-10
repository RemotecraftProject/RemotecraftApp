package com.zireck.remotecraft.server.mock.protocol;

public class ResponseBuilder<T extends BaseData> {

  private BaseMessage baseMessage;

  public ResponseBuilder() {
    baseMessage = new BaseMessage<T>();
  }

  public ResponseBuilder success() {
    baseMessage.setSuccess(true);
    return this;
  }

  public ResponseBuilder failure() {
    baseMessage.setSuccess(false);
    return this;
  }

  public ResponseBuilder setData(T data) {
    baseMessage.setData(data);
    return this;
  }

  public ResponseBuilder setError(int code, String message) {
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setCode(code);
    errorMessage.setMessage(message);
    baseMessage.setError(errorMessage);
    return this;
  }

  public BaseMessage build() {
    return baseMessage;
  }
}
