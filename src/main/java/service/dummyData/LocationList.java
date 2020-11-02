package service.dummyData;

public class LocationList {
  Location[] data;

  public LocationList() {
  }

  public LocationList(Location[] locations) {
    this.data = locations;
  }

  public Location[] getData() {
    return data;
  }

  public void setData(Location[] data) {
    this.data = data;
  }
}
