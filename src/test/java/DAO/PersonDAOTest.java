package DAO;

import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonDAOTest {
  private Database db;
  private Person samplePerson1;
  private Person samplePerson2;
  private Person samplePerson3;
  private PersonDAO personDAO;

  @BeforeEach
  void setUp() throws DataAccessException {
    db = new Database();
    samplePerson1 = new Person("personId1", "radicalGrim", "Josh", "Reese",
            "M", "fatherId1", "motherId1", "spouseId1");
    samplePerson2 = new Person("personId2", "radicalGrim", "Josh", "Reese",
            "M", "fatherId2", "motherId2", "spouseId2");
    samplePerson3 = new Person("personId3", "DifferentUser", "Josh", "Reese",
            "M", "fatherId3", "motherId3", "spouseId3");
    Connection conn = db.getConnection();
    db.clearTables();
    personDAO = new PersonDAO(conn);
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }

  @Test
  void insertPass() throws DataAccessException {
    personDAO.insert(samplePerson1);
    Person compareTest = personDAO.find(samplePerson1.getId());
    assertNotNull(compareTest);
    assertEquals(samplePerson1, compareTest);
  }

 @Test
  void insertFail() throws DataAccessException {
    personDAO.insert(samplePerson1);
    assertThrows(DataAccessException.class, ()-> personDAO.insert(samplePerson1));
  }

  @Test
  public void findPass() throws DataAccessException {
    personDAO.insert(samplePerson1);
    Person compareTest = personDAO.find(samplePerson1.getId());
    assertNotNull(compareTest);
    assertEquals(samplePerson1, compareTest);
  }

  @Test
  public void findFail() throws DataAccessException {
    assertNull(personDAO.find(samplePerson1.getId()));
  }

  @Test
  public void findUserEventsPass() throws DataAccessException {
    personDAO.insert(samplePerson1); // radicalGrim
    personDAO.insert(samplePerson2); // radicalGrim
    personDAO.insert(samplePerson3); // DifferentUser
    Person[] compareTest = personDAO.findUserPersons("radicalGrim");
    assertNotNull(compareTest);
    assertEquals(samplePerson1, compareTest[0]);
    assertEquals(samplePerson2, compareTest[1]);
    assertEquals(2, compareTest.length);
  }

  @Test
  public void findUserEventsFail() throws DataAccessException {
    personDAO.insert(samplePerson3); // DifferentUser
    assertNull(personDAO.findUserPersons("radicalGrim"));
  }

  @Test
  public void deleteTest() throws DataAccessException {
    personDAO.insert(samplePerson1);
    personDAO.delete();
    assertNull(personDAO.find(samplePerson1.getId()));
  }

  @Test
  public void deleteUserEventsTest() throws DataAccessException {
    personDAO.insert(samplePerson1); // radicalGrim
    personDAO.insert(samplePerson2); // radicalGrim
    personDAO.insert(samplePerson3); // DifferentUser
    personDAO.deleteUserPersons("radicalGrim");
    assertNull(personDAO.find(samplePerson1.getId()));
    assertNull(personDAO.find(samplePerson2.getId()));
    assertEquals(samplePerson3, personDAO.find(samplePerson3.getId()));
  }
}