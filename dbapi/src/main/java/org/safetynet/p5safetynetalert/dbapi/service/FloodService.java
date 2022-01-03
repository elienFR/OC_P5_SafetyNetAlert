package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.FireStation;
import org.safetynet.p5safetynetalert.dbapi.model.dto.FloodPersonsListDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonForFloodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FloodService {

  @Autowired
  FireStationService fireStationService;

  public FloodPersonsListDTO getPersonsFloodDTOFromFireStation(Collection<String> stations) {
    FloodPersonsListDTO floodPersonsListDTO = new FloodPersonsListDTO();
    Collection<PersonForFloodDTO> personForFloodDTOCollection = new ArrayList<>();

    for (String stationNumber : stations) {
      personForFloodDTOCollection.addAll(
        fireStationService.getPersonsForFlood(stationNumber)
      );
    }

    floodPersonsListDTO.setPersons(personForFloodDTOCollection);

    return floodPersonsListDTO;
  }
}
