package org.safetynet.p5safetynetalert.model.initPersist;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JsonMedicalRecords {
  private List<JsonMedicalRecord> medicalrecords;
}
