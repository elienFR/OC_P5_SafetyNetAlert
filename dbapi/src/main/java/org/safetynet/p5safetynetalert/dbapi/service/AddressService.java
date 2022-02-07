package org.safetynet.p5safetynetalert.dbapi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.dto.AddressDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AddressService implements IAddressService {

  private static final Logger LOGGER = LogManager.getLogger(AddressService.class);
  @Autowired
  private AddressRepository addressRepository;

  /**
   * This method get an Address object from DB according to its road, city and Zip Code.
   *
   * @param address is the address object looked in DB
   * @return the object from db (with its id)
   */
  public Address getByRoadAndCityAndZipCode(Address address) {
    LOGGER.debug("Retrieving address in DB.");
    return addressRepository.findByRoadAndCityAndZipCode(
      address.getRoad(),
      address.getCity(),
      address.getZipCode()
    );
  }

  /**
   * This method returns the address object from a String road
   *
   * @param road is a string of the road
   * @return an address Object
   */
  @Override
  public Address getByRoad(final String road) {
    LOGGER.debug("Requesting address...");
    Address address = addressRepository.findByRoad(road);
    if (address == null) {
      LOGGER.debug("Address not found in DB.");
      return null;
    } else {
      LOGGER.debug("Address properly requested.");
      return address;
    }
  }

  /**
   * This method returns a boolean, true if the road exists in addresses DB exists, false if it does
   * not.
   *
   * @param road is the string you look for in DB
   * @return A boolean. see description.
   */
  @Override
  public boolean existsByRoad(String road) {
    return addressRepository.existsByRoad(road);
  }

  /**
   * This method checks if an address exists by checking its road, its city and its zipcode
   *
   * @param address the address you want to check.
   * @return a boolean corresponding to existence. true = exists
   */
  public boolean existsByRoadAndCityAndZipCode(Address address) {
    LOGGER.debug("Checking address existence in DB.");
    return addressRepository.existsByRoadAndCityAndZipCode(
      address.getRoad(),
      address.getCity(),
      address.getZipCode()
    );
  }

  /**
   * This method saves a new address in database
   *
   * @param savedAddress is the address object to save
   * @return the address object saved.
   */
  @Override
  public Address save(Address savedAddress) {
    if (savedAddress == null) {
      LOGGER.debug("Address provided is null");
      return null;
    } else{
      LOGGER.debug("Address saved");
      return addressRepository.save(savedAddress);
    }
  }

  /**
   * This method returns a collection of Addresses object from a city String
   *
   * @param city is the string of the city.
   * @return A collection of Addresses object.
   */
  @Override
  public Collection<Address> getAllByCity(final String city) {
    LOGGER.debug("Loading All Addresses from city "+city);
    if (city == null || city.isBlank()) {
      LOGGER.debug("City is null or blank.");
      return null;
    } else {
      LOGGER.debug("All addresses properly requested in collection.");
      return addressRepository.findAllByCity(city);
    }
  }

  /**
   * This method converts an address into an addressDTO
   *
   * @param address is the address to be converted
   * @return is the output addressDTO
   */
  @Override
  public AddressDTO convertAddressToAddressDTO(Address address){
    AddressDTO addressDTO = new AddressDTO();
    addressDTO.setRoad(address.getRoad());
    addressDTO.setCity(address.getCity());
    addressDTO.setZip(address.getZipCode());

    return addressDTO;
  }

}
