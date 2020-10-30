package service;

import DAO.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

  @BeforeEach
  void setUp() {

  }

  @AfterEach
  void tearDown() {
  }

  /*
      @BeforeEach
    public void setUp() throws DataAccessException {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        eventDAO= new EventDAO(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }
  */

  @Test
  void clear() {
  }
}

/*
2 test cases for each public method found in DAO and Service classes

One positive & one negative or 2 positive test cases

One positive test should test the main usage scenario, the other should test an alternative scenario

Most (if not all) test cases will involve multiple assertions.

Examples:
  try to log in with a bad password
  try to find a personID that does not exist
  pass invalid parameters

Make them unique
*/