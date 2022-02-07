package org.safetynet.p5safetynetalert.service.urls;

import org.safetynet.p5safetynetalert.model.dto.EmailListDTO;

public interface IEmailService {

  /**
   * This method returns a list of each email of each inhabitant of a city encapsulated
   * in an EmailListDTO Object.
   *
   * @param city is a String containing the city where emails are extracted from.
   * @return An EmailListDTO object, see description
   */
  EmailListDTO getAllEmailFromCityInhabitants(String city);

}
