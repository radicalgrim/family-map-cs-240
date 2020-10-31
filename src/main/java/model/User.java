package model;

import com.google.gson.annotations.SerializedName;

public class User {
  @SerializedName("userName")
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;
  @SerializedName("personID")
  private String personId;

  public User(String username, String password, String email, String firstName, String lastName, String gender, String personId) {
    this.username=username;
    this.password=password;
    this.email=email;
    this.firstName=firstName;
    this.lastName=lastName;
    this.gender=gender;
    this.personId=personId;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password=password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email=email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName=firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName=lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender=gender;
  }

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String personId) {
    this.personId=personId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof User) {
      User oUser = (User) o;
      return oUser.getUsername().equals(getUsername()) &&
              oUser.getPassword().equals(getPassword()) &&
              oUser.getEmail().equals(getEmail()) &&
              oUser.getFirstName().equals(getFirstName()) &&
              oUser.getLastName().equals(getLastName()) &&
              oUser.getGender().equals(getGender()) &&
              oUser.getPersonId().equals(getPersonId());
    } else {
      return false;
    }
  }

}
