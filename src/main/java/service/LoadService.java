package service;

import DAO.*;
import request.LoadRequest;
import result.LoadResult;

import java.sql.Connection;

public class LoadService {
  public LoadService() {
  }

  public LoadResult load(LoadRequest request) {
    Database db = new Database();
    try {
      try {
        Connection conn = db.openConnection();
        db.clearTables();
        UserDAO userDAO = new UserDAO(conn);
        for (int i = 0; i < request.getUsers().length; i++) {
          userDAO.insert(request.getUsers()[i]);
        }
        PersonDAO personDAO = new PersonDAO(conn);
        for (int i = 0; i < request.getPersons().length; i++) {
          personDAO.insert(request.getPersons()[i]);
        }
        EventDAO eventDAO = new EventDAO(conn);
        for (int i = 0; i < request.getEvents().length; i++) {
          eventDAO.insert(request.getEvents()[i]);
        }

        db.closeConnection(true);
      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new LoadResult("Error: Internal server error", false);
      } catch (NullPointerException e) {
        db.closeConnection(false);
        return new LoadResult("Error: One of the fields was empty", false);
      }
    } catch(DataAccessException e) {
      return new LoadResult("Error: Internal server error", false);
    }

    String message = "Successfully added " + request.getUsers().length + " users, " + request.getPersons().length +
            " persons, and " + request.getEvents().length + " events to the database.";

    return new LoadResult(message, true);
  }
}
