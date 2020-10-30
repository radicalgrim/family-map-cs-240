package service.result;

public class ClearResult extends ErrorResult {
  public ClearResult() {
    super();
  }

  public ClearResult(String message, Boolean success) {
    super(message, success);
  }

  // TODO: Write a toJsonString() method that overrides the ErrorResult one
}
