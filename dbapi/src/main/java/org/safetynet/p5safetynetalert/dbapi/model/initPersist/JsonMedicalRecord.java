package org.safetynet.p5safetynetalert.dbapi.model.initPersist;

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
}
