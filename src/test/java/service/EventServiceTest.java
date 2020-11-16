package service;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import request.LoginRequest;
import result.EventResult;
import result.LoginResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventServiceTest {
  private static EventService eventService;
  private static Event[] myEvents;
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
    myEvents = new Event[] {sampleEvent3, sampleEvent4};

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

    eventService = new EventService();

  }

  @Test
  void eventTest_badAuthToken() {
    EventResult compareTest = new EventResult("Error: Invalid auth token", false);
    EventResult actual = eventService.event(null, "BlahBlah");

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void eventTest_mismatchedAuthToken() {
    EventResult compareTest = new EventResult("Error: Invalid auth token", false);
    EventResult actual = eventService.event("Biking_123A", uuidCurrentUser);

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void eventTest_goodAuthToken_noEvents() {
    EventResult compareTest = new EventResult("Error: No events found for given user", false);
    EventResult actual = eventService.event(null, uuidOtherUser);

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void eventTest_singleEvent_goodRequest() {
    EventResult compareTest = new EventResult("currentUser", "RG_Birth", "personId1",
            35.9f, 140.1f, "Japan", "Ushiku", "Biking_Around", 1993,
            true);
    EventResult actual = eventService.event("RG_Birth", uuidCurrentUser);

    assertEquals(compareTest.getAssociatedUsername(), actual.getAssociatedUsername());
    assertEquals(compareTest.getEventID(), actual.getEventID());
    assertEquals(compareTest.getPersonID(), actual.getPersonID());
    assertEquals(compareTest.getLatitude(), actual.getLatitude());
    assertEquals(compareTest.getLongitude(), actual.getLongitude());
    assertEquals(compareTest.getCountry(), actual.getCountry());
    assertEquals(compareTest.getCity(), actual.getCity());
    assertEquals(compareTest.getEventType(), actual.getEventType());
    assertEquals(compareTest.getYear(), actual.getYear());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void eventTest_allEvents_goodRequest() {
    EventResult compareTest = new EventResult(myEvents, true);
    EventResult actual = eventService.event(null, uuidCurrentUser);

    assertEquals(myEvents[0], actual.getData()[0]);
    assertEquals(myEvents[1], actual.getData()[1]);
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }


}