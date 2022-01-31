package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
public class PersonForFloodDTO {
  private String firstName;
  private String lastName;
  private String phone;
  private Integer age;
  private MedicalRecordsDTO medicalRecords;

  public PersonForFloodDTO(){}
  public PersonForFloodDTO(String firstName, String lastName, String phone, Integer age, MedicalRecordsDTO medicalRecords) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.age = age;
    this.medicalRecords = medicalRecords;
  }
}
