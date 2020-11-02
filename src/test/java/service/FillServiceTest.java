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
  private FillResult compareTest;

  @BeforeAll
  static void declare() {
    User bestUser = new User("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId");
    User[] users = new User[]{bestUser};
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
    FillResult result = new FillResult("Invalid username", false);
    compareTest = fillService.fill("badUsername", "4");

    assertEquals(result.getSuccess(), compareTest.getSuccess());
    assertEquals(result.getMessage(), compareTest.getMessage());
  }

  @Test
  void fillTest_nonNumberGeneration() {
    FillResult result = new FillResult("Invalid input. Please enter a positive number of generations", false);
    compareTest = fillService.fill("radicalGrim", "h");

    assertEquals(result.getSuccess(), compareTest.getSuccess());
    assertEquals(result.getMessage(), compareTest.getMessage());
  }

  @Test
  void fillTest_negativeGeneration() {
    FillResult result = new FillResult("Please enter a positive number of generations", false);
    compareTest = fillService.fill("radicalGrim", "-1");

    assertEquals(result.getSuccess(), compareTest.getSuccess());
    assertEquals(result.getMessage(), compareTest.getMessage());
  }

  @Test
  void fillTest_zeroGenerations() {
    FillResult result = new FillResult("Successfully added 1 persons, and 1 events to the database.", true);
    compareTest = fillService.fill("radicalGrim", "0");

    assertEquals(result.getSuccess(), compareTest.getSuccess());
    assertEquals(result.getMessage(), compareTest.getMessage());
  }

  @Test
  void fillTest_oneGeneration() {
    FillResult result = new FillResult("Successfully added 3 persons, and 7 events to the database.", true);
    compareTest = fillService.fill("radicalGrim", "1");

    assertEquals(result.getMessage(), compareTest.getMessage());
    assertEquals(result.getSuccess(), compareTest.getSuccess());
  }

  @Test
  void fillTest_multipleGenerations() {
    FillResult result = new FillResult("Successfully added 31 persons, and 91 events to the database.", true);
    compareTest = fillService.fill("radicalGrim", "4");

    assertEquals(result.getMessage(), compareTest.getMessage());
    assertEquals(result.getSuccess(), compareTest.getSuccess());
  }

  @Test
  void fillTest_chainedRequests() {
    FillResult result = new FillResult("Successfully added 15 persons, and 43 events to the database.", true);
    compareTest = fillService.fill("radicalGrim", "3");

    assertEquals(result.getMessage(), compareTest.getMessage());
    assertEquals(result.getSuccess(), compareTest.getSuccess());

    result = new FillResult("Successfully added 63 persons, and 187 events to the database.", true);
    compareTest = fillService.fill("radicalGrim", "5");

    assertEquals(result.getMessage(), compareTest.getMessage());
    assertEquals(result.getSuccess(), compareTest.getSuccess());
  }
}