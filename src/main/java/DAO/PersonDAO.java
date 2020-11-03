package DAO;

import model.Event;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDAO {
  private final Connection conn;

  public PersonDAO(Connection conn) {
    this.conn=conn;
  }

  public void insert(Person person) throws DAO.DataAccessException {
    String sql = "INSERT INTO Person (Id, Username, First_Name, Last_Name, Gender, Father_Id, Mother_Id, Spouse_Id) " +
            "VALUES(?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, person.getId());
      stmt.setString(2, person.getUsername());
      stmt.setString(3, person.getFirstName());
      stmt.setString(4, person.getLastName());
      stmt.setString(5, person.getGender());
      stmt.setString(6, person.getFatherId());
      stmt.setString(7, person.getMotherId());
      stmt.setString(8, person.getSpouseId());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting a person into the database: " + e.getMessage());
    }
  }

  public Person find(String personId) throws DAO.DataAccessException {
    Person person;
    ResultSet rs = null;
    String sql = "SELECT * FROM Person WHERE Id = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, personId);
      rs = stmt.executeQuery();
      if (rs.next()) {
        person = new Person(rs.getString("Id"), rs.getString("Username"),
                rs.getString("First_Name"), rs.getString("Last_Name"),
                rs.getString("Gender"), rs.getString("Father_Id"),
                rs.getString("Mother_Id"), rs.getString("Spouse_Id"));
        return person;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding person");
    } finally {
      if(rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
    return null;
  }

  public Person[] findUserPersons(String username) throws DataAccessException {
    Person person;
    ArrayList<Person> dataList = new ArrayList<>();
    ResultSet rs = null;
    String sql = "SELECT * FROM Person WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      while (rs.next()) {
        person = new Person(rs.getString("Id"), rs.getString("Username"),
                rs.getString("First_Name"), rs.getString("Last_Name"),
                rs.getString("Gender"), rs.getString("Father_Id"),
                rs.getString("Mother_Id"), rs.getString("Spouse_Id"));
        dataList.add(person);
      }
      if (!dataList.isEmpty()) {
        Person[] dataArray = new Person[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
          dataArray[i] = dataList.get(i);
        }
        return dataArray;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding persons");
    } finally {
      if(rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
    return null;
  }

  public void deleteUserPersons(String username) throws DataAccessException {
    String sql = "DELETE FROM Person WHERE Username = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing persons from table");
    }
  }

  public void delete() throws DataAccessException {
    String sql = "DELETE FROM Person";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing table");
    }
  }
}
