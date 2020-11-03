package service;

import DAO.*;
import model.AuthToken;
import model.Person;
import service.result.PersonResult;

import java.sql.Connection;

public class PersonService {
  public PersonService() {
  }

  public PersonResult person(String personId, String authTokenString) {

    Database db = new Database();
    PersonResult result;

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

      } catch (Exception e) {
        db.closeConnection(false);
        return new PersonResult(e.getMessage(), false);
      }
    } catch(DataAccessException e) {
      return new PersonResult(e.getMessage(), false);
    }

    return result;
  }

  public String findCurrentUser(Connection conn, String authTokenString) throws Exception {
    AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
    AuthToken authToken = authTokenDAO.find(authTokenString);
    if (authToken == null) {
      throw new Exception("Invalid auth token");
    }
    return authToken.getUsername();
  }

  private PersonResult retrieveAllPersons(Connection conn, String username) throws Exception {
    PersonDAO personDAO = new PersonDAO(conn);
    Person[] data = personDAO.findUserPersons(username);

    if (data == null) {
      throw new Exception("No persons found for given user");
    }

    return new PersonResult(data, true);
  }

  private PersonResult retrievePerson(Connection conn, String personId, String username) throws Exception {
    PersonDAO personDAO = new PersonDAO(conn);
    Person person = personDAO.find(personId);
    if (person == null) {
      throw new Exception("No persons found for given user");
    }
    if (!person.getUsername().equals(username)) {
      throw new Exception("Invalid auth token");
    }

    // TODO: Optional Id's?

    return new PersonResult(person.getUsername(), person.getId(), person.getFirstName(), person.getLastName(),
            person.getGender(), person.getFatherId(), person.getMotherId(), person.getSpouseId(), true);
  }

}
