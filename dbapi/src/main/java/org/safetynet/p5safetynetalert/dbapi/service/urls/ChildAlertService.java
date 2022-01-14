package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;
import org.safetynet.p5safetynetalert.dbapi.service.AddressService;
import org.safetynet.p5safetynetalert.dbapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildAlertService {
  @Autowired
  AddressService addressService;
  @Autowired
  PersonService personService;

  private static final Logger LOGGER = LogManager.getLogger(ChildAlertService.class);

  /**
   * This method returns a list of children (anyone aged 18 or under) living at a specific road.
   * The list includes each child's first and last name, age, and a list of others
   * household members. If there is no child, this method return an empty string for children.
   *
   * @param road it is the String of the road
   * @return a ChildFromAddressDTO (see description)
   * @throws Exception
   */
  public ChildFromAddressDTO getChildrenFromAddress(String road) {
    Address address = addressService.getByRoad(road);

    if (address == null) {
      LOGGER.debug("Address returned is null");
      return null;
    } else {
      ChildFromAddressDTO childFromAddressDTO = new ChildFromAddressDTO();
      childFromAddressDTO.setChildrenAtAddress(personService.getChildrenDTO(address));
      childFromAddressDTO.setOtherAdultsAtAddress(personService.getAdultsDTO(address));
      return childFromAddressDTO;
    }
  }
}
