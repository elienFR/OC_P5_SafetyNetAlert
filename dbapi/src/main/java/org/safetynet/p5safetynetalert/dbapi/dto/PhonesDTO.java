package org.safetynet.p5safetynetalert.dbapi.dto;

import lombok.Data;

@Data
public class PhonesDTO {

  private String title = "List of phones covered by a fire station.";
  private Iterable<String> phonesList;

}
