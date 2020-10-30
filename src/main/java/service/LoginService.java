package service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import service.request.LoginRequest;
import service.result.LoginResult;

import java.sql.Connection;

public class LoginService {
  public LoginService() {
  }

  public LoginResult login(LoginRequest request) {

    Database db = new Database();
    try {
      Connection conn = db.openConnection();
      db.clearTables();

      AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);

      // TODO: Figure out how to generate an AuthToken

      /*
      Request Body:
{
	"userName": "susan",		// Non-empty string
	"password": "mysecret"	// Non-empty string
}

Success Response Body:
{
	"authToken": "cf7a368f",	// Non-empty auth token string
	"userName": "susan",		// User name passed in with request
	"personID": "39f9fe46"	// Non-empty string containing the Person ID of the
//    user’s generated Person object
“success”:”true”		// Boolean identifier
}
       */

      db.closeConnection(true);
    } catch (DataAccessException e) {
      return new LoginResult(e.getMessage(), false);
    }

    return new LoginResult(true, "", "", "");
  }
}
