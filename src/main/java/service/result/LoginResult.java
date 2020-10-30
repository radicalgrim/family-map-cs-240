package service.result;

public class LoginResult extends ErrorResult {
  private String authToken;
  private String username;
  private String personId;

  public LoginResult() {
  }

  public LoginResult(String message, Boolean success) {
    super(message, success);
  }

  public LoginResult(String authToken, String username, String personId, Boolean success) {
    this.authToken = authToken;
    this.username = username;
    this.personId = personId;
    this.success = success;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String personId) {
    this.personId=personId;
  }
}
