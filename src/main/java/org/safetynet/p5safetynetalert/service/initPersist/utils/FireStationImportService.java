package org.safetynet.p5safetynetalert.service.initPersist.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.entity.FireStation;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.repository.FireStationRepository;
import org.safetynet.p5safetynetalert.service.initPersist.JsonDataInjectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class FireStationImportService {

  @Autowired
  private FireStationRepository fireStationRepository;

  private static final Logger LOGGER = LogManager.getLogger(JsonDataInjectorServiceImpl.class);

  /**
   * script that import fire stations from a json data object
   */
  public void importFireStations(JsonData jsonData) {
    LOGGER.debug("importing fire stations...");

    Set<String> mySet = new TreeSet<>();
    for (JsonFireStation jsonFireStation : jsonData.getFireStations().getFirestations()) {
      if (!mySet.contains(jsonFireStation.getStation())) {
        FireStation fireStationToSave = new FireStation();
        fireStationToSave.setNumber(jsonFireStation.getStation());
        fireStationRepository.save(fireStationToSave);
      }
      mySet.add(jsonFireStation.getStation());
    }
  }

}
