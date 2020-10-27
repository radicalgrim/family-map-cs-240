package model;

public class Person {
  private String id;
  private String username;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherId;
  private String motherId;
  private String spouseId;

  public Person(String id, String username, String firstName, String lastName, String gender, String fatherId, String motherId, String spouseId) {
    this.id=id;
    this.username=username;
    this.firstName=firstName;
    this.lastName=lastName;
    this.gender=gender;
    this.fatherId=fatherId;
    this.motherId=motherId;
    this.spouseId=spouseId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id=id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
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

  public String getFatherId() {
    return fatherId;
  }

  public void setFatherId(String fatherId) {
    this.fatherId=fatherId;
  }

  public String getMotherId() {
    return motherId;
  }

  public void setMotherId(String motherId) {
    this.motherId=motherId;
  }

  public String getSpouseId() {
    return spouseId;
  }

  public void setSpouseId(String spouseId) {
    this.spouseId=spouseId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof Person) {
      Person oPerson = (Person) o;
      return oPerson.getId().equals(getId()) &&
              oPerson.getUsername().equals(getUsername()) &&
              oPerson.getFirstName().equals(getFirstName()) &&
              oPerson.getLastName().equals(getLastName()) &&
              oPerson.getGender().equals(getGender()) &&
              oPerson.getFatherId().equals(getFatherId()) &&
              oPerson.getMotherId().equals(getMotherId()) &&
              oPerson.getSpouseId().equals(getSpouseId());
    } else {
      return false;
    }
  }

}
