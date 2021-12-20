package org.safetynet.p5safetynetalert.dbapi.model.initPersist;

import lombok.Data;

import java.util.List;

@Data
public class JsonPersons {
  private List<JsonPerson> persons;
}
