package service.result;

public class LoadResult extends ErrorResult {
  public LoadResult() {
  }

  public LoadResult(String message, Boolean success) {
    super(message, success);
  }
}
