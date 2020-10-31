package service.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
  @SerializedName("userName")
  private String username;
  private String password;

  public LoginRequest() {
  }
  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password=password;
  }
}
