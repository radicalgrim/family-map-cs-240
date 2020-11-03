package service.result;

import model.Person;

public class PersonResult extends ErrorResult {
  private Person[] data;
  private String associatedUsername;
  private String personID;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID; // Can be optional
  private String motherID; // Can be optional
  private String spouseID; // Can be optional

  public PersonResult() {
  }

  public PersonResult(String message, Boolean success) {
    super(message, success);
  }

  public PersonResult(Person[] data, Boolean success) {
    this.data = data;
    this.success = success;
  }

  public PersonResult(String associatedUsername, String personID, String firstName, String lastName, String gender,
                      String fatherID, String motherID, String spouseID, Boolean success) {
    this.associatedUsername = associatedUsername;
    this.personID = personID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.fatherID = fatherID;
    this.motherID = motherID;
    this.spouseID = spouseID;
    this.success = success;
  }


  public Person[] getData() {
    return data;
  }

  public void setData(Person[] data) {
    this.data=data;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID = personID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername = associatedUsername;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getFatherID() {
    return fatherID;
  }

  public void setFatherID(String fatherID) {
    this.fatherID = fatherID;
  }

  public String getMotherID() {
    return motherID;
  }

  public void setMotherID(String motherID) {
    this.motherID = motherID;
  }

  public String getSpouseID() {
    return spouseID;
  }

  public void setSpouseID(String spouseID) {
    this.spouseID = spouseID;
  }
}
