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
import service.result.EventResult;
import service.result.LoginResult;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {
  private static EventService eventService;
  private static Event[] myEvents;
  private static String uuidCurrentUser;
  private static String uuidOtherUser;

  @BeforeAll
  static void declareAndSetUp() {
    User bestUser1 = new User("currentUser", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId1");
    User bestUser2 = new User("otherUser", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId2");
    User[] users = new User[] {bestUser1, bestUser2};
    Person bestPerson1 = new Person("personId1", "currentUser", "Josh", "Reese",
            "M", "fatherId", "motherId", "spouseId");
    Person bestPerson2 = new Person("personId2", "otherUser", "Josh", "Reese",
            "M", "fatherId", "motherId", "spouseId");
    Person[] persons = new Person[] {bestPerson1, bestPerson2};
    Event bestEvent1 = new Event("Biking_123A", "otherUser2", "personId2",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    Event bestEvent2 = new Event("Biking_321A", "otherUser2", "personId2",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    Event bestEvent3 = new Event("RG_Birth", "currentUser", "personId1",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 1993);
    Event bestEvent4 = new Event("RG_Death", "currentUser", "personId1",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2070);
    Event[] events = new Event[] {bestEvent1, bestEvent2, bestEvent3, bestEvent4};
    myEvents = new Event[] {bestEvent3, bestEvent4};

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

  @BeforeEach
  void setUp() {

  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void eventTest_badAuthToken() {
    EventResult compareTest = new EventResult("Invalid auth token", false);
    EventResult actual = eventService.event(null, "BlahBlah");

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void eventTest_mismatchedAuthToken() {
    EventResult compareTest = new EventResult("Invalid auth token", false);
    EventResult actual = eventService.event("Biking_123A", uuidCurrentUser);

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void eventTest_goodAuthToken_noEvents() {
    EventResult compareTest = new EventResult("No events found for given user", false);
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

    assertNotNull(actual.getData());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }


}