package service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
  private static RegisterService registerService;
  private static ClearService clearService;

  @BeforeAll
  static void declare() {
    registerService = new RegisterService();
    clearService = new ClearService();
  }

  @AfterEach
  void tearDown() {
    clearService.clear();
  }

  @Test
  void registerTest_goodRequest() {
    RegisterRequest request = new RegisterRequest("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M");
    RegisterResult compareTest = new RegisterResult("uuid", "radicalGrim", "uuid", true);
    RegisterResult actual = registerService.register(request);

    assertNull(actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertEquals(compareTest.getUsername(), actual.getUsername());
    assertNotNull(actual.getAuthToken());
    assertNotNull(actual.getPersonId());
  }

  @Test
  void registerTest_usernameTaken() {
    RegisterRequest request = new RegisterRequest("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M");
    RegisterResult compareTest = new RegisterResult("Error: Username taken", false);
    registerService.register(request);
    RegisterResult actual = registerService.register(request);

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }
}