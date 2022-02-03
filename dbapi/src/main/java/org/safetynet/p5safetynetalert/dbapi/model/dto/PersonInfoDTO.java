package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PersonInfoDTO {
  private String firstName;
  private String lastName;
  private Integer age;
  private String mail;
  private MedicalRecordsDTO medicalRecords;

  public PersonInfoDTO(){}
  public PersonInfoDTO(String firstName, String lastName, Integer age, String mail, MedicalRecordsDTO medicalRecords) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.mail = mail;
    this.medicalRecords = medicalRecords;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonInfoDTO that = (PersonInfoDTO) o;
    return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(age, that.age) && Objects.equals(mail, that.mail) && Objects.equals(medicalRecords, that.medicalRecords);
  }
}
