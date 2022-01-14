package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.safetynet.p5safetynetalert.dbapi.model.dto.FireDTO;

public interface IFireService {

  /**
   * This url returns the list of inhabitants living at the given address
   * as well as the number of the barracks of firefighters serving it.
   * The list includes name, phone number, age and background (medications, dosage and allergies)
   * for each person.
   */
  FireDTO getFireDTOFromAddressInFire(String road);

}
