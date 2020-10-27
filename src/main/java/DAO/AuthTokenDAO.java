package DAO;

import model.AuthToken;

public class AuthTokenDAO {
  public AuthTokenDAO() {
  }

  public Boolean insert(AuthToken token) { return false; }

  public Boolean delete(String username) {
    return false;
  }

  public AuthToken read(String username) {
    return new AuthToken();
  }

}
