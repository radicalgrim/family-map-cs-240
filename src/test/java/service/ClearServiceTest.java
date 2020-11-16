package service;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import request.LoginRequest;
import result.ClearResult;
import result.LoginResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClearServiceTest {
  private static ClearService clearService;
  private static LoadService loadService;
  private static LoadRequest loadRequest;
  private static LoginService loginService;
  private static LoginRequest loginRequest;

  @BeforeAll
  static void declare() {
    User sampleUser1 = new User("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId1");
    User sampleUser2 = new User("otherUser", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId2");
    User[] users = new User[] {sampleUser1, sampleUser2};
    Person samplePerson1 = new Person("personId1", "currentUser", "Josh", "Reese",
            "M", "fatherId", "motherId", "spouseId");
    Person samplePerson2 = new Person("personId2", "otherUser", "Josh", "Reese",
            "M", "fatherId", "motherId", "spouseId");
    Person[] persons = new Person[] {samplePerson1, samplePerson2};
    Event sampleEvent1 = new Event("Biking_123A", "otherUser2", "personId2",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    Event sampleEvent2 = new Event("Biking_321A", "otherUser2", "personId2",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    Event sampleEvent3 = new Event("RG_Birth", "currentUser", "personId1",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 1993);
    Event sampleEvent4 = new Event("RG_Death", "currentUser", "personId1",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2070);
    Event[] events = new Event[] {sampleEvent1, sampleEvent2, sampleEvent3, sampleEvent4};

    loadRequest = new LoadRequest(users, persons, events);
    loginRequest = new LoginRequest("radicalGrim", "KilroyWasHere");
    loadService = new LoadService();
    clearService = new ClearService();
    loginService = new LoginService();

  }

  @Test
  void clearTest() {
    loadService.load(loadRequest);
    ClearResult compareTest = new ClearResult("Clear succeeded.", true);
    ClearResult actual = clearService.clear();

    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertEquals(compareTest.getMessage(), actual.getMessage());

    loginService.login(loginRequest);

    LoginResult loginCompareTest = new LoginResult("Error: Invalid username", false);
    LoginResult loginActual = loginService.login(loginRequest);

    assertEquals(loginCompareTest.getSuccess(), loginActual.getSuccess());
    assertEquals(loginCompareTest.getMessage(), loginActual.getMessage());
  }
}