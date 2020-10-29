package service;

import DAO.DataAccessException;
import DAO.Database;
import service.result.ClearResult;

public class ClearService {
  public ClearService() {
  }

  public ClearResult clear() {

    // TODO: Figure out which Database object I should be modifying
    Database db = new Database();

    try {


      db.clearTables();


    } catch (DataAccessException e) {
      e.printStackTrace();
    }


    return new ClearResult();
  }

}
