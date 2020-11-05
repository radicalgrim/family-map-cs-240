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
  private AuthToken sampleToken;
  private AuthTokenDAO authTokenDAO;
  private final String uuid = UUID.randomUUID().toString();

  @BeforeEach
  void setUp() throws DataAccessException {
    db = new Database();
    sampleToken = new AuthToken(uuid, "radicalGrim");
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
    authTokenDAO.insert(sampleToken);
    AuthToken compareTest = authTokenDAO.find(sampleToken.getAuthToken());
    assertNotNull(compareTest);
    assertEquals(sampleToken, compareTest);
  }

  @Test
  void insertFail() throws DataAccessException {
    authTokenDAO.insert(sampleToken);
    assertThrows(DataAccessException.class, ()-> authTokenDAO.insert(sampleToken));
  }

  @Test
  void findPass() throws DataAccessException {
    authTokenDAO.insert(sampleToken);
    AuthToken compareTest = authTokenDAO.find(sampleToken.getAuthToken());
    assertNotNull(compareTest);
    assertEquals(sampleToken, compareTest);
  }

  @Test
  void findFail() throws DataAccessException {
    assertNull(authTokenDAO.find(sampleToken.getAuthToken()));
  }

  @Test
  void deleteTest() throws DataAccessException {
    authTokenDAO.insert(sampleToken);
    authTokenDAO.delete();
    assertNull(authTokenDAO.find(sampleToken.getAuthToken()));
  }
}