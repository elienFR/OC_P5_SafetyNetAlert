package org.safetynet.p5safetynetalert.model.dto;

import lombok.Setter;

import java.util.Objects;

@Setter
public class PersonForFloodDTO {
  private String firstName;
  private String lastName;
  private String phone;
  private Integer age;
  private MedicalRecordsDTO medicalRecords;

  public PersonForFloodDTO(){}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonForFloodDTO that = (PersonForFloodDTO) o;
    return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phone, that.phone) && Objects.equals(age, that.age) && Objects.equals(medicalRecords, that.medicalRecords);
  }
}
