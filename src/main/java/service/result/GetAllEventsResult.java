package service.result;

import model.Event;

public class GetAllEventsResult extends ErrorResult {
  Event[] data;

  public GetAllEventsResult() {
  }

  public Event[] getData() {
    return data;
  }

  public void setData(Event[] data) {
    this.data=data;
  }
}
