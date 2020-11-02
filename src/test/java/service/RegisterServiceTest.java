package service;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
  private static RegisterService registerService;
  private static ClearService clearService;
  private RegisterRequest request;
  private RegisterResult result;
  private RegisterResult compareTest;

  @BeforeEach
  void setUp() {
    registerService = new RegisterService();
    clearService = new ClearService();
  }

  @AfterEach
  void tearDown() {
    clearService.clear();
  }

  @Test
  void registerTest_goodRequest() {
    request = new RegisterRequest("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M");
    compareTest = new RegisterResult("uuid", "radicalGrim", "uuid", true);
    result = registerService.register(request);

    assertNull(result.getMessage());
    assertEquals(compareTest.getSuccess(), result.getSuccess());
    assertEquals(compareTest.getUsername(), result.getUsername());
    assertNotNull(result.getAuthToken());
    assertNotNull(result.getPersonId());
  }

  @Test
  void registerTest_usernameTaken() {
    request = new RegisterRequest("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M");
    compareTest = new RegisterResult("Username taken", false);
    result = registerService.register(request);
    result = registerService.register(request);

    assertEquals(compareTest.getMessage(), result.getMessage());
    assertEquals(compareTest.getSuccess(), result.getSuccess());
  }
}