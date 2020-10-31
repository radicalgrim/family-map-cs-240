package service;

import DAO.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.LoadRequest;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
  ClearService clearService;
  LoadService loadService;
  LoadRequest loadRequest;
  User bestUser1;
  User bestUser2;
  UserDAO userDAO;
  Person bestPerson1;
  Person bestPerson2;
  PersonDAO personDAO;
  Event bestEvent1;
  Event bestEvent2;
  EventDAO eventDAO;
  Database db;

  @BeforeEach
  void setUp() {
    bestUser1 = new User("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId1");
    bestUser2 = new User("vladimirBim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId2");
    User[] users = new User[]{bestUser1, bestUser2};
    bestPerson1 = new Person("personId1", "radicalGrim", "Josh", "Reese",
            "M", "fatherId", "motherId", "spouseId");
    bestPerson2 = new Person("personId2", "vladimirBim", "Josh", "Reese",
            "M", "fatherId", "motherId", "spouseId");
    Person[] persons = new Person[]{bestPerson1, bestPerson2};
    bestEvent1 = new Event("Biking_123A", "Gale", "personId1",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    bestEvent2 = new Event("Biking_321A", "Gale", "personId2",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    Event[] events = new Event[]{bestEvent1, bestEvent2};


    loadRequest = new LoadRequest();
    loadRequest.setUsers(users);
    loadRequest.setPersons(persons);
    loadRequest.setEvents(events);

    loadService = new LoadService();
    clearService = new ClearService();
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }

  @Test
  void load() throws DataAccessException {
    loadService.load(loadRequest);
    clearService.clear();

    db = new Database();
    Connection conn = db.openConnection();

    userDAO = new UserDAO(conn);
    assertNull(userDAO.find(bestUser1.getUsername()));
    assertNull(userDAO.find(bestUser2.getUsername()));

    personDAO = new PersonDAO(conn);
    assertNull(personDAO.find(bestPerson1.getId()));
    assertNull(personDAO.find(bestPerson2.getId()));

    eventDAO = new EventDAO(conn);
    assertNull(eventDAO.find(bestEvent1.getEventID()));
    assertNull(eventDAO.find(bestEvent2.getEventID()));

  }
}