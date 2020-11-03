package service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import model.AuthToken;
import model.User;
import service.request.LoginRequest;
import service.request.RegisterRequest;
import service.result.FillResult;
import service.result.LoadResult;
import service.result.LoginResult;
import service.result.RegisterResult;

import java.sql.Connection;
import java.util.Objects;
import java.util.UUID;

public class RegisterService extends AncestorService {

  public RegisterService() {
  }

  public RegisterResult register(RegisterRequest request) {
    Database db = new Database();
    String uuid = UUID.randomUUID().toString();
    User user;

    try {
      try {
        Connection conn = db.openConnection();
        UserDAO userDAO = new UserDAO(conn);
        if (userDAO.find(request.getUsername()) != null) {
          db.closeConnection(false);
          return new RegisterResult("Username taken", false);
        }
        else {
          user = new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstName(),
                  request.getLastName(), request.getGender(), UUID.randomUUID().toString());
          userDAO.insert(user);

          gen = 4;
          personCount = 0;
          eventCount = 0;
          populateDummyData();
          generateAncestors(conn, user, gen);

          AuthToken token = new AuthToken(uuid, user.getUsername());
          AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
          authTokenDAO.insert(token);

          db.closeConnection(true);
        }

      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new RegisterResult(e.getMessage(), false);
      }
    } catch(DataAccessException e) {
      return new RegisterResult(e.getMessage(), false);
    }

    return new RegisterResult(uuid, user.getUsername(), user.getPersonId(), true);
  }
}