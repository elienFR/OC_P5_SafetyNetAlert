package org.safetynet.p5safetynetalert.dbapi.dto;

import lombok.Data;

@Data
public class FireDTO {
  private String fireStationNumber;
  Iterable<PersonForFireDTO> personsList;
}
