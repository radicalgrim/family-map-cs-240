package DAO;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventDAOTest {
    private Database db;
    private Event sampleEvent1;
    private Event sampleEvent2;
    private Event sampleEvent3;
    private EventDAO eventDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        sampleEvent1 = new Event("Biking_123A", "Gale", "Gale123A", 35.9f,
                140.1f, "Japan", "Ushiku", "Biking_Around", 2016);
        sampleEvent2 = new Event("Biking_456A", "Gale", "Gale123A", 35.9f,
                140.1f, "Japan", "Ushiku", "Biking_Around2", 2017);
        sampleEvent3 = new Event("Biking_789A", "DifferentUser", "Gale123A", 35.9f,
                140.1f, "Japan", "Ushiku", "Biking_Around3", 2018);
        Connection conn = db.getConnection();
        db.clearTables();
        eventDAO = new EventDAO(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        eventDAO.insert(sampleEvent1);
        Event compareTest = eventDAO.find(sampleEvent1.getEventID());
        assertNotNull(compareTest);
        assertEquals(sampleEvent1, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        eventDAO.insert(sampleEvent1);
        assertThrows(DataAccessException.class, ()-> eventDAO.insert(sampleEvent1));
    }

    @Test
    public void findPass() throws DataAccessException {
        eventDAO.insert(sampleEvent1);
        Event compareTest = eventDAO.find(sampleEvent1.getEventID());
        assertNotNull(compareTest);
        assertEquals(sampleEvent1, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        assertNull(eventDAO.find(sampleEvent1.getEventID()));
    }

    @Test
    public void findUserEventsPass() throws DataAccessException {
        eventDAO.insert(sampleEvent1); // Gale
        eventDAO.insert(sampleEvent2); // Gale
        eventDAO.insert(sampleEvent3); // DifferentUser
        Event[] compareTest = eventDAO.findUserEvents("Gale");
        assertNotNull(compareTest);
        assertEquals(sampleEvent1, compareTest[0]);
        assertEquals(sampleEvent2, compareTest[1]);
        assertEquals(2, compareTest.length);
    }

    @Test
    public void findUserEventsFail() throws DataAccessException {
        eventDAO.insert(sampleEvent3); // DifferentUser
        assertNull(eventDAO.findUserEvents("Gale"));
    }

    @Test
    public void deleteTest() throws DataAccessException {
        eventDAO.insert(sampleEvent1);
        eventDAO.delete();
        assertNull(eventDAO.find(sampleEvent1.getEventID()));
    }

    @Test
    public void deleteUserEventsTest() throws DataAccessException {
        eventDAO.insert(sampleEvent1); // Gale
        eventDAO.insert(sampleEvent2); // Gale
        eventDAO.insert(sampleEvent3); // DifferentUser
        eventDAO.deleteUserEvents("Gale");
        assertNull(eventDAO.find(sampleEvent1.getEventID()));
        assertNull(eventDAO.find(sampleEvent2.getEventID()));
        assertEquals(sampleEvent3, eventDAO.find(sampleEvent3.getEventID()));
    }
}
