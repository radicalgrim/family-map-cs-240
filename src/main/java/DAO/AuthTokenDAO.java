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
      throw new DataAccessException("Error encountered while inserting an auth token into the database: " + e.getMessage());
    }
  }

  public AuthToken find(String tokenString) throws DataAccessException {
    AuthToken token;
    ResultSet rs = null;
    String sql = "SELECT * FROM Auth_Token WHERE Auth_Token = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, tokenString);
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
