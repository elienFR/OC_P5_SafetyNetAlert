package org.safetynet.p5safetynetalert.dbapi.repository;

import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
  Address findByRoadAndCityAndZipCode(String road, String city, String zipCode);

  Address findByRoad(String road);

  Collection<Address> findAllByCity(String city);

  boolean existsByRoad(String road);

  boolean existsByRoadAndCityAndZipCode(String road, String city, String zipCode);
}
