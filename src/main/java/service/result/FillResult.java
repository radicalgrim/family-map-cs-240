package service.result;

public class FillResult extends ErrorResult {
  public FillResult() {
  }

  public FillResult(String message, Boolean success) {
    super(message, success);
  }
}
