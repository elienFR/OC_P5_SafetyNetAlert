package org.safetynet.p5safetynetalert.model.dto;


import lombok.Setter;

import java.util.Objects;

@Setter
public class PersonInfoDTO {
  private String firstName;
  private String lastName;
  private Integer age;
  private String mail;
  private MedicalRecordsDTO medicalRecords;

  public PersonInfoDTO(){}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonInfoDTO that = (PersonInfoDTO) o;
    return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(age, that.age) && Objects.equals(mail, that.mail) && Objects.equals(medicalRecords, that.medicalRecords);
  }

}
