package service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import model.AuthToken;
import service.request.LoginRequest;
import service.result.LoginResult;

import java.sql.Connection;
import java.util.UUID;

public class LoginService {
  public LoginService() {
  }

  public LoginResult login(LoginRequest request) {

    Database db = new Database();
    String uuid = UUID.randomUUID().toString();
    String personId;

    try {
      Connection conn = db.openConnection();
      db.clearTables();

      // TODO: Still encountering error inserting into database
      // Generate an AuthToken and insert it in the database
      AuthToken token = new AuthToken(uuid, request.getUsername());
      AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
      authTokenDAO.insert(token);

      UserDAO userDAO = new UserDAO(conn);
      personId = userDAO.findPersonId(request.getUsername());

      db.closeConnection(true);
    } catch (DataAccessException e) {
      return new LoginResult(e.getMessage(), false);
    }

    return new LoginResult(true, uuid, request.getUsername(), personId);
  }
}
