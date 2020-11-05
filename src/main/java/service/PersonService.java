package service;

import DAO.*;
import model.AuthToken;
import model.Person;
import service.result.PersonResult;

import java.sql.Connection;

public class PersonService {
  PersonResult result;

  public PersonService() {
  }

  public PersonResult person(String personId, String authTokenString) {
    Database db = new Database();
    try {
      try {
        Connection conn = db.openConnection();
        String username = findCurrentUser(conn, authTokenString);
        if (personId == null) {
          result = retrieveAllPersons(conn, username);
        }
        else {
          result = retrievePerson(conn, personId, username);
        }
        db.closeConnection(true);

      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new PersonResult("Error: Internal server error", false);
      } catch (Exception e) {
        db.closeConnection(false);
        return new PersonResult(e.getMessage(), false);
      }
    } catch(DataAccessException e) {
      return new PersonResult("Error: Internal server error", false);
    }

    return result;
  }

  public String findCurrentUser(Connection conn, String authTokenString) throws Exception {
    AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
    AuthToken authToken = authTokenDAO.find(authTokenString);
    if (authToken == null) {
      throw new Exception("Error: Invalid auth token");
    }
    return authToken.getUsername();
  }

  private PersonResult retrieveAllPersons(Connection conn, String username) throws Exception {
    PersonDAO personDAO = new PersonDAO(conn);
    Person[] data = personDAO.findUserPersons(username);

    if (data == null) {
      throw new Exception("Error: No persons found for given user");
    }

    return new PersonResult(data, true);
  }

  private PersonResult retrievePerson(Connection conn, String personId, String username) throws Exception {
    PersonDAO personDAO = new PersonDAO(conn);
    Person person = personDAO.find(personId);
    if (person == null) {
      throw new Exception("Error: No persons found for given user");
    }
    if (!person.getUsername().equals(username)) {
      throw new Exception("Error: Invalid auth token");
    }

    return new PersonResult(person.getUsername(), person.getId(), person.getFirstName(), person.getLastName(),
            person.getGender(), person.getFatherId(), person.getMotherId(), person.getSpouseId(), true);
  }

}
