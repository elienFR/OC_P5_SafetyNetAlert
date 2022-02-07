package org.safetynet.p5safetynetalert.service.initPersist;

import org.safetynet.p5safetynetalert.model.initPersist.JsonData;

public interface IJsonFileExtractorService {
  public JsonData fromFile(String fileName);
}
