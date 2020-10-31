package DAO;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
  private final Connection conn;

  public UserDAO(Connection conn) {
    this.conn = conn;
  }

  public void insert(User user) throws DAO.DataAccessException {
    String sql = "INSERT INTO User (Username, Password, Email, First_Name, Last_Name, Gender, Person_Id) " +
            "VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getEmail());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.setString(6, user.getGender());
      stmt.setString(7, user.getPersonId());

      stmt.executeUpdate();

    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting a user into the database: " + e.getMessage());
    }
  }

  public User find(String username) throws DAO.DataAccessException {
    User user;
    ResultSet rs = null;
    String sql = "SELECT * FROM User WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("Username"), rs.getString("Password"),
                rs.getString("Email"), rs.getString("First_Name"),
                rs.getString("Last_Name"), rs.getString("Gender"),
                rs.getString("Person_Id"));
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding user");
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

  public String findPersonId(String username) throws DAO.DataAccessException {
    String id;
    ResultSet rs = null;
    String sql = "SELECT Person_Id FROM User WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        id = rs.getString("Person_Id");
        return id;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding person id");
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

  public void delete() throws DataAccessException {
    String sql = "DELETE FROM User";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing table");
    }
  }

}
