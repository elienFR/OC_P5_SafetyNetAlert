package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class PersonsFromFireStationDTO {
  private String title = "List of persons covered by a fire station.";
  private Integer adultCount;
  private Integer childrenCount;
  private Collection<PersonDTO> personsList;

}
