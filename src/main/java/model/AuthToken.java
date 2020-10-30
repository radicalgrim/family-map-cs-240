package model;

public class AuthToken {
  private String authToken;
  private String username;

  public AuthToken() {
  }

  public AuthToken(String authToken, String username) {
    this.authToken=authToken;
    this.username=username;
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

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof AuthToken) {
      AuthToken oAuthToken = (AuthToken) o;
      return oAuthToken.getAuthToken().equals(getAuthToken()) &&
              oAuthToken.getUsername().equals(getUsername());
    } else {
      return false;
    }
  }
}
