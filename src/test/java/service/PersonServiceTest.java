package service;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import request.LoginRequest;
import result.PersonResult;
import result.LoginResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonServiceTest {
  private static PersonService personService;
  private static Person[] myPersons;
  private static String uuidCurrentUser;
  private static String uuidOtherUser;

  @BeforeAll
  static void declare() {
    User sampleUser1 = new User("currentUser", "KilroyWasHere", "josh.reese.is@gmail.com",
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
    myPersons = new Person[] {samplePerson1, samplePerson2};

    LoadRequest loadRequest = new LoadRequest(users, persons, events);
    LoadService loadService = new LoadService();
    loadService.load(loadRequest);

    LoginRequest loginRequest = new LoginRequest("currentUser", "KilroyWasHere");
    LoginService loginService = new LoginService();
    LoginResult loginResult = loginService.login(loginRequest);
    uuidCurrentUser = loginResult.getAuthToken();

    loginRequest = new LoginRequest("otherUser", "KilroyWasHere");
    loginResult = loginService.login(loginRequest);
    uuidOtherUser = loginResult.getAuthToken();

    personService = new PersonService();

  }

  @Test
  void personTest_badAuthToken() {
    PersonResult compareTest = new PersonResult("Error: Invalid auth token", false);
    PersonResult actual = personService.person(null, "BlahBlah");

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void personTest_mismatchedAuthToken() {
    PersonResult compareTest = new PersonResult("Error: Invalid auth token", false);
    PersonResult actual = personService.person("personId2", uuidCurrentUser);

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void PersonTest_singlePerson_goodRequest() {
    PersonResult compareTest = new PersonResult("currentUser", "personId1", "Josh",
            "Reese", "M", "fatherId", "motherId", "spouseId", true);
    PersonResult actual = personService.person("personId1", uuidCurrentUser);

    assertEquals(compareTest.getAssociatedUsername(), actual.getAssociatedUsername());
    assertEquals(compareTest.getPersonID(), actual.getPersonID());
    assertEquals(compareTest.getFirstName(), actual.getFirstName());
    assertEquals(compareTest.getLastName(), actual.getLastName());
    assertEquals(compareTest.getGender(), actual.getGender());
    assertEquals(compareTest.getFatherID(), actual.getFatherID());
    assertEquals(compareTest.getMotherID(), actual.getMotherID());
    assertEquals(compareTest.getSpouseID(), actual.getSpouseID());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void PersonTest_allPersons_goodRequest() {
    PersonResult compareTest = new PersonResult(myPersons, true);
    PersonResult actual = personService.person(null, uuidCurrentUser);

    assertEquals(myPersons[0], actual.getData()[0]);
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }
}