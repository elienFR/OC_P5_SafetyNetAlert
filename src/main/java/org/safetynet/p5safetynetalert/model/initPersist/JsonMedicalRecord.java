package org.safetynet.p5safetynetalert.model.initPersist;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class JsonMedicalRecord {
  private String firstName;
  private String lastName;
  private String birthdate;
  private List<String> medications;
  private List<String> allergies;

  public JsonMedicalRecord(){}

  public JsonMedicalRecord(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
    this.medications = medications;
    this.allergies = allergies;
  }
}
