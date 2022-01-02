package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.dto.FireDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireService {

  @Autowired
  private PersonService personService;
  @Autowired
  private AddressService addressService;

  /**
   * This method returns the list of inhabitants living at the given address as well as the number of
   * its associated fire station. The list includes name, phone number, age and background
   * (medications, dosage and allergies) for each person.
   *
   * @param road is the road of the address you want to recover the people from.
   * @return an FireDTO object containing fire station and people f this address. (see description)
   */
  public FireDTO getFireDTOFromAddressInFire(String road) {
    Address address = addressService.getByRoad(road);

    FireDTO fireDTO = new FireDTO();

    fireDTO.setPersonsList(
      personService.getPersonsForFireDTOFromAddressInFire(
        addressService.getPersons(address))
    );
    fireDTO.setFireStationNumber(address.getFireStation().getNumber());

    return fireDTO;
  }

}
