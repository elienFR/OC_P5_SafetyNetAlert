package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
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
}
