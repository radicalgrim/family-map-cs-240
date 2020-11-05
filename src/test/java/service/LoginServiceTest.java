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
    LoginRequest loginRequest = new LoginRequest("badUsername", "KilroyWasHere");
    LoginResult compareTest = new LoginResult("Error: Invalid username", false);
    LoginResult actual = loginService.login(loginRequest);

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertNull(compareTest.getUsername());
    assertNull(compareTest.getAuthToken());
    assertNull(compareTest.getPersonId());
  }

  @Test
  void loginTest_badPassword() {
    LoginRequest loginRequest = new LoginRequest("radicalGrim", "badPassword");
    LoginResult compareTest = new LoginResult("Error: Invalid password", false);
    LoginResult actual = loginService.login(loginRequest);

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertNull(compareTest.getUsername());
    assertNull(compareTest.getAuthToken());
    assertNull(compareTest.getPersonId());
  }

  @Test
  void loginTest_goodLogin() {
    LoginRequest loginRequest = new LoginRequest("radicalGrim", "KilroyWasHere");
    LoginResult compareTest = new LoginResult("uuid", "radicalGrim", "personId", true);
    LoginResult actual = loginService.login(loginRequest);

    assertNull(actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertEquals(compareTest.getUsername(), actual.getUsername());
    assertNotNull(actual.getAuthToken());
    assertEquals(compareTest.getPersonId(), actual.getPersonId());
  }
}