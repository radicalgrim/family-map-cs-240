package service;

import DAO.DataAccessException;
import DAO.Database;
import service.result.ClearResult;

import java.sql.Connection;

public class ClearService {
  public ClearService() {
  }

  public ClearResult clear() {
    try {
      Database db = new Database();
      try {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new ClearResult(e.getMessage(), false);
      }
    } catch(DataAccessException e) {
      e.printStackTrace();
    }

    return new ClearResult("Clear succeeded.", true);
  }
}
