package service.result;

public class GetPersonResult extends ErrorResult {
  private String associatedUsername;
  private String personId;
  private String firstName;
  private String lastName;
  private Character gender;
  private String fatherId;
  private String motherId;
  private String spouseId;

  public GetPersonResult() {
  }
}
