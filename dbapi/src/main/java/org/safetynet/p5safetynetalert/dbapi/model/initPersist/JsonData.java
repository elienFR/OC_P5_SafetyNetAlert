package org.safetynet.p5safetynetalert.dbapi.model.initPersist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonData {
  private JsonPersons persons;
  private JsonMedicalRecords medicalRecords;
  private JsonFireStations fireStations;

}
