package service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import model.AuthToken;
import model.User;
import service.request.LoginRequest;
import service.result.LoginResult;

import java.sql.Connection;
import java.util.UUID;

public class LoginService {
  public LoginService() {
  }

  public LoginResult login(LoginRequest request) {
    String uuid = UUID.randomUUID().toString();
    User user = new User();

    try {
      Database db = new Database();
      try {

        Connection conn = db.openConnection();
        db.clearTables();
        // Check the user's password against their username
        UserDAO userDAO = new UserDAO(conn);
        user = userDAO.find(request.getUsername());
        if (request.getPassword().equals(user.getPassword())) {
          // If it succeeds then create an AuthToken
          AuthToken token = new AuthToken(uuid, request.getUsername());
          // Associate the token with the username
          AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
          authTokenDAO.insert(token);
          // TODO: Make sure the same user can have multiple authTokens
        } else {
          db.closeConnection(false);
          return new LoginResult("Invalid password", false);
        }
        db.closeConnection(true);

      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new LoginResult(e.getMessage(), false);
      }

    } catch (DataAccessException e) {
      e.printStackTrace();
    }
    // Return the Token, Username, Password, and Success
    return new LoginResult(uuid, user.getUsername(), user.getPersonId(), true);
  }
}

/*
When a user logs in, the login request sent from the client to the server must include
the user’s username and password.  If login succeeds, your server should generate a
unique “authorization token” string for the user, and return it to the client.
Subsequent requests sent from the client to your server should include the auth token
so your server can determine which user is making the request.  This allows non-login
requests to be made without having to include the user’s credentials, thus reducing
the likelihood that a hacker will intercept them.  For this scheme to work, your server
should store auth tokens in its database and record which user each token belongs to.
Also, to protect against the possibility that a hacker might intercept a user’s auth
token, it is important that each new login request generate and return a unique auth
token.  It should also be possible for the same user to be logged in from multiple
clients at the same time, which means that the same user could have multiple active
auth tokens simultaneously. An auth token should be included in the HTTP “Authorization”
request header for all requests that require an auth token.

 */
