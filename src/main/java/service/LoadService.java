package service;

import DAO.*;
import service.request.LoadRequest;
import service.result.ClearResult;
import service.result.LoadResult;

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

      } catch (DataAccessException | NullPointerException e) {
        db.closeConnection(false);
        return new LoadResult(e.getMessage(), false);
      }
    } catch(DataAccessException e) {
      return new LoadResult(e.getMessage(), false);
    }

    String message = "Successfully added " + request.getUsers().length + " users, " + request.getPersons().length +
            " persons, and " + request.getEvents().length + " events to the database.";

    return new LoadResult(message, true);
  }
}
