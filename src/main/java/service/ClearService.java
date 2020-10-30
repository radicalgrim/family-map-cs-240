package service;

import DAO.DataAccessException;
import DAO.Database;
import service.result.ClearResult;

public class ClearService {
  public ClearService() {
  }

  public ClearResult clear() {

    Database db = new Database();
    try {
      db.openConnection();
      db.clearTables();
      db.closeConnection(true); // Does this need to be outside of this try/catch but inside another?
    } catch (DataAccessException e) {
      return new ClearResult(e.getMessage(), false);
    }

    return new ClearResult("Clear succeeded.", true);
  }
}
