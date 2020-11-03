package service;

import DAO.*;
import handler.JsonSerializer;
import model.Event;
import model.Person;
import model.User;
import service.dummyData.*;
import service.result.FillResult;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;
import java.util.UUID;

public class FillService extends AncestorService {

  public FillService() {
  }

  public FillResult fill(String username, String generations) {
    try {
      gen = Integer.parseInt(generations);
      if (gen < 0) {
        return new FillResult("Please enter a positive number of generations", false);
      } // TODO: Account for non numbers
      personCount = 0;
      eventCount = 0;
      Database db = new Database();
      try {
        Connection conn = db.openConnection();
        UserDAO userDAO = new UserDAO(conn);
        User user = userDAO.find(username);
        if (user == null) {
          db.closeConnection(false);
          return new FillResult("Invalid username", false);
        }

        deleteExistingPersonData(conn, user.getUsername());
        populateDummyData();
        generateAncestors(conn, user, gen);

        db.closeConnection(true);
      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new FillResult(e.getMessage(), false);
      }
    } catch(DataAccessException e) {
      return new FillResult(e.getMessage(), false);
    } catch (NumberFormatException e) {
      return new FillResult("Invalid input. Please enter a positive number of generations", false);
    }

    return new FillResult("Successfully added " + personCount + " persons and " + eventCount + " events to the database.", true);
  }

}