package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
public class PersonForFloodDTO {
  private String firstName;
  private String lastName;
  private String phone;
  private Integer age;
  private MedicalRecordsDTO medicalRecords;

}
