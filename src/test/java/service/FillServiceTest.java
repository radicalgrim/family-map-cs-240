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
  void fillTest_zeroGenerations() {
    FillResult zeroGenResult = new FillResult("Successfully added 1 persons, and 1 events to the database", true);
    compareTest = fillService.fill("radicalGrim", 0);

    assertEquals(zeroGenResult.getSuccess(), compareTest.getSuccess());
    assertEquals(zeroGenResult.getMessage(), compareTest.getMessage());

  }
}