package org.safetynet.p5safetynetalert.service.urls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.entity.Address;
import org.safetynet.p5safetynetalert.model.dto.EmailListDTO;
import org.safetynet.p5safetynetalert.service.IAddressService;
import org.safetynet.p5safetynetalert.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EmailService implements IEmailService {

  @Autowired
  IAddressService iAddressService;
  @Autowired
  IPersonService iPersonService;

  private static final Logger LOGGER = LogManager.getLogger(EmailService.class);

  /**
   * This method returns a list of each email of each inhabitant of a city encapsulated
   * in an EmailListDTO Object.
   *
   * @param city is a String containing the city where emails are extracted from.
   * @return An EmailListDTO object, see description
   */
  @Override
  public EmailListDTO getAllEmailFromCityInhabitants(String city) {
    EmailListDTO emailListDTO = new EmailListDTO();
    Collection<Address> addresses = iAddressService.getAllByCity(city);
    if (addresses == null || addresses.size()==0) {
      LOGGER.debug("Addresses collection is null.");
      return null;
    } else {
      emailListDTO.setEmailList(
        iPersonService.getEmails(
          iPersonService.getPersonsFromAddresses(
            addresses)));
      emailListDTO.setCityName(city);
      LOGGER.debug("Email successfully extracted.");
      return emailListDTO;
    }
  }

}
