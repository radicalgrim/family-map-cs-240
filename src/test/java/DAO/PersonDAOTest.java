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
  private Person bestPerson;
  private PersonDAO personDAO;

  @BeforeEach
  void setUp() throws DataAccessException {
    db = new Database();
    bestPerson = new Person("personId", "radicalGrim", "Josh", "Reese",
            "M", "fatherId", "motherId", "spouseId");
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
    personDAO.insert(bestPerson);
    Person compareTest = personDAO.find(bestPerson.getId());
    assertNotNull(compareTest);
    assertEquals(bestPerson, compareTest);
  }

  @Test
  void insertFail() throws DataAccessException {
    personDAO.insert(bestPerson);
    assertThrows(DataAccessException.class, ()-> personDAO.insert(bestPerson));
  }

  @Test
  public void findPass() throws DataAccessException {
    personDAO.insert(bestPerson);
    Person compareTest = personDAO.find(bestPerson.getId());
    assertNotNull(compareTest);
    assertEquals(bestPerson, compareTest);
  }

  @Test
  public void findFail() throws DataAccessException {
    assertNull(personDAO.find(bestPerson.getId()));
  }

  @Test
  public void deleteTest() throws DataAccessException {
    personDAO.insert(bestPerson);
    personDAO.delete();
    assertNull(personDAO.find(bestPerson.getId()));
  }
}