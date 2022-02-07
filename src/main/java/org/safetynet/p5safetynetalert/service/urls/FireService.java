package org.safetynet.p5safetynetalert.service.urls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.entity.Address;
import org.safetynet.p5safetynetalert.model.dto.FireDTO;
import org.safetynet.p5safetynetalert.service.IAddressService;
import org.safetynet.p5safetynetalert.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireService implements IFireService {

  private static final Logger LOGGER = LogManager.getLogger(FireService.class);
  @Autowired
  private IPersonService iPersonService;
  @Autowired
  private IAddressService iAddressService;

  /**
   * This method returns the list of inhabitants living at the given address as well as the number of
   * its associated fire station. The list includes name, phone number, age and background
   * (medications, dosage and allergies) for each person.
   *
   * @param road is the road of the address you want to recover the people from.
   * @return an FireDTO object containing fire station and people for this address. (see description)
   */
  @Override
  public FireDTO getFireDTOFromAddressInFire(String road) {
    LOGGER.debug("FireDTO creation...");
    Address address = iAddressService.getByRoad(road);
    FireDTO fireDTO = new FireDTO();
    fireDTO.setPersonsList(
      iPersonService.convertPersonsInPersonForFireDTO(
        iPersonService.getPersonsFromAddress(address))
    );
    fireDTO.setFireStationNumber(address.getFireStation().getNumber());
    LOGGER.debug("FireDTO properly created");
    return fireDTO;
  }
}
