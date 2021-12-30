package org.safetynet.p5safetynetalert.dbapi.dto;

import lombok.Data;

@Data
public class PersonsFromFireStationDTO {
  private Integer adultCount;
  private Integer childrenCount;
  private Iterable<PersonDTO> personsList;

  public PersonsFromFireStationDTO(){}
  public PersonsFromFireStationDTO(Integer adultCount, Integer childrenCount, Iterable<PersonDTO> personsList) {
    this.adultCount = adultCount;
    this.childrenCount = childrenCount;
    this.personsList = personsList;
  }
}
