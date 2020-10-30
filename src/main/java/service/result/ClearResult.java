package service.result;

public class ClearResult extends ErrorResult {
  public ClearResult() {
    super();
  }

  public ClearResult(String message, Boolean success) {
    super(message, success);
  }
}
