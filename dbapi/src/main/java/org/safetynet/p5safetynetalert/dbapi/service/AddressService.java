package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.model.dto.*;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Service
public class AddressService {

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
    return addressRepository.findByRoadAndCityAndZipCode(
      address.getRoad(),
      address.getCity(),
      address.getZipCode()
    );
  }

  public Address getByRoad(final String road) {
    Address address = addressRepository.findByRoad(road);
    if (address == null) {
      LOGGER.debug("Address not found in DB.");
      return null;
    } else {
      return address;
    }
  }

  public boolean existsByRoad(String road) {
    return addressRepository.existsByRoad(road);
  }

  public boolean existsByRoadAndCityAndZipCode(Address address) {
    return addressRepository.existsByRoadAndCityAndZipCode(
      address.getRoad(),
      address.getCity(),
      address.getZipCode()
    );
  }

  public Address save(Address savedAddress) {
    return addressRepository.save(savedAddress);
  }

  public Collection<Address> getAllByCity(final String city) {
    if (city == null || city.equals("")) {
      return null;
    } else {
      return addressRepository.findAllByCity(city);
    }
  }

}
