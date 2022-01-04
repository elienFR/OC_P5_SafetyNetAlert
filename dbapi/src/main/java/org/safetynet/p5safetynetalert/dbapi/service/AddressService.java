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
  @Autowired
  private PersonService personService;


  public Address getByRoad(final String road) {
    return addressRepository.findByRoad(road);
  }

  public boolean existsByRoad(String road) {
    return addressRepository.existsByRoad(road);
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

  public Collection<PersonDTO> getAdultsDTO(Address address) throws Exception {
    Collection<PersonDTO> listOfAdults = personService
      .getAdultsFromPersonsDTOs(personService.getPersonDTOsFromAddress(address));
    return listOfAdults;
  }

  public Collection<ChildDTO> getChildrenDTO(Address address) throws Exception {
    Collection<ChildDTO> listOfChildren = personService
      .getChildrenFromPersonsDTOs(personService.getPersonDTOsFromAddress(address));

    return listOfChildren;
  }



}
