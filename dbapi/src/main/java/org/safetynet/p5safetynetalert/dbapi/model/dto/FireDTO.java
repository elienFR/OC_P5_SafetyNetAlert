package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class FireDTO {
  private String fireStationNumber;
  Iterable<PersonForFireDTO> personsList;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FireDTO fireDTO = (FireDTO) o;
    return Objects.equals(fireStationNumber, fireDTO.fireStationNumber) && Objects.equals(personsList, fireDTO.personsList);
  }
}
