package service;

import DAO.*;
import model.User;
import result.FillResult;

import java.sql.Connection;

public class FillService extends AncestorService {

  public FillService() {
  }

  public FillResult fill(String username, String generations) {
    Database db = new Database();
    personCount = 0;
    eventCount = 0;
    try {
      try {
        gen = Integer.parseInt(generations);
        if (gen < 0) {
          return new FillResult("Error: Please enter a positive number of generations", false);
        }
        Connection conn = db.openConnection();
        UserDAO userDAO = new UserDAO(conn);
        User user = userDAO.find(username);
        if (user == null) {
          db.closeConnection(false);
          return new FillResult("Error: Invalid username", false);
        }

        deleteExistingPersonData(conn, user.getUsername());
        populateDummyData();
        generateAncestors(conn, user, gen);

        db.closeConnection(true);
      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new FillResult("Error: Internal server error", false);
      }
    } catch(DataAccessException e) {
      return new FillResult("Error: Internal server error", false);
    } catch (NumberFormatException e) {
      return new FillResult("Error: Invalid input. Please enter a positive number of generations", false);
    }

    return new FillResult("Successfully added " + personCount + " persons and " + eventCount + " events to the database.", true);
  }

}