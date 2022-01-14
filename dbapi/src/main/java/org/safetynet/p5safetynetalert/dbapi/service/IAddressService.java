package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;

import java.util.Collection;

public interface IAddressService {

  /**
   * This method returns the address object from a String road
   *
   * @param road is a string of the road
   * @return an address Object
   */
  Address getByRoad(String road);

  /**
   * This method returns a collection of Addresses object from a city String
   *
   * @param city is the string of the city.
   * @return A collection of Addresses object.
   */
  Collection<Address> getAllByCity(String city);

  /**
   * This method returns a boolean, true if the road exists in addresses DB exists, false if it does
   * not.
   *
   * @param road is the string you look for in DB
   * @return A boolean. see description.
   */
  boolean existsByRoad(String road);

  /**
   * This method saves a new address in database
   *
   * @param savedAddress is the address object to save
   * @return the address object saved.
   */
  Address save(Address addressToUnMap);
}