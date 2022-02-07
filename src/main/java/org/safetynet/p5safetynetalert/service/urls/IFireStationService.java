package org.safetynet.p5safetynetalert.service.urls;

import org.safetynet.p5safetynetalert.model.dto.PersonForFloodDTO;
import org.safetynet.p5safetynetalert.model.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.model.dto.PhonesDTO;
import org.safetynet.p5safetynetalert.model.initPersist.JsonFireStation;

import java.util.Collection;

public interface IFireStationService {

  /**
   * This method returns a PersonsFromFireStationDTO covered by the corresponding fire station.
   * If fire station is null then it returns a null object.
   * If fire station is properly recorded it returns the PersonsFromFireStationDTO that contains
   * a list of persons covered by the fire station and the number of adult and children.
   *
   * @param id is the number of the fire station
   * @return a PersonsFromFireStationDTO if method is successful. null if it is not.
   */
  PersonsFromFireStationDTO getPersonsAndCount(String id);

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
  JsonFireStation saveAddressFireStationMapping(JsonFireStation jsonFireStation);

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
  JsonFireStation updateAddressFireStationMapping(JsonFireStation jsonFireStation);

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
  JsonFireStation eraseAddressFireStationMapping(JsonFireStation jsonFireStation);

  /**
   * This method extracts an Iterable of String containing all the phone numbers possessed by people
   * indirectly connected to a fire station.
   *
   * @param number It is the number of the fire station.
   * @return A PhonesDTO Object, see description.
   */
  PhonesDTO getPhonesFromFireStationNumber(String number);

  /**
   * This returns a Collection of PersonsFloodDTO from a fireStation number
   *
   * @param number is the number of the station
   * @return A Collection of PersonFloodDTO
   */
  Collection<PersonForFloodDTO> getPersonsForFlood(String number);
}
