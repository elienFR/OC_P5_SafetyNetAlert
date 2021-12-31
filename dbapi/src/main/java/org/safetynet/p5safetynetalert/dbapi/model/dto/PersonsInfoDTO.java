package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
public class PersonsInfoDTO {
  private final String title = "Persons' Informations";
  private Iterable<PersonInfoDTO> personsInfoDTO;
}
