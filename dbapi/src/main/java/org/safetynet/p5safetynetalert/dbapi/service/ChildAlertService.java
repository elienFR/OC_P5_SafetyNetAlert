package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildAlertService {
  @Autowired
  AddressService addressService;

  /**
   * This method returns a list of children (anyone aged 18 or under) living at a specific road.
   * The list includes each child's first and last name, age, and a list of others
   * household members. If there is no child, this method return an empty string for children.
   *
   * @param road it is the String of the road
   * @return a ChildFromAddressDTO (see description)
   * @throws Exception
   */
  public ChildFromAddressDTO getChildrenFromAddress(String road) throws Exception {
    Address address = addressService.getByRoad(road);

    ChildFromAddressDTO childFromAddressDTO = new ChildFromAddressDTO();

    childFromAddressDTO.setChildrenAtAddress(addressService.getChildrenDTO(address));
    childFromAddressDTO.setOtherAdultsAtAddress(addressService.getAdultsDTO(address));

    return childFromAddressDTO;
  }
}
