package DAO;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
/user/register

Create a new user account
Generate 4 generations of ancestor data for the new user
Log the user in
Return an auth token

HTTP Method: POST
Auth Token Required: No

Request Body:
{
	"userName": "susan",		// Non-empty string
	"password": "mysecret",	    // Non-empty string
	"email": "susan@gmail.com",	// Non-empty string
	"firstName": "Susan",		// Non-empty string
	"lastName": "Ellis",		// Non-empty string
    "gender": "f"			    // “f” or “m”
}

Errors:
Request property missing or has invalid value
Username already taken by another user
Internal server error

Success Response Body:
{
	"authToken": "cf7a368f",	// Non-empty auth token string
	"userName": "susan",		// User name passed in with request
	"personID": "39f9fe46"		// Non-empty string with the PersonID of the user’s generated Person object
    “success”:”true”		    // Boolean identifier
}

Error Response Body:
{
	“message”: “Description of the error”
    “success”:”false”		    // Boolean identifier
}



/user/login

Log in the user
Return an auth token

HTTP Method: POST
Auth Token Required: No

Request Body:
{
	"userName": "susan",		// Non-empty string
	"password": "mysecret"	    // Non-empty string
}

Errors:
Request property missing or has invalid value
Internal server error

Success Response Body:
{
	"authToken": "cf7a368f",	// Non-empty auth token string
	"userName": "susan",		// User name passed in with request
	"personID": "39f9fe46"	    // Non-empty string with the PersonID of the user’s generated Person object
    “success”:”true”		    // Boolean identifier
}

Error Response Body:
{
	“message”: “Description of the error”
    “success”:”false”		    // Boolean identifier
}




/clear

Delete ALL data from the database
    user accounts
    auth tokens
    generated person and event data

HTTP Method: POST
Auth Token Required: No

Request Body: None

Errors:
Internal server error

Success Response Body:
{
	“message”: “Clear succeeded.”
    “success”:”true”		  // Boolean identifier
}

Error Response Body:
{
	“message”: “Description of the error”
    “success”:”false”		  // Boolean identifier
}




/fill/[username]/{generations}

URL Path Example: /fill/susan/3

Populate the server's database with generated data for the specified user name
    The required "username" parameter must be a user already registered with the server
    If there is any data in the database already associated with the given user name, it is deleted
    The optional “generations” parameter must be a non-negative integer
        (the default is 4, which results in 31 new persons each with associated events)

HTTP Method: POST
Auth Token Required: No

Request Body: None

Errors:
Invalid username or generations parameter
Internal server error

Success Response Body:
{
	“message”: “Successfully added X persons and Y events to the database.”
    “success”:”true”		  // Boolean identifier
}

Error Response Body:
{
	“message”: “Description of the error”
    “success”:”false”		  // Boolean identifier
}

 */

public class UserDAO {
  private final Connection conn;

  /*
  Create a new user account
  Generate 4 generations of ancestor data for the new user
  Log the user in
  Return an auth token
  */

  public UserDAO(Connection conn) {
    this.conn = conn;
  }

  public void insert(User user) throws DAO.DataAccessException {
    String sql = "INSERT INTO User (Username, Password, Email, First_Name, Last_Name, Gender, Person_Id) " +
            "VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getEmail());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.setString(6, user.getGender());
      stmt.setString(7, user.getPersonId());

      stmt.executeUpdate();

    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  public User find(String username) throws DAO.DataAccessException {
    User user;
    ResultSet rs = null;
    String sql = "SELECT * FROM User WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("Username"), rs.getString("Password"),
                rs.getString("Email"), rs.getString("First_Name"),
                rs.getString("Last_Name"), rs.getString("Gender"),
                rs.getString("Person_Id"));
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding event");
    } finally {
      if(rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
    return null;
  }

  public void delete() throws DataAccessException {
    String sql = "DELETE FROM User";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing table");
    }
  }

}
