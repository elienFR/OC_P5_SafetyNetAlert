package org.safetynet.p5safetynetalert.dbapi.model.initPersist;


import lombok.Getter;

import java.util.List;

@Getter
public class JsonMedicalRecords {
  private List<JsonMedicalRecord> medicalRecords;
}
