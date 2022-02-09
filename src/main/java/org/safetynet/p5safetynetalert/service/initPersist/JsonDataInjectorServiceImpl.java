package org.safetynet.p5safetynetalert.service.initPersist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.service.initPersist.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonDataInjectorServiceImpl implements IJsonDataInjectorService {

  private static final Logger LOGGER = LogManager.getLogger(JsonDataInjectorServiceImpl.class);

  @Autowired
  private IJsonFileExtractorService IJsonFileExtractorService;
  //Repositories to inject data into databases
  @Autowired
  private AllergyImportService allergyImportService;
  @Autowired
  private FireStationImportService fireStationImportService;
  @Autowired
  private MedicationImportService medicationImportService;
  @Autowired
  private AddressImportService addressImportService;
  @Autowired
  private PersonImportService personImportService;
  @Autowired
  private PersonsMedicationImportService personsMedicationImportService;
  @Autowired
  private PersonsAllergyImportService personsAllergyImportService;

  private JsonData jsonData;

  /**
   * File name of the file to be imported. It's location must be in src/main/resources/ .
   */
  private String fileName = "data.json";


  /**
   * Script that initialise the database
   */
  @Override
  public void initDb() {
    LOGGER.debug("Starting database initialisation.");

    //Extracting file
    jsonData = IJsonFileExtractorService.fromFile(fileName);

    if (jsonData == null) {
      LOGGER.error("The object jsonData from json file is null.");
      throw new NullPointerException("JsonData is null. information cannot be imported.");
    } else {
      allergyImportService.importAllergies(jsonData);
      fireStationImportService.importFireStations(jsonData);
      addressImportService.importAddresses(jsonData);
      medicationImportService.importMedications(jsonData);
      personImportService.importPersons(jsonData);
      personsMedicationImportService.importPersonsMedications(jsonData);
      personsAllergyImportService.importPersonsAllergies(jsonData);
      LOGGER.debug("Database initialisation complete.");
    }
  }

}
