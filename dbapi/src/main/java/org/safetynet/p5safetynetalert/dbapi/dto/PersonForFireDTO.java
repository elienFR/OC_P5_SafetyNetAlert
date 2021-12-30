package org.safetynet.p5safetynetalert.dbapi.dto;

import lombok.Data;

@Data
public class PersonForFireDTO {
  private String firstName;
  private String lastName;
  private String phone;
  private MedicalRecordsDTO medicalRecords;

  public PersonForFireDTO(String firstName, String lastName, String phone, MedicalRecordsDTO medicalRecords) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.medicalRecords = medicalRecords;
  }
}
