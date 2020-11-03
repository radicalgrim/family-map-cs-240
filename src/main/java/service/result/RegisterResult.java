package service.result;

import com.google.gson.annotations.SerializedName;

public class RegisterResult extends ErrorResult {
  private String authToken;
  @SerializedName("userName")
  private String username;
  @SerializedName("personID")
  private String personId;

  public RegisterResult() {
  }

  public RegisterResult(String message, Boolean success) {
    super(message, success);
  }

  public RegisterResult(String authToken, String username, String personId, Boolean success) {
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
