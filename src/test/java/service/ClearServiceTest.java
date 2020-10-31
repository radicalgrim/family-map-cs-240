package service;

import DAO.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
  private Database db;
  private ClearService service;
  private LoadService load;

  /*
  private Event bestEvent;
  private EventDAO eventDAO;
  private Person bestPerson;
  private PersonDAO personDAO;
  private User bestUser;
  private UserDAO userDAO;
  private AuthToken bestToken;
  private AuthTokenDAO authTokenDAO;
  private final String uuid = UUID.randomUUID().toString();
   */

  @BeforeEach
  void setUp() throws DataAccessException {
    load = new LoadService();

    /*
    db = new Database();
    bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    bestPerson = new Person("personId", "radicalGrim", "Josh", "Reese",
            "M", "fatherId", "motherId", "spouseId");
    bestUser = new User("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId");
    bestToken = new AuthToken(uuid, "radicalGrim");
    Connection conn = db.openConnection();
    db.clearTables();
    eventDAO = new EventDAO(conn);
    personDAO = new PersonDAO(conn);
    userDAO = new UserDAO(conn);
    authTokenDAO = new AuthTokenDAO(conn);
    service = new ClearService();
     */
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }

  @Test
  void clear() throws DataAccessException {
    /*
    eventDAO.insert(bestEvent);
    personDAO.insert(bestPerson);
    userDAO.insert(bestUser);
    authTokenDAO.insert(bestToken);

    service.clear();

    assertNull(eventDAO.find(bestEvent.getEventID()));
    assertNull(personDAO.find(bestPerson.getId()));
    assertNull(userDAO.find(bestUser.getUsername()));
    assertNull(authTokenDAO.find(bestToken.getUsername()));
*/
  }
}

/*
2 test cases for each public method found in DAO and Service classes

One positive & one negative or 2 positive test cases

One positive test should test the main usage scenario, the other should test an alternative scenario

Most (if not all) test cases will involve multiple assertions.

Examples:
  try to log in with a bad password
  try to find a personID that does not exist
  pass invalid parameters

Make them unique
*/