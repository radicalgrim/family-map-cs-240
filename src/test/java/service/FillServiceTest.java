package service;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.LoadRequest;
import service.result.FillResult;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {
  private static FillService fillService;
  private static LoadService loadService;
  private static LoadRequest loadRequest;
  private static ClearService clearService;

  @BeforeAll
  static void declare() {
    User sampleUser = new User("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId");
    User[] users = new User[]{sampleUser};
    Person[] persons = new Person[]{};
    Event[] events = new Event[]{};
    fillService = new FillService();
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
  void fillTest_invalidUsername() {
    FillResult compareTest = new FillResult("Error: Invalid username", false);
    FillResult actual = fillService.fill("badUsername", "4");

    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertEquals(compareTest.getMessage(), actual.getMessage());
  }

  @Test
  void fillTest_nonNumberGeneration() {
    FillResult compareTest = new FillResult("Error: Invalid input. Please enter a positive number of generations", false);
    FillResult actual = fillService.fill("radicalGrim", "h");

    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertEquals(compareTest.getMessage(), actual.getMessage());
  }

  @Test
  void fillTest_negativeGeneration() {
    FillResult compareTest = new FillResult("Error: Please enter a positive number of generations", false);
    FillResult actual = fillService.fill("radicalGrim", "-1");

    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertEquals(compareTest.getMessage(), actual.getMessage());
  }

  @Test
  void fillTest_zeroGenerations() {
    FillResult compareTest = new FillResult("Successfully added 1 persons and 1 events to the database.", true);
    FillResult actual = fillService.fill("radicalGrim", "0");

    assertEquals(compareTest.getSuccess(), actual.getSuccess());
    assertEquals(compareTest.getMessage(), actual.getMessage());
  }

  @Test
  void fillTest_oneGeneration() {
    FillResult compareTest = new FillResult("Successfully added 3 persons and 7 events to the database.", true);
    FillResult actual = fillService.fill("radicalGrim", "1");

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void fillTest_multipleGenerations() {
    FillResult compareTest = new FillResult("Successfully added 31 persons and 91 events to the database.", true);
    FillResult actual = fillService.fill("radicalGrim", "4");

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }

  @Test
  void fillTest_chainedRequests() {
    FillResult compareTest = new FillResult("Successfully added 15 persons and 43 events to the database.", true);
    FillResult actual = fillService.fill("radicalGrim", "3");

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());

    compareTest = new FillResult("Successfully added 63 persons and 187 events to the database.", true);
    actual = fillService.fill("radicalGrim", "5");

    assertEquals(compareTest.getMessage(), actual.getMessage());
    assertEquals(compareTest.getSuccess(), actual.getSuccess());
  }
}