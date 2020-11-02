package service.result;

import model.Event;

public class EventResult extends ErrorResult {
  private Event[] data;
  private String associatedUsername;
  private String eventID;
  private String personID;
  private Float latitude;
  private Float longitude;
  private String country;
  private String city;
  private String eventType;
  private Integer year;

  public EventResult() {
  }

  public EventResult(String message, Boolean success) {
    super(message, success);
  }

  public EventResult(Event[] data, Boolean success) {
    this.data = data;
    this.success = success;
  }

  public EventResult(String associatedUsername, String eventID, String personID, Float latitude, Float longitude,
                     String country, String city, String eventType, Integer year, Boolean success) {
    this.associatedUsername = associatedUsername;
    this.eventID = eventID;
    this.personID = personID;
    this.latitude = latitude;
    this.longitude = longitude;
    this.country = country;
    this.city = city;
    this.eventType = eventType;
    this.year = year;
    this.success = success;
  }

  public Event[] getData() {
    return data;
  }

  public void setData(Event[] data) {
    this.data=data;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername = associatedUsername;
  }

  public String getEventID() {
    return eventID;
  }

  public void setEventID(String eventID) {
    this.eventID = eventID;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID = personID;
  }

  public Float getLatitude() {
    return latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }

  public Float getLongitude() {
    return longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }
}
