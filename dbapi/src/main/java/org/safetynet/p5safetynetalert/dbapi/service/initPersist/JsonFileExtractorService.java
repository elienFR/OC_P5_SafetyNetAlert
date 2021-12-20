package org.safetynet.p5safetynetalert.dbapi.service.initPersist;

import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonData;

public interface JsonFileExtractorService {
  public JsonData fromFile(String fileName);
}
