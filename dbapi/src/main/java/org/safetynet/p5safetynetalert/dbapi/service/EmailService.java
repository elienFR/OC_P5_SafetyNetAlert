package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.dto.EmailListDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class EmailService {

  @Autowired
  AddressService addressService;
  @Autowired
  PersonService personService;

  public EmailListDTO getAllEmailFromCityInhabitants(String city) {
    Collection<Address> addresses = addressService.getAllByCity(city);
    if (addresses != null) {
      EmailListDTO emailListDTO = new EmailListDTO();
      emailListDTO.setEmailList(
          personService.getEmails(
          addressService.getPersons(addresses))
      );
      emailListDTO.setCityName(city);
      return emailListDTO;
    } else {
      return null;
    }
  }

}
