package org.safetynet.p5safetynetalert.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PersonsInfoDTO {
  private final String title = "Persons' Informations";
  private PersonInfoDTO personsInfo;
  private Iterable<PersonInfoDTO> personsFromTheSameFamily;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonsInfoDTO that = (PersonsInfoDTO) o;
    return Objects.equals(personsInfo, that.personsInfo) && Objects.equals(personsFromTheSameFamily, that.personsFromTheSameFamily);
  }
}
