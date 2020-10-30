package service;

import DAO.DataAccessException;
import DAO.Database;
import service.result.ClearResult;

import java.sql.Connection;

public class ClearService {
  public ClearService() {
  }

  public ClearResult clear() {

    // TODO: Figure out which Database object I should be modifying

    Database db = new Database();
    try {
      db.openConnection(); // TODO: Do I need to save the connection?
      db.clearTables();
      db.closeConnection(true); // TODO: Does this need to be outside of this try/catch but inside another?
    } catch (DataAccessException e) {
      return new ClearResult(e.getMessage(), false);
    }

    return new ClearResult("Clear succeeded.", true);
  }
}
