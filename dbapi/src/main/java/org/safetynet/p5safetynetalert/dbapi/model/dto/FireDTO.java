package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
public class FireDTO {
  private String fireStationNumber;
  Iterable<PersonForFireDTO> personsList;
}
