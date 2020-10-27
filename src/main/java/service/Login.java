package service;

import service.request.LoginRequest;
import service.result.LoginResult;

public class Login {
  public Login() {
  }

  public LoginResult login(LoginRequest request) { return new LoginResult(); }
}
