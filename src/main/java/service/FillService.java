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

public class FillService {
  private LocationList locationList;
  private FemaleNames femaleNames;
  private MaleNames maleNames;
  private Surnames surnames;
  private int gen;
  private int personCount;
  private int eventCount;

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

    return new FillResult("Successfully added " + personCount + " persons, and " + eventCount + " events to the database.", true);
  }

  private void deleteExistingPersonData(Connection conn, String username) throws DataAccessException {
    EventDAO eventDAO = new EventDAO(conn);
    eventDAO.deleteByUsername(username);
    PersonDAO personDAO = new PersonDAO(conn);
    personDAO.deleteByUsername(username);
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

  private Location randomLocation() {
    int random = (int) Math.floor(Math.random() * Math.floor(locationList.getData().length));
    return locationList.getData()[random];
  }

  private String randomFemaleName() {
    int random = (int) Math.floor(Math.random() * Math.floor(femaleNames.getData().length));
    return femaleNames.getData()[random];
  }

  private String randomMaleName() {
    int random = (int) Math.floor(Math.random() * Math.floor(maleNames.getData().length));
    return maleNames.getData()[random];
  }

  private String randomSurname() {
    int random = (int) Math.floor(Math.random() * Math.floor(surnames.getData().length));
    return surnames.getData()[random];
  }

  private void generateAncestors(Connection conn, User user, int gen) throws DataAccessException {
    Person self = generateUserInfo(conn, user);
    if (gen > 0) {
      generateParents(conn, self, gen - 1);
    }
  }

  private Person generateUserInfo(Connection conn, User user) throws DataAccessException {
    Person self = generateUserPerson(conn, user);
    generateUserBirthEvent(conn, user);
    return self;
  }

  private Person generateUserPerson(Connection conn, User user) throws DataAccessException {
    Person self = new Person(user.getPersonId(), user.getUsername(), user.getFirstName(), user.getLastName(),
            user.getGender(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
    PersonDAO personDAO = new PersonDAO(conn);
    personDAO.insert(self);
    personCount++;
    return self;
  }

  private void generateUserBirthEvent(Connection conn, User user) throws DataAccessException {
    Location location = randomLocation();
    String eventType = "Birth";
    String eventId = user.getPersonId() + "_" + eventType;
    Event event = new Event(eventId, user.getUsername(), user.getPersonId(), location.getLatitude(),
            location.getLongitude(), location.getCountry(), location.getCity(), eventType, 1993);
    EventDAO eventDAO = new EventDAO(conn);
    eventDAO.insert(event);
    eventCount++;
  }

  private void generateParents(Connection conn, Person child, int gen) throws DataAccessException {
    Person father = generateParentPerson(conn, child, "m");
    Person mother = generateParentPerson(conn, child, "f");
    generateParentEvents(conn, father, mother, child);
    if (gen > 0) {
      generateParents(conn, father, gen - 1);
      generateParents(conn, mother, gen - 1);
    }
  }

  private Person generateParentPerson(Connection conn, Person child, String gender) throws DataAccessException {
    Person parent;
    if (gender.equals("m")) {
      parent = new Person(child.getFatherId(), child.getUsername(), randomMaleName(), child.getLastName(),
              gender, UUID.randomUUID().toString(), UUID.randomUUID().toString(), child.getMotherId());
    }
    else {
      parent = new Person(child.getMotherId(), child.getUsername(), randomFemaleName(), randomSurname(),
              gender, UUID.randomUUID().toString(), UUID.randomUUID().toString(), child.getFatherId());
    }
    PersonDAO personDAO = new PersonDAO(conn);
    personDAO.insert(parent);
    personCount++;
    return parent;
  }

  // TODO: Allow for user generated events
  private void generateParentEvents(Connection conn, Person father, Person mother, Person child) throws DataAccessException {
    generateBirthEvent(conn, father, child);
    generateBirthEvent(conn, mother, child);
    generateDeathEvent(conn, father, child);
    generateDeathEvent(conn, mother, child);
    generateMarriageEvents(conn, father, mother);
  }

  private void generateBirthEvent(Connection conn, Person self, Person child) throws DataAccessException {
    /*
    - Parent birth date 13 years or more than child birth date
    - Females no children after 50
    - Birth event must be first
     */
    EventDAO eventDAO = new EventDAO(conn);
    Event birthEventChild = eventDAO.find(child.getId() + "_Birth");
    int upper = birthEventChild.getYear() - 13;
    int lower = birthEventChild.getYear() - 51;
    int birthYear = (int) (Math.random() * (upper - lower)) + lower;

    Location location = randomLocation();
    String eventType = "Birth";
    String eventId = self.getId() + "_" + eventType;

    Event birthEventSelf = new Event(eventId, self.getUsername(), self.getId(), location.getLatitude(),
            location.getLongitude(), location.getCountry(), location.getCity(), eventType, birthYear);
    eventDAO.insert(birthEventSelf);
    eventCount++;
  }

  private void generateDeathEvent(Connection conn, Person self, Person child) throws DataAccessException {
    /*
    - Parent death date must not be before child's birth date
    - Death event must be last
    - Age <= 120
    */
    EventDAO eventDAO = new EventDAO(conn);
    Event birthEventSelf = eventDAO.find(self.getId() + "_Birth");
    Event birthEventChild = eventDAO.find(child.getId() + "_Birth");
    int lower = birthEventChild.getYear();
    int upper = birthEventSelf.getYear() + 121;
    int deathYear = (int) (Math.random() * (upper - lower)) + lower;

    Location location = randomLocation();
    String eventType = "Death";
    String eventId = self.getId() + "_" + eventType;

    Event deathEvent = new Event(eventId, self.getUsername(), self.getId(), location.getLatitude(),
            location.getLongitude(), location.getCountry(), location.getCity(), eventType, deathYear);
    eventDAO.insert(deathEvent);
    eventCount++;
  }

  private void generateMarriageEvents(Connection conn, Person self, Person spouse) throws DataAccessException {
    /*
    - Marriage date >= 18
    - Spouse marriage year and location must match each other
    */
    EventDAO eventDAO = new EventDAO(conn);

    Event birthEventSelf = eventDAO.find(self.getId() + "_Birth");
    Event birthEventSpouse = eventDAO.find(spouse.getId() + "_Birth");
    Event deathEventSelf = eventDAO.find(self.getId() + "_Death");
    Event deathEventSpouse = eventDAO.find(spouse.getId() + "_Death");

    int upper;
    int lower;
    if (birthEventSelf.getYear() > birthEventSpouse.getYear()) {
      lower = birthEventSelf.getYear() + 18;
    }
    else {
      lower = birthEventSpouse.getYear() + 18;
    }
    if (deathEventSelf.getYear() < deathEventSpouse.getYear()) {
      upper = birthEventSelf.getYear();
    }
    else {
      upper = birthEventSpouse.getYear();
    }
    int marriageYear = (int) (Math.random() * (upper - lower)) + lower;

    String eventType = "Marriage";
    String eventIdSelf = self.getId() + "_" + eventType;
    String eventIdSpouse = spouse.getId() + "_" + eventType;

    Location location = randomLocation();
    Float latitude = location.getLatitude();
    Float longitude = location.getLongitude();
    String country = location.getCountry();
    String city = location.getCity();

    Event marriageEventSelf = new Event(eventIdSelf, self.getUsername(), self.getId(), latitude,
            longitude, country, city, eventType, marriageYear);
    Event marriageEventSpouse = new Event(eventIdSpouse, spouse.getUsername(), spouse.getId(), latitude,
            longitude, country, city, eventType, marriageYear);

    eventDAO.insert(marriageEventSelf);
    eventDAO.insert(marriageEventSpouse);
    eventCount += 2;
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