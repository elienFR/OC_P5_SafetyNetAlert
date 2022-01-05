package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
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

  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private PersonsMedicationService personsMedicationService;
  @Autowired
  private PersonsAllergyService personsAllergyService;
  @Autowired
  private AgeService ageService;
  @Autowired
  private MedicalRecordsService medicalRecordsService;

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
    return addressRepository.findByRoad(road);
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

  public Collection<Person> getPersons(Address address){
    return address.getPersons();
  }

  public Collection<Person> getPersons(Collection<Address> addresses) {
    if (addresses == null || addresses.size() == 0) {
      return null;
    } else {
      Collection<Person> persons = new ArrayList<>();
      for (Address address : addresses) {
        persons.addAll(address.getPersons());
      }
      return persons;
    }
  }

}
