package service;

import DAO.DataAccessException;
import DAO.EventDAO;
import DAO.PersonDAO;
import handler.JsonSerializer;
import model.Event;
import model.Person;
import model.User;
import service.dummyData.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;
import java.util.UUID;

public abstract class AncestorService {
  public LocationList locationList;
  public FemaleNames femaleNames;
  public MaleNames maleNames;
  public Surnames surnames;
  public int gen;
  public int personCount;
  public int eventCount;

  public void deleteExistingPersonData(Connection conn, String username) throws DataAccessException {
    EventDAO eventDAO = new EventDAO(conn);
    eventDAO.deleteByUsername(username);
    PersonDAO personDAO = new PersonDAO(conn);
    personDAO.deleteByUsername(username);
  }

  public void populateDummyData() {
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

  public String readFile(String pathname) throws IOException {
    File file = new File(pathname);
    StringBuilder fileContents = new StringBuilder((int)file.length());
    try (Scanner scanner = new Scanner(file)) {
      while(scanner.hasNextLine()) {
        fileContents.append(scanner.nextLine()).append(System.lineSeparator());
      }
      return fileContents.toString();
    }
  }

  public Location randomLocation() {
    int random = (int) Math.floor(Math.random() * Math.floor(locationList.getData().length));
    return locationList.getData()[random];
  }

  public String randomFemaleName() {
    int random = (int) Math.floor(Math.random() * Math.floor(femaleNames.getData().length));
    return femaleNames.getData()[random];
  }

  public String randomMaleName() {
    int random = (int) Math.floor(Math.random() * Math.floor(maleNames.getData().length));
    return maleNames.getData()[random];
  }

  public String randomSurname() {
    int random = (int) Math.floor(Math.random() * Math.floor(surnames.getData().length));
    return surnames.getData()[random];
  }

  public void generateAncestors(Connection conn, User user, int gen) throws DataAccessException {
    Person self = generateUserInfo(conn, user);
    if (gen > 0) {
      generateParents(conn, self, gen - 1);
    }
  }

  public Person generateUserInfo(Connection conn, User user) throws DataAccessException {
    Person self = generateUserPerson(conn, user);
    generateUserBirthEvent(conn, user);
    return self;
  }

  public Person generateUserPerson(Connection conn, User user) throws DataAccessException {
    Person self = new Person(user.getPersonId(), user.getUsername(), user.getFirstName(), user.getLastName(),
            user.getGender(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
    PersonDAO personDAO = new PersonDAO(conn);
    personDAO.insert(self);
    personCount++;
    return self;
  }

  public void generateUserBirthEvent(Connection conn, User user) throws DataAccessException {
    Location location = randomLocation();
    String eventType = "Birth";
    String eventId = user.getPersonId() + "_" + eventType;
    Event event = new Event(eventId, user.getUsername(), user.getPersonId(), location.getLatitude(),
            location.getLongitude(), location.getCountry(), location.getCity(), eventType, 1993);
    EventDAO eventDAO = new EventDAO(conn);
    eventDAO.insert(event);
    eventCount++;
  }

  public void generateParents(Connection conn, Person child, int gen) throws DataAccessException {
    Person father = generateParentPerson(conn, child, "m");
    Person mother = generateParentPerson(conn, child, "f");
    generateParentEvents(conn, father, mother, child);
    if (gen > 0) {
      generateParents(conn, father, gen - 1);
      generateParents(conn, mother, gen - 1);
    }
  }

  public Person generateParentPerson(Connection conn, Person child, String gender) throws DataAccessException {
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
  public void generateParentEvents(Connection conn, Person father, Person mother, Person child) throws DataAccessException {
    generateBirthEvent(conn, father, child);
    generateBirthEvent(conn, mother, child);
    generateDeathEvent(conn, father, child);
    generateDeathEvent(conn, mother, child);
    generateMarriageEvents(conn, father, mother);
  }

  public void generateBirthEvent(Connection conn, Person self, Person child) throws DataAccessException {
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

  public void generateDeathEvent(Connection conn, Person self, Person child) throws DataAccessException {
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

  public void generateMarriageEvents(Connection conn, Person self, Person spouse) throws DataAccessException {
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
