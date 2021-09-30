package store.backend.springcybersource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Payload {

  public String toJson() {
    String json = "";
    ObjectMapper mapper = new ObjectMapper();
    try {
      json = mapper.writeValueAsString(this);
    } catch (Exception e) {}
    return json;
  }
}
