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
      try {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new ClearResult(e.getMessage(), false);
      }
    } catch(DataAccessException e) {
      return new ClearResult(e.getMessage(), false);
    }

    return new ClearResult("Clear succeeded.", true);
  }
}
