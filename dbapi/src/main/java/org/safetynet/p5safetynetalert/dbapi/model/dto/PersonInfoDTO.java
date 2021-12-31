package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
public class PersonInfoDTO {
  private String firstName;
  private String lastName;
  private Integer age;
  private String mail;
  private MedicalRecordsDTO medicalRecords;
}
