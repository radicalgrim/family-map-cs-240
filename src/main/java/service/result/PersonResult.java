package service.result;

import model.Person;

public class PersonResult extends ErrorResult {
  Person[] data;

  public PersonResult() {
  }

  public Person[] getData() {
    return data;
  }

  public void setData(Person[] data) {
    this.data=data;
  }
}
