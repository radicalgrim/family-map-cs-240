package service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDAO;
import model.AuthToken;
import model.Event;
import service.result.EventResult;

import java.sql.Connection;

public class EventService {
  public EventService() {
  }

  public EventResult event(String eventId, String authTokenString) {

    Database db = new Database();
    EventResult result;

    try {
      try {

        Connection conn = db.openConnection();
        String username = findCurrentUser(conn, authTokenString);
        if (eventId == null) {
          result = retrieveAllEvents(conn, username);
        }
        else {
          result = retrieveEvent(conn, eventId, username);
        }
        db.closeConnection(true);

      } catch (Exception e) {
        db.closeConnection(false);
        return new EventResult(e.getMessage(), false);
      }
    } catch(DataAccessException e) {
      return new EventResult(e.getMessage(), false);
    }

    return result;
  }

  public String findCurrentUser(Connection conn, String authTokenString) throws Exception {
    AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
    AuthToken authToken = authTokenDAO.find(authTokenString);
    if (authToken == null) {
      throw new Exception("Invalid auth token");
    }
    return authToken.getUsername();
  }

  private EventResult retrieveAllEvents(Connection conn, String username) throws Exception {
    EventDAO eventDAO = new EventDAO(conn);
    Event[] data = eventDAO.findUserEvents(username);

    if (data == null) {
      throw new Exception("No events found for given user");
    }

    return new EventResult(data, true);
  }

  private EventResult retrieveEvent(Connection conn, String eventId, String username) throws Exception {
    EventDAO eventDAO = new EventDAO(conn);
    Event event = eventDAO.find(eventId);
    if (event == null) {
      throw new Exception("No events found for given user");
    }
    if (!event.getUsername().equals(username)) {
      throw new Exception("Invalid auth token");
    }
    return new EventResult(event.getUsername(), event.getEventID(), event.getPersonID(), event.getLatitude(),
            event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear(),
            true);
  }

}