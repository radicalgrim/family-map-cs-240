package service;

import service.request.RegisterRequest;
import service.result.RegisterResult;

public class RegisterService {
  public RegisterService() {
  }

  public RegisterResult register(RegisterRequest request) { return new RegisterResult(); }
}
