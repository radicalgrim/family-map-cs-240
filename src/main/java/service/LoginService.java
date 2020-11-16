package service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import result.LoginResult;

import java.sql.Connection;
import java.util.UUID;

public class LoginService {
  LoginResult result;
  User user;

  public LoginService() {
  }

  public LoginResult login(LoginRequest request) {
    String uuid = UUID.randomUUID().toString();
    Database db = new Database();
    try {
      try {
        Connection conn = db.openConnection();
        UserDAO userDAO = new UserDAO(conn);
        user = userDAO.find(request.getUsername());
        if (user == null) {
          db.closeConnection(false);
          return new LoginResult("Error: Invalid username", false);
        }
        if (request.getPassword().equals(user.getPassword())) {
          AuthToken token = new AuthToken(uuid, request.getUsername());
          AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
          authTokenDAO.insert(token);
        }
        else {
          db.closeConnection(false);
          return new LoginResult("Error: Invalid password", false);
        }
        db.closeConnection(true);

      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new LoginResult("Error: Internal server error", false);
      }

    } catch (DataAccessException e) {
      return new LoginResult("Error: Internal server error", false);
    }

    return new LoginResult(uuid, user.getUsername(), user.getPersonId(), true);
  }
}
