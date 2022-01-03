package org.safetynet.p5safetynetalert.dbapi.model.initPersist;

import lombok.Data;

import java.util.List;

@Data
public class JsonMedicalRecord {
  private String firstName;
  private String lastName;
  private String birthdate;
  private List<String> medications;
  private List<String> allergies;
}
