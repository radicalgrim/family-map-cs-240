package service;

import service.request.LoginRequest;
import service.result.LoginResult;

public class LoginService {
  public LoginService() {
  }

  public LoginResult login(LoginRequest request) { return new LoginResult(); }
}
