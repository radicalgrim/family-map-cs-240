package service;

import service.request.RegisterRequest;
import service.result.RegisterResult;

public class Register {
  public Register() {
  }

  public RegisterResult register(RegisterRequest request) { return new RegisterResult(); }
}
