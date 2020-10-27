package DAO;

import model.Person;

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




/load

Clear all data from the database
Load the posted user, person, and event data into the database

HTTP Method: POST
Auth Token Required: No

Request Body:
The objects contained in the “persons” and “events” arrays should be added to the server’s database
The objects in the “users” array have the same format as the /user/register API with the addition of the personID
The objects in the “persons” array have the same format as those returned by the /person/[personID] API
The objects in the “events” array have the same format as those returned by the /event/[eventID] API

{
    “users”: [ Array of User objects ],
    “persons”: [ Array of Person objects ],
    “events”: [ Array of Event objects  ]
}

Errors:
Invalid request data (missing values, invalid values, etc.)
Internal server error

Success Response Body:
{
    “message”: “Successfully added X users, Y persons, and Z events to the database.”
    “success”:”true”		// Boolean identifier
}

Error Response Body:
{
    “message”: “Description of the error”
    “success”:”false”		// Boolean identifier
}




/person/[personID]

URL Path Example: /person/7255e93e

Return the single Person object with the specified ID

HTTP Method: GET
Auth Token Required: Yes

Request Body: None

Errors:
Invalid auth token
Invalid personID parameter
Requested person does not belong to this user
Internal server error

Success Response Body:
{
    "associatedUsername": "susan",	  // Name of user account this person belongs to
    "personID": "7255e93e",	          // Person’s unique ID
    "firstName": "Stuart",		      // Person’s first name
    "lastName": "Klocke",		      // Person’s last name
    "gender": "m",			          // Person’s gender (“m” or “f”)
    “fatherID”: “7255e93e”		      // ID of person’s father [OPTIONAL, can be missing]
    “motherID”: “d3gz214j”	          // ID of person’s mother [OPTIONAL, can be missing]
    "spouseID":"f42126c8"	          // ID of person’s spouse [OPTIONAL, can be missing]
    “success”:”true”		          // Boolean identifier
}

Error Response Body:
{
    “message”: “Description of the error”
    “success”:”false”		          // Boolean identifier
}




/person

Return ALL family members of the current user
The current user is determined from the provided auth token

HTTP Method: GET
Auth Token Required: Yes

Request Body: None

Errors:
Invalid auth token
Internal server error

Success Response Body:
The response body returns a JSON object with a “data” attribute that contains an array of Person objects
Each Person object has the same format as described in previous section on the /person/[personID] API

{
    "data": [  Array of Person objects  ]
    “success”:”true”		// Boolean identifier
}

Error Response Body:
{
    “message”: “Description of the error”
    “success”:”false”		// Boolean identifier
}

 */

public class PersonDAO {
  private final Connection conn;

  public PersonDAO(Connection conn) {
    this.conn=conn;
  }

  public void insert(Person person) throws DAO.DataAccessException {
    String sql = "INSERT INTO Person (Id, Username, First_Name, Last_Name, Gender, Father_Id, Mother_Id, Spouse_Id) " +
            "VALUES(?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, person.getId());
      stmt.setString(2, person.getUsername());
      stmt.setString(3, person.getFirstName());
      stmt.setString(4, person.getLastName());
      stmt.setString(5, person.getGender());
      stmt.setString(6, person.getFatherId());
      stmt.setString(7, person.getMotherId());
      stmt.setString(8, person.getSpouseId());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  public Person find(String personId) throws DAO.DataAccessException {
    Person person;
    ResultSet rs = null;
    String sql = "SELECT * FROM Person WHERE Id = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, personId);
      rs = stmt.executeQuery();
      if (rs.next()) {
        person = new Person(rs.getString("Id"), rs.getString("Username"),
                rs.getString("First_Name"), rs.getString("Last_Name"),
                rs.getString("Gender"), rs.getString("Father_Id"),
                rs.getString("Mother_Id"), rs.getString("Spouse_Id"));
        return person;
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
    String sql = "DELETE FROM Person";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing table");
    }
  }
}
