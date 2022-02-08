package org.safetynet.p5safetynetalert.model.dto;

import java.util.Collection;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonsFromFireStationDTO {
  private final String title = "List of persons covered by a fire station.";
  private Integer adultCount;
  private Integer childrenCount;
  private Collection<PersonDTO> personsList;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonsFromFireStationDTO that = (PersonsFromFireStationDTO) o;
    return Objects.equals(title, that.title) && Objects.equals(adultCount, that.adultCount) && Objects.equals(childrenCount, that.childrenCount) && Objects.equals(personsList, that.personsList);
  }
}
