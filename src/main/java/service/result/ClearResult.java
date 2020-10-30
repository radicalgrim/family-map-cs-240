package service.result;

public class ClearResult extends ErrorResult {
  public ClearResult() {
  }

  public ClearResult(String message, Boolean success) {
    super(message, success);
  }
}
