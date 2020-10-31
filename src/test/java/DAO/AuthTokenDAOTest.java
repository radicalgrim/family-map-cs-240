package DAO;

import model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDAOTest {
  private Database db;
  private AuthToken bestToken;
  private AuthTokenDAO authTokenDAO;
  private final String uuid = UUID.randomUUID().toString();

  @BeforeEach
  void setUp() throws DataAccessException {
    db = new Database();
    bestToken = new AuthToken(uuid, "radicalGrim");
    Connection conn = db.getConnection();
    db.clearTables();
    authTokenDAO = new AuthTokenDAO(conn);
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }

  @Test
  void insertPass() throws DataAccessException {
    authTokenDAO.insert(bestToken);
    AuthToken compareTest = authTokenDAO.find(bestToken.getUsername());
    assertNotNull(compareTest);
    assertEquals(bestToken, compareTest);
  }

  @Test
  void insertFail() throws DataAccessException {
    authTokenDAO.insert(bestToken);
    assertThrows(DataAccessException.class, ()-> authTokenDAO.insert(bestToken));
  }

  @Test
  void findPass() throws DataAccessException {
    authTokenDAO.insert(bestToken);
    AuthToken compareTest = authTokenDAO.find(bestToken.getUsername());
    assertNotNull(compareTest);
    assertEquals(bestToken, compareTest);
  }

  @Test
  void findFail() throws DataAccessException {
    assertNull(authTokenDAO.find(bestToken.getUsername()));
  }

  @Test
  void deleteTest() throws DataAccessException {
    authTokenDAO.insert(bestToken);
    authTokenDAO.delete();
    assertNull(authTokenDAO.find(bestToken.getUsername()));
  }
}