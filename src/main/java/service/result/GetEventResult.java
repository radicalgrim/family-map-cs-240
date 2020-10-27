package service.result;

public class GetEventResult extends ErrorResult {
  private String associatedUsername;
  private String eventId;
  private String personId;
  private Double latitude;
  private Double longitude;
  private String country;
  private String city;
  private String eventType;
  private Integer year;

  public GetEventResult() {
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId=eventId;
  }

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String personId) {
    this.personId=personId;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude=latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude=longitude;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country=country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city=city;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType=eventType;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year=year;
  }
}
