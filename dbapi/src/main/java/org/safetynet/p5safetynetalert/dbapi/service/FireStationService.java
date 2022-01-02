package org.safetynet.p5safetynetalert.dbapi.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.dto.AddressDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PhonesDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.FireStation;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@Service
public class FireStationService {

  @Autowired
  private FireStationRepository fireStationRepository;
  @Autowired
  private AgeService ageService;
  @Autowired
  private AddressService addressService;
  @Autowired
  private PersonService personService;

  private FireStation getFireStationByNumber(final String number) {
    return fireStationRepository.findByNumber(number);
  }

  /**
   * This method should return a list of people covered by the corresponding fire station.
   * So, if the station number = 1, it must return the inhabitants covered by the station number 1.
   * The list includes the following specific information: first name, last name, address,
   * telephone number.
   * It provides a count of adults, and a count of children (anyone aged 18 or over less)
   * in the service area.
   *
   * @param number is the number of the fire station
   * @return see description
   */
  public PersonsFromFireStationDTO getPersonsFromFireStationNumber(String number) {
    FireStation fireStation = getFireStationByNumber(number);
    if (fireStation != null) {
      Collection<PersonDTO> personsList = personService.getPersonDTOsFromAddresses(
        fireStation.getAddresses()
      );

      Integer adultCount = null;
      Integer childrenCount = null;
      try {
        adultCount = ageService.countAdultsAndChildren(personsList).get("adults");
        childrenCount = ageService.countAdultsAndChildren(personsList).get("children");
      } catch (Exception e) {
        e.printStackTrace();
      }

      PersonsFromFireStationDTO listOfPersons = new PersonsFromFireStationDTO();
      listOfPersons.setPersonsList(personsList);
      listOfPersons.setAdultCount(adultCount);
      listOfPersons.setChildrenCount(childrenCount);

      return listOfPersons;
    } else {
      return null;
    }
  }


  public PhonesDTO getPhonesFromFireStationNumber(String number) {
    Set<String> phonesToAdd = new TreeSet<>();

    FireStation fireStation = getFireStationByNumber(number);
    if (fireStation != null) {
      Collection<Address> addresses = fireStation.getAddresses();
      for (Address address : addresses) {
        Collection<Person> persons = address.getPersons();
        for (Person person : persons) {
          String phoneToAdd = person.getPhone();
          if (!phonesToAdd.contains(phoneToAdd)) {
            phonesToAdd.add(person.getPhone());
          }
        }
      }
      PhonesDTO phonesDTO = new PhonesDTO();
      phonesDTO.setPhonesList(phonesToAdd);
      return phonesDTO;
    } else {
      return null;
    }

  }
}
