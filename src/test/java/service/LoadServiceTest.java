package service;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import result.LoadResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoadServiceTest {
  private static LoadService loadService;
  private static User[] users;
  private static Person[] persons;
  private static Event[] events;

  @BeforeAll
  static void declare() {
    User sampleUser1 = new User("currentUser", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId1");
    User sampleUser2 = new User("otherUser", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId2");
    users = new User[] {sampleUser1, sampleUser2};
    Person samplePerson1 = new Person("personId1", "currentUser", "Josh", "Reese",
            "M", "fatherId", "motherId", "spouseId");
    Person samplePerson2 = new Person("personId2", "otherUser", "Josh", "Reese",
            "M", "fatherId", "motherId", "spouseId");
    persons = new Person[] {samplePerson1, samplePerson2};
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
    events = new Event[] {sampleEvent1, sampleEvent2, sampleEvent3, sampleEvent4};

    loadService = new LoadService();
  }

  @Test
  void loadTest_validLoad() {
    LoadResult compareTest = new LoadResult("Successfully added 2 users, 2 persons, and 4 events to the database.", true);
    LoadRequest loadRequest = new LoadRequest(users, persons, events);
    LoadResult actual = loadService.load(loadRequest);

    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertEquals(compareTest.getMessage(), actual.getMessage());
  }

  @Test
  void loadTest_emptyParameter() {
    LoadResult compareTest = new LoadResult("Error: One of the fields was empty" , false);
    LoadRequest loadRequest = new LoadRequest(users, persons, null);
    LoadResult actual = loadService.load(loadRequest);

    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertEquals(compareTest.getMessage(), actual.getMessage());
  }
}