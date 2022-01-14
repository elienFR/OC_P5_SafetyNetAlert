package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.safetynet.p5safetynetalert.dbapi.model.dto.FireDTO;

public interface IFireService {

  /**
   * This method returns the list of inhabitants living at the given address as well as the number of
   * its associated fire station. The list includes name, phone number, age and background
   * (medications, dosage and allergies) for each person.
   *
   * @param road is the road of the address you want to recover the people from.
   * @return an FireDTO object containing fire station and people for this address. (see description)
   */
  FireDTO getFireDTOFromAddressInFire(String road);

}
