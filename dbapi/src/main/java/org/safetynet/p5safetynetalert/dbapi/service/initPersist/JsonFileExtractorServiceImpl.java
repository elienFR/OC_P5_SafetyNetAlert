package org.safetynet.p5safetynetalert.dbapi.service.initPersist;

import com.google.gson.Gson;
import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.CustomProperties;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonFireStations;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecords;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPersons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Data
@Service
public class JsonFileExtractorServiceImpl implements JsonFileExtractorService {

  private Logger logger = LoggerFactory.getLogger(JsonFileExtractorServiceImpl.class);

  @Autowired
  private CustomProperties props;

  /**
   * This method creates Java object from a specific json file located in src/main/resources
   *
   * @param fileName is the name of the json file
   * @return a JsonData Object
   */
  public JsonData fromFile(String fileName) {
    logger.info("Start Extracting Json file.");

    //Json's file location
    String jsonLocation = props.getMainResourcesPath() + fileName;
    String jsonContent = new String();

    try {
      jsonContent = Files.readString(Path.of(jsonLocation));
    } catch (Exception e) {
      e.printStackTrace();
    }

    //Json parser
    JsonData jsonData = new JsonData();
    Gson gson = new Gson();
    jsonData.setPersons(gson.fromJson(jsonContent, JsonPersons.class));
    jsonData.setFireStations(gson.fromJson(jsonContent, JsonFireStations.class));
    jsonData.setMedicalRecords(gson.fromJson(jsonContent, JsonMedicalRecords.class));

    logger.info("Json file extraction finished");

    return jsonData;
  }
}


