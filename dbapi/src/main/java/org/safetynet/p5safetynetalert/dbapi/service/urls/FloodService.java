package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.dto.FloodPersonsListDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonForFloodDTO;
import org.safetynet.p5safetynetalert.dbapi.service.urls.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FloodService implements IFLoodService{

  private static final Logger LOGGER = LogManager.getLogger(FloodService.class);
  @Autowired
  private IFireStationService iFireStationService;

  /**
   * This method returns a list of all the homes served by the fire station. This list includes the
   * people by address. It also includes the name, telephone number and age of the inhabitants,
   * and includes their medical history (medications, dosage and allergies) next to each name.
   *
   * @param stations is a collection of fire station.
   * @return a FloodPersonsListDTO
   */
  @Override
  public FloodPersonsListDTO getPersonsFloodDTOFromFireStation(Collection<String> stations) {
    LOGGER.debug("Creating Flood persons List DTO...");
    FloodPersonsListDTO floodPersonsListDTO = new FloodPersonsListDTO();
    Collection<PersonForFloodDTO> personForFloodDTOCollection = new ArrayList<>();

    for (String stationNumber : stations) {
      personForFloodDTOCollection.addAll(
        iFireStationService.getPersonsForFlood(stationNumber)
      );
    }
    floodPersonsListDTO.setPersons(personForFloodDTOCollection);
    LOGGER.debug("Flood persons List DTO created.");
    return floodPersonsListDTO;
  }
}
