package service.result;

public class ErrorResult {
  String message;
  Boolean success;

  public ErrorResult() {
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success=success;
  }

  // TODO: Write a toJsonString() method that can be overridden
}
