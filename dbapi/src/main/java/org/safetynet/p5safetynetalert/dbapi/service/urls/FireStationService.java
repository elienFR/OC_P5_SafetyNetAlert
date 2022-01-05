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

  /**
   * Return a fireStation object according to its number in DB.
   *
   * @param number the number of the Firestation
   * @return see description
   */
  private FireStation getByNumber(final String number) {
    return fireStationRepository.findByNumber(number);
  }

  /**
   * Save a FireStation in DB
   *
   * @param fireStation is the object to be saved.
   * @return the saved object.
   */
  public FireStation save(FireStation fireStation) {
    return fireStationRepository.save(fireStation);
  }

  /**
   * Check if a fire station already exists with a given number. True means exists and false means
   * it does not.
   *
   * @param number is the number to be checked
   * @return a boolean with true for existence and false for nonexistence.
   */
  public boolean existsByNumber(String number) {
    return fireStationRepository.existsByNumber(number);
  }

  /**
   * This method returns a PersonsFromFireStationDTO covered by the corresponding fire station.
   * If fire station is null then it returns a null object.
   * If fire station is properly recorded it returns the PersonsFromFireStationDTO that contains
   * a list of persons covered by the fire station and the number of adult and children.
   *
   * @param number is the number of the fire station
   * @return a PersonsFromFireStationDTO if method is successful. null if it is not.
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

  /**
   * This method adds a mapping between an address and a fire station. The jsonFireStation object may have
   * no fire station registered or a null one. If so, then only an address is created.
   * And for the purpose of the exercise, this address is only composed of the road string.
   * The city and the zip code are hard coded to refer to Culver 97541.
   *
   * @param jsonFireStation is the posted object composed of road string
   *                        and fire station number to map.
   * @return the jsonFireStation object furnished if methods is successful, and null if any error occurs.
   */
  public JsonFireStation saveAddressFireStationMapping(JsonFireStation jsonFireStation) {
    //We consider address/road MUST not be null or blank here
    String fireStationNumber = jsonFireStation.getStation();
    String road = jsonFireStation.getAddress();

    // If a proper address road is registered in json object
    if (road != null && !road.isBlank()) {
      Address savedAddress;
      if (addressService.existsByRoad(road)) {
        savedAddress = addressService.getByRoad(road);
      } else {
        savedAddress = new Address(jsonFireStation.getAddress(), "Culver", "97451", null);
      }

      // If a proper fire station is registered in json object
      if (fireStationNumber != null && !fireStationNumber.isBlank()) {
        if (existsByNumber(fireStationNumber)) {
          savedAddress.setFireStation(
            getByNumber(fireStationNumber)
          );
        } else {
          FireStation newFireStation = save(new FireStation(fireStationNumber));
          savedAddress.setFireStation(newFireStation);
        }
      } else { //Is similar to create a new address without fire station.
        savedAddress.setFireStation(null);
      }

      addressService.save(savedAddress);

      return jsonFireStation;
    } else {
      return null;
    }
  }

  /**
   * This method updates the mapping between an address and its fire station.
   * If the address (road) does not exist in database, then it returns a null object.
   * If the fire station does not exist in DB, then it creates it and update the mapping.
   *
   * @param jsonFireStation is the posted object composed of road string
   *                        and fire station number to map.
   * @return A null object if the address given does not exist. Or return the given jsonFireStation
   * Object if method was successful.
   */
  public JsonFireStation updateAddressFireStationMapping(JsonFireStation jsonFireStation) {
    String road = jsonFireStation.getAddress();
    String fireStationNumber = jsonFireStation.getStation();
    FireStation fireStationToUpdate = null;

    //If the fire station has properly been filled
    if (fireStationNumber != null && !fireStationNumber.isBlank()) {
      //If it does not already exist in DB
      if (!existsByNumber(fireStationNumber)) {
        fireStationToUpdate = save(new FireStation(fireStationNumber));
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

  /**
   * This method deletes the mapping between an address and its mapped fire station.
   * If the address given does not exit it returns a null object. If it is executed successfully
   * then it returns the new mapping that is an address mapped to null fire station.
   *
   * @param jsonFireStation is the posted object composed of road string
   *                        and fire station number to map.
   * @return Null if the address is wrong. A JsonFireStation with a null fire station if executed
   * successfully.
   */
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
