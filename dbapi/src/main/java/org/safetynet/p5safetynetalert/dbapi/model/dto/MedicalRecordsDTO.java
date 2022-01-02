package org.safetynet.p5safetynetalert.dbapi.model.dto;

import lombok.Data;

@Data
public class MedicalRecordsDTO {
  private Iterable<String> medications;
  private Iterable<String> allergies;
}