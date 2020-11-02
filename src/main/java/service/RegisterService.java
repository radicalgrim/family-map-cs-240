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

/*
/user/register
URL Path: /user/register
Description: Creates a new user account, generates 4 generations of ancestor data for the
new user, logs the user in, and returns an auth token.
HTTP Method: POST
Auth Token Required: No
Request Body:
{
	"userName": "susan",		// Non-empty string
	"password": "mysecret",	// Non-empty string
	"email": "susan@gmail.com",	// Non-empty string
	"firstName": "Susan",		// Non-empty string
	"lastName": "Ellis",		// Non-empty string
 "gender": "f"			// “f” or “m”
}
Errors: Request property missing or has invalid value, Username already taken by another user, Internal server error
Success Response Body:
{
	"authToken": "cf7a368f",	// Non-empty auth token string
	"userName": "susan",		// User name passed in with request
	"personID": "39f9fe46"		// Non-empty string containing the Person ID of the
			//  user’s generated Person object
“success”:”true”		// Boolean identifier
}
Error Response Body:
{
	“message”: “Description of the error”
“success”:”false”		// Boolean identifier
}

*/