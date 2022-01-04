package org.safetynet.p5safetynetalert.dbapi.service.urls;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonForFloodDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PhonesDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.FireStation;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.dbapi.repository.FireStationRepository;
import org.safetynet.p5safetynetalert.dbapi.service.AddressService;
import org.safetynet.p5safetynetalert.dbapi.service.AgeService;
import org.safetynet.p5safetynetalert.dbapi.service.PersonService;
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

  private FireStation getByNumber(final String number) {
    return fireStationRepository.findByNumber(number);
  }

  public FireStation save(FireStation fireStation) {
    return fireStationRepository.save(fireStation);
  }

  public boolean existsByNumber(String number) {
    return fireStationRepository.existsByNumber(number);
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
  public PersonsFromFireStationDTO getPersonsAndCount(String number) {
    FireStation fireStation = getByNumber(number);
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

  /**
   * This returns a Collection of PersonsFloodDTO from a fireStation number
   *
   * @param number is the number of the station
   * @return A Collection of PersonFloodDTO
   */
  public Collection<PersonForFloodDTO> getPersonsForFlood(String number) {
    FireStation fireStation = getByNumber(number);
    if (fireStation != null) {
      Collection<PersonForFloodDTO> personForFloodDTOCollection =
        personService.getPersonsForFlood(fireStation.getAddresses());
      return personForFloodDTOCollection;
    } else {
      return null;
    }
  }

  /**
   * This method extracts an Iterable of String containing all the phone numbers possessed by people
   * indirectly connected to a fire station.
   *
   * @param number It is the number of the fire station.
   * @return A PhonesDTO Object, see description.
   */
  public PhonesDTO getPhonesFromFireStationNumber(String number) {
    FireStation fireStation = getByNumber(number);
    if (fireStation != null) {
      Collection<String> phoneNumbers =
        personService.getPhones(
          addressService.getPersons(
            fireStation.getAddresses()
          )
        );
      PhonesDTO phonesDTO = new PhonesDTO();
      phonesDTO.setPhonesList(phoneNumbers);
      return phonesDTO;
    } else {
      return null;
    }
  }

  public JsonFireStation saveAddressFireStationMapping(JsonFireStation jsonFireStation) {
    FireStation savedFireStation = new FireStation(jsonFireStation.getStation());
    Address savedAddress = new Address(jsonFireStation.getAddress(), "Culver", "97451", savedFireStation);
    FireStation fireStationInDB = getByNumber(jsonFireStation.getStation());
    if (fireStationInDB == null) {
      save(savedFireStation);
    } else {
      savedAddress.setFireStation(fireStationInDB);
    }
    Address addressInDB = addressService.getByRoad(jsonFireStation.getAddress());
    if (addressInDB == null) {
      addressService.save(savedAddress);
    }
    return jsonFireStation;
  }

  public JsonFireStation updateAddressFireStationMapping(JsonFireStation jsonFireStation) {
    String road = jsonFireStation.getAddress();
    String fireStationNumber = jsonFireStation.getStation();
    FireStation fireStationToUpdate = null;

    //If the fire station has properly been filled
    if (fireStationNumber != null) {
      if (!fireStationNumber.isBlank()) {
        //If it does not already exists in DB
        if (!existsByNumber(fireStationNumber)) {
          fireStationToUpdate = save(new FireStation(fireStationNumber));
        }
      }
    } else {
      return null;
    }

    if (road != null) {
      if (addressService.existsByRoad(road)) {
        Address updatedAddress = addressService.getByRoad(road);
        updatedAddress.setFireStation(fireStationToUpdate);
        addressService.save(updatedAddress);
      } else {
        return null;
      }
      return jsonFireStation;
    } else {
      return null;
    }
  }


  public JsonFireStation eraseAddressFireStationMapping(JsonFireStation jsonFireStation) {
    String road = jsonFireStation.getAddress();

    if (addressService.existsByRoad(road)) {
      Address addressToUnMap = addressService.getByRoad(road);
      addressToUnMap.setFireStation(null);
      addressService.save(addressToUnMap);

      jsonFireStation.setStation(null);
      return jsonFireStation;
    } else {
      return null;
    }
  }
}
