package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.dto.*;
import org.safetynet.p5safetynetalert.dbapi.model.*;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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

  public Optional<Address> getAddress(final Integer id) {
    return addressRepository.findById(id);
  }

  public Iterable<Address> getAddresses() {
    return addressRepository.findAll();
  }

  public Address getByRoad(final String road) {
    return addressRepository.findByRoad(road);
  }

  public Collection<Address> getAllByCity(final String city) {
    if (city == null || city.equals("")) {
      return null;
    } else {
      return addressRepository.findAllByCity(city);
    }
  }

  public void deleteAddress(final Integer id) {
    addressRepository.deleteById(id);
  }

  public Address saveAddress(Address address) {
    Address savedAddress = addressRepository.save(address);
    return savedAddress;
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

  public Collection<PersonDTO> getPersonDTOsFromAddress(Address address) throws Exception {
    Collection<PersonDTO> listOfPersonsDTO =
      personService.getPersonDTOsFromAddress(address);
    return listOfPersonsDTO;
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
