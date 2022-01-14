package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.safetynet.p5safetynetalert.dbapi.model.dto.FloodPersonsListDTO;

import java.util.Collection;

public interface IFLoodService {

  /**
   * This method returns a list of all the homes served by the fire station. This list includes the
   * people by address. It also includes the name, telephone number and age of the inhabitants,
   * and includes their medical history (medications, dosage and allergies) next to each name.
   *
   * @param stations is a collection of fire station.
   * @return a FloodPersonsListDTO
   */
  FloodPersonsListDTO getPersonsFloodDTOFromFireStation(Collection<String> stations);

}
