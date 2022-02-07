package org.safetynet.p5safetynetalert.service.urls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.model.entity.Address;
import org.safetynet.p5safetynetalert.service.IAddressService;
import org.safetynet.p5safetynetalert.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildAlertService implements IChildAlertService {
  @Autowired
  private IAddressService iAddressService;
  @Autowired
  private IPersonService iPersonService;

  private static final Logger LOGGER = LogManager.getLogger(ChildAlertService.class);

  /**
   * This method returns a list of children (anyone aged 18 or under) living at a specific road.
   * The list includes each child's first and last name, age, and a list of others
   * household members. If there is no child, this method return an empty string for children.
   *
   * @param road it is the String of the road
   * @return a ChildFromAddressDTO (see description)
   */
  @Override
  public ChildFromAddressDTO getChildrenFromAddress(String road) {
    Address address = iAddressService.getByRoad(road);

    if (address == null) {
      LOGGER.debug("Address returned is null");
      return null;
    } else {
      ChildFromAddressDTO childFromAddressDTO = new ChildFromAddressDTO();
      childFromAddressDTO.setChildrenAtAddress(iPersonService.getChildrenDTO(address));
      childFromAddressDTO.setOtherAdultsAtAddress(iPersonService.getAdultsDTO(address));
      return childFromAddressDTO;
    }
  }
}
