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

  public EventResult event(String eventId, String tokenString) {

    Database db = new Database();
    EventResult result = null;

    try {
      try {
        Connection conn = db.openConnection();

        AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
        AuthToken authToken = authTokenDAO.find(tokenString);
        if (authToken == null) {
          db.closeConnection(false);
          return new EventResult("Invalid auth token", false);
        }

        if (eventId == null) {
          result = returnAllEvents(conn, authToken.getUsername()); // TODO: Fix this
        }
        else {
          result = returnSingleEvent(conn, eventId);
        }

        db.closeConnection(true);
      } catch (DataAccessException e) {
        db.closeConnection(false);
        return new EventResult(e.getMessage(), false);
      }
    } catch(DataAccessException e) {
      return new EventResult(e.getMessage(), false);
    }

    return result;
  }



  private EventResult returnAllEvents(Connection conn, String username) throws DataAccessException {
    EventDAO eventDAO = new EventDAO(conn);
    Event[] data = eventDAO.findUserEvents(username);
    return new EventResult(data, true);
  }

  private EventResult returnSingleEvent(Connection conn, String eventId) throws DataAccessException {
    EventDAO eventDAO = new EventDAO(conn);
    Event event = eventDAO.find(eventId);

    return new EventResult(event.getUsername(), event.getEventID(), event.getPersonID(), event.getLatitude(),
            event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear(),
            true);
  }

}
