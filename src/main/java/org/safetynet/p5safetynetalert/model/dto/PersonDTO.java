package org.safetynet.p5safetynetalert.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
public class PersonDTO {
  private String firstName;
  private String lastName;
  private String phone;
  private String birthDate;
  private AddressDTO address;

  public PersonDTO(){}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonDTO personDTO = (PersonDTO) o;
    return Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(phone, personDTO.phone) && Objects.equals(birthDate, personDTO.birthDate) && Objects.equals(address, personDTO.address);
  }
}
