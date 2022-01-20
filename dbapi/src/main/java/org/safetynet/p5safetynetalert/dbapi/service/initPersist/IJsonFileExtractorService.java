package org.safetynet.p5safetynetalert.dbapi.service.initPersist;

import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonData;

public interface IJsonFileExtractorService {
  public JsonData fromFile(String fileName);
}
