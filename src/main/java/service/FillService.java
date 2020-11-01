package service;

import DAO.*;
import handler.JsonSerializer;
import model.Event;
import model.Person;
import model.User;
import service.dummyData.FemaleNames;
import service.dummyData.LocationList;
import service.dummyData.MaleNames;
import service.dummyData.Surnames;
import service.result.FillResult;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;
import java.util.UUID;

public class FillService {
  LocationList locationList;
  FemaleNames femaleNames;
  MaleNames maleNames;
  Surnames surnames;

  public FillService() {
  }

  public FillResult fill(String username, int generations) {
    if (generations < 0) {
      return new FillResult("Please enter a positive number of generations", false);
    }

    populateDummyData();


    Database db = new Database();
    try {
      try {
        Connection conn = db.openConnection();
        UserDAO userDAO = new UserDAO(conn);
        User user = userDAO.find(username);
        if (user == null) {
          db.closeConnection(false);
          return new FillResult("Invalid username", false);
        }
        // TODO: Delete existing person data for given user

        generateUserPerson(user, conn);
        if (generations > 0) {
          generateAncestors(generations, conn);
        }

        db.closeConnection(true);
      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new FillResult(e.getMessage(), false);
      }
    } catch(DataAccessException e) {
      return new FillResult(e.getMessage(), false);
    }


    // TODO: Implement
    int numPersons = 0;
    int numEvents = 0;
    String message = "Successfully added " + numPersons + " persons, and " + numEvents + " events to the database.";

    return new FillResult(message, true);
  }

  private void populateDummyData() {
    try {
      String content = readFile("json/locations.json");
      locationList = JsonSerializer.deserialize(content, LocationList.class);
      content = readFile("json/fnames.json");
      femaleNames = JsonSerializer.deserialize(content, FemaleNames.class);
      content = readFile("json/mnames.json");
      maleNames = JsonSerializer.deserialize(content, MaleNames.class);
      content = readFile("json/snames.json");
      surnames = JsonSerializer.deserialize(content, Surnames.class);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String readFile(String pathname) throws IOException {
    File file = new File(pathname);
    StringBuilder fileContents = new StringBuilder((int)file.length());
    try (Scanner scanner = new Scanner(file)) {
      while(scanner.hasNextLine()) {
        fileContents.append(scanner.nextLine()).append(System.lineSeparator());
      }
      return fileContents.toString();
    }
  }

  private Person generateUserPerson(User user, Connection conn) throws DataAccessException {
    Person person = new Person(user.getPersonId(), user.getUsername(), user.getFirstName(), user.getLastName(),
            user.getGender(), UUID.randomUUID().toString(), UUID.randomUUID().toString()); // TODO: Change this?
    PersonDAO personDAO = new PersonDAO(conn);
    personDAO.insert(person);

    Event event = new Event(UUID.randomUUID().toString(), user.getUsername(),user.getPersonId(), 3.2f,
            3.2f, "America", "Provo", "Birth", 1993);
    EventDAO eventDAO = new EventDAO(conn);
    eventDAO.insert(event);

    return person;
  }

  private void generateAncestors(int generations, Connection conn) throws DataAccessException {

  }
}

/*
- At least three events: Birth, Marriage, Death (except user)
- Parent birth date 13 years or more than child birth date
- Marriage date >= 18
- Parent death date must not be before child's birth date
- Females no children after 50
- Birth event must be first, death event must be last
- Age <= 120
- Spouse marriage year and location must match each other
- Allow for user generated event types

Your server should generate and store in its database family history information for
each user.  This data should include two types of objects: Persons and Events.  A user’s
family history data should include at least one Person object, which represents the user
him or herself.  Beyond the user’s own Person object, there can be zero or more additional
generations of data.  When your server is asked to generate family history data for a
user, it will be told how many generations of ancestor data to create (0 generations =
the user, 1 generation = the user + parents, 2 generations = the user + parents +
grandparents, etc.).  Based on the requested number of generations, you should fill out
the user’s family tree with generated Person and Event data.

For each Person you should generate a set of Event objects that describe important events
from the person’s life.  The types of events should be somewhat realistic and meet the
following criteria: each person, excluding the user, must have at least three events
(birth, marriage, and death) and the user needs to at least have a birth event. This does
not mean that a person can ONLY have these events, but they must have AT LEAST these
events. Parents should be born a reasonable number of years before their children (at
least 13 years), get married at a reasonable age, and not die before their child is born.
Also, females should not give birth at over 50 years old. Birth events need be first, and
death events need to be last. No one should die at over 120 years old. Each person in a
couple has their own marriage event, but their two marriage events need to have the same
year and location. Event locations may be randomly selected, or you can try to make them
more realistic (e.g., many people live their lives in a relatively small geographical
area). Your code needs to account for any possible event type, even if you yourself do
not generate those events. For example you might only generate events for Birth, Marriage,
and Death. But you should be able to also handle event types such as Baptism, Christening,
or any other data that may be loaded into your server.
 */