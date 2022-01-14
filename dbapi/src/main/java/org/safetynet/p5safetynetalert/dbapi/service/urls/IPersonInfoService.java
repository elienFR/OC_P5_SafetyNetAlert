package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsInfoDTO;

public interface IPersonInfoService {

  /**
   * This method returns the name, address, age, email address and medical history (drugs,
   * dosage, allergies) of each inhabitant. If more than one person has the same last name, they
   * all appear.
   *
   * @param firstName it is the first name of the person you desire the info
   * @param lastName  not null - not blank - it is the last name of the person you desire the info
   * @return an object PersonsInfoDTO (see description)
   */
  PersonsInfoDTO getPersonInfoFromFirstAndOrLastName(String firstName, String lastName);
}
