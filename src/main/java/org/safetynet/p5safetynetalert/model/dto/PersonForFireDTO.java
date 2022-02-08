package org.safetynet.p5safetynetalert.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PersonForFireDTO {
  private String firstName;
  private String lastName;
  private String phone;
  private MedicalRecordsDTO medicalRecords;

  public PersonForFireDTO(){}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonForFireDTO that = (PersonForFireDTO) o;
    return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phone, that.phone) && Objects.equals(medicalRecords, that.medicalRecords);
  }
}
