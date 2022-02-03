package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PhonesDTO {

  private final String title = "List of phones covered by a fire station.";
  private Iterable<String> phonesList;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PhonesDTO phonesDTO = (PhonesDTO) o;
    return Objects.equals(phonesList, phonesDTO.phonesList);
  }
}
