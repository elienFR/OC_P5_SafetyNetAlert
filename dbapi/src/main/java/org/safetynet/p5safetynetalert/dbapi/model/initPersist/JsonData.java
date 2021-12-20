package org.safetynet.p5safetynetalert.dbapi.model.initPersist;

import lombok.Data;

@Data
public class JsonData {
  private JsonPersons persons;
  private JsonMedicalRecords medicalRecords;
  private JsonFireStations fireStations;
}
