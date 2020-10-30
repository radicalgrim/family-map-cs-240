package DAO;

import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
  private Database db;
  private User bestUser;
  private UserDAO userDAO;

  @BeforeEach
  void setUp() throws DataAccessException {
    db = new Database();
    bestUser = new User("radicalGrim", "KilroyWasHere", "josh.reese.is@gmail.com",
            "Josh", "Reese", "M", "personId");
    Connection conn = db.getConnection();
    db.clearTables();
    userDAO = new UserDAO(conn);
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }

  @Test
  void insertPass() throws DataAccessException {
    userDAO.insert(bestUser);
    User compareTest = userDAO.find(bestUser.getUsername());
    assertNotNull(compareTest);
    assertEquals(bestUser, compareTest);
  }

  @Test
  void insertFail() throws DataAccessException {
    userDAO.insert(bestUser);
    assertThrows(DataAccessException.class, ()-> userDAO.insert(bestUser));
  }

  @Test
  public void findPass() throws DataAccessException {
    userDAO.insert(bestUser);
    User compareTest = userDAO.find(bestUser.getUsername());
    assertNotNull(compareTest);
    assertEquals(bestUser, compareTest);
  }

  @Test
  public void findFail() throws DataAccessException {
    assertNull(userDAO.find(bestUser.getUsername()));
  }

  @Test
  public void findPersonIdPass() throws DataAccessException {
    userDAO.insert(bestUser);
    String compareTest = userDAO.findPersonId(bestUser.getUsername());
    assertNotNull(compareTest);
    assertEquals(bestUser.getPersonId(), compareTest);
  }

  @Test
  public void findPersonIdFail() throws DataAccessException {
    assertNull(userDAO.findPersonId(bestUser.getUsername()));
  }

  @Test
  public void deleteTest() throws DataAccessException {
    userDAO.insert(bestUser);
    userDAO.delete();
    assertNull(userDAO.find(bestUser.getUsername()));
  }

}