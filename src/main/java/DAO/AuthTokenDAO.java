package DAO;

import model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTokenDAO {
  private final Connection conn;

  public AuthTokenDAO(Connection conn) {
    this.conn = conn;
  }

  public void insert(AuthToken token) throws DataAccessException {
    String sql = "INSERT INTO Auth_Token (Auth_Token, Username) " +
            "VALUES(?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, token.getAuthToken());
      stmt.setString(2, token.getUsername());

      stmt.executeUpdate();

    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  /*
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
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }
  */

  public AuthToken find(String username) throws DataAccessException {
    AuthToken token;
    ResultSet rs = null;
    String sql = "SELECT * FROM Auth_Token WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        token = new AuthToken(rs.getString("Auth_Token"), rs.getString("Username"));
        return token;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding auth token");
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

  /*
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
  */

  public void delete() throws DataAccessException{
    String sql = "DELETE FROM Auth_Token";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing table");
    }
  }
}
