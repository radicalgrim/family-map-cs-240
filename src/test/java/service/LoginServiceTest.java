package service;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.LoadRequest;
import service.request.LoginRequest;
import service.result.LoginResult;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
  private static LoginService loginService;
  private static LoadService loadService;
  private static LoadRequest loadRequest;
  private static ClearService clearService;
  private LoginResult compareTest;

  @BeforeAll
  static void declare() {
    User bestUser = new User("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId");
    User[] users = new User[]{bestUser};
    Person[] persons = new Person[]{};
    Event[] events = new Event[]{};
    loginService = new LoginService();
    loadService = new LoadService();
    loadRequest = new LoadRequest(users, persons, events);
    clearService = new ClearService();
  }

  @BeforeEach
  void setUp() {
    loadService.load(loadRequest);
  }

  @AfterEach
  void tearDown() {
    clearService.clear();
  }

  @Test
  void loginTest_badUsername() {
    LoginRequest badUsernameRequest = new LoginRequest("badUsername", "KilroyWasHere");
    LoginResult badUsernameResult = new LoginResult("Invalid username", false);
    compareTest = loginService.login(badUsernameRequest);

    assertEquals(badUsernameResult.getMessage(), compareTest.getMessage());
    assertEquals(badUsernameResult.getSuccess(), compareTest.getSuccess());
    assertNull(badUsernameResult.getUsername());
    assertNull(badUsernameResult.getAuthToken());
    assertNull(badUsernameResult.getPersonId());
  }

  @Test
  void loginTest_badPassword() {
    LoginRequest badPasswordRequest = new LoginRequest("radicalGrim", "badPassword");
    LoginResult badPasswordResult = new LoginResult("Invalid password", false);
    compareTest = loginService.login(badPasswordRequest);

    assertEquals(badPasswordResult.getMessage(), compareTest.getMessage());
    assertEquals(badPasswordResult.getSuccess(), compareTest.getSuccess());
    assertNull(badPasswordResult.getUsername());
    assertNull(badPasswordResult.getAuthToken());
    assertNull(badPasswordResult.getPersonId());
  }

  @Test
  void loginTest_goodLogin() {
    LoginRequest goodRequest = new LoginRequest("radicalGrim", "KilroyWasHere");
    LoginResult goodResult = new LoginResult("uuid", "radicalGrim", "personId", true);
    compareTest = loginService.login(goodRequest);

    assertNull(goodResult.getMessage());
    assertEquals(goodResult.getSuccess(), compareTest.getSuccess());
    assertEquals(goodResult.getUsername(), compareTest.getUsername());
    assertNotNull(goodResult.getAuthToken());
    assertEquals(goodResult.getPersonId(), compareTest.getPersonId());
  }
}