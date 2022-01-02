package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.dto.EmailListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EmailService {

  @Autowired
  AddressService addressService;
  @Autowired
  PersonService personService;

  public EmailListDTO getAllEmailFromCityInhabitants(String city) {
    EmailListDTO emailListDTO = new EmailListDTO();
    Collection<Address> addresses = addressService.getAllByCity(city);
    if (addresses == null || addresses.size()==0) {
      return null;
    } else {
      emailListDTO.setEmailList(
        personService.getEmails(
          addressService.getPersons(
            addresses)));
      emailListDTO.setCityName(city);

      return emailListDTO;
    }
  }

}
