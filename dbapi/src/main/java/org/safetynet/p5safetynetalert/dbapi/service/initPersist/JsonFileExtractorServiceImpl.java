package org.safetynet.p5safetynetalert.dbapi.service.initPersist;

import com.google.gson.Gson;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.CustomProperties;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonFireStations;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecords;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPersons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class JsonFileExtractorServiceImpl implements IJsonFileExtractorService {

  private Logger logger = LogManager.getLogger(JsonFileExtractorServiceImpl.class);

  @Autowired
  private CustomProperties props;

  /**
   * This method creates Java object from a specific json file located in src/main/resources
   *
   * @param fileName is the name of the json file
   * @return a JsonData Object
   */
  public JsonData fromFile(String fileName) {
    logger.debug("Start Extracting Json file.");

    //Json's file location
    String jsonLocation = props.getMainResourcesPath() + fileName;
    String jsonContent = new String();

    JsonData jsonData = new JsonData();
    try {

      jsonContent = Files.readString(Path.of(jsonLocation));

      //Json parser

      Gson gson = new Gson();
      jsonData.setPersons(gson.fromJson(jsonContent, JsonPersons.class));
      jsonData.setFireStations(gson.fromJson(jsonContent, JsonFireStations.class));
      jsonData.setMedicalRecords(gson.fromJson(jsonContent, JsonMedicalRecords.class));

      logger.debug("Json file extraction finished");



    } catch (Exception e) {
      jsonData = null;
      logger.error("The file could not be read.");
      e.printStackTrace();
    } finally {
      return jsonData;
    }
  }
}


