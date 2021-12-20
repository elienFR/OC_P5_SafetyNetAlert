package org.safetynet.p5safetynetalert.dbapi.service.initPersist;

import com.google.gson.Gson;
import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.CustomProperties;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonFireStations;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecords;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPersons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Data
@Service
public class JsonFileExtractorServiceImpl implements JsonFileExtractorService {

  @Autowired
  private CustomProperties props;

public JsonData fromFile(String fileName) {

    //Json's file location
    String jsonLocation = props.getMainResourcesPath() + fileName;
    String jsonContent = new String();

    try {
      jsonContent = Files.readString(Path.of(jsonLocation));
    } catch (Exception e) {
      e.printStackTrace();
    }

    JsonData jsonData = new JsonData();
    //Json parser
    Gson gson = new Gson();
    jsonData.setPersons(gson.fromJson(jsonContent, JsonPersons.class));
    jsonData.setFireStations(gson.fromJson(jsonContent, JsonFireStations.class));
    jsonData.setMedicalRecords(gson.fromJson(jsonContent, JsonMedicalRecords.class));

    return jsonData;
  }
}


