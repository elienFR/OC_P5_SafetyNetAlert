package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.dto.EmailListDTO;
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

  public EmailListDTO getAllEmailFromCityInhabitants(String city) {
    List<String> emails = new ArrayList<>();
    Collection<Address> addresses = addressService.getAllByCity(city);

    if (addresses != null) {
      for (Address address : addresses) {
        Collection<Person> persons = address.getPersons();
        for (Person person : persons) {
          emails.add(person.getEmail());
        }
      }
      EmailListDTO emailListDTO = new EmailListDTO();
      emailListDTO.setEmailList(emails);
      emailListDTO.setCityName(city);
      return emailListDTO;
    } else {
      return null;
    }
  }

}
