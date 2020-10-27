package service.result;

public class LoginResult extends ErrorResult {
  private String authToken;
  private String username;
  private String personId;

  public LoginResult() {
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
