package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.dto.FloodPersonsListDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FloodService {
  public FloodPersonsListDTO getPersonsFloodDTOFromFireStation(Collection<String> stations) {
    FloodPersonsListDTO floodPersonsListDTO = new FloodPersonsListDTO();
    return floodPersonsListDTO;
  }
}
