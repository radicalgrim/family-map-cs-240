package service.result;

import model.Person;

public class GetAllPersonsResult extends ErrorResult {
  Person[] data;

  public GetAllPersonsResult() {
  }

  public Person[] getData() {
    return data;
  }

  public void setData(Person[] data) {
    this.data=data;
  }
}
