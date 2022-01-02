package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.dto.*;
import org.safetynet.p5safetynetalert.dbapi.model.*;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
  private AgeCalculatorService ageCalculatorService;
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

  public Address findByRoad(final String road) {
    return addressRepository.findByRoad(road);
  }

  public Collection<Address> getAllByCity(final String city) {
    return addressRepository.findAllByCity(city);
  }

  public void deleteAddress(final Integer id) {
    addressRepository.deleteById(id);
  }

  public Address saveAddress(Address address) {
    Address savedAddress = addressRepository.save(address);
    return savedAddress;
  }

  public Collection<Person> getPersons(Collection<Address> addresses) {
    Collection<Person> persons = new ArrayList<>();
    for (Address address : addresses) {
      persons.addAll(address.getPersons());
    }
    return persons;
  }

  public List<ChildDTO> getChildrenDTO(Address address) throws Exception {
    List<ChildDTO> listOfChildren = new ArrayList<>();
    Collection<Person> persons = address.getPersons();
    for (Person person : persons) {
      if (!ageCalculatorService.isStrictlyOverEighteen(person.getBirthDate())) {
        listOfChildren.add(
            new ChildDTO(
                person.getFirstName(),
                person.getLastName(),
                ageCalculatorService.getAge(person.getBirthDate())
            )
        );
      }
    }
    return listOfChildren;
  }

  public List<PersonDTO> getPersonsDTO(Address address) throws Exception {
    List<PersonDTO> listOfPersonsDTO = new ArrayList<>();
    Collection<Person> persons = address.getPersons();
    for (Person person : persons) {
      listOfPersonsDTO.add(
          new PersonDTO(
              person.getFirstName(),
              person.getLastName(),
              person.getPhone(),
              person.getBirthDate(),
              new AddressDTO(
                  address.getRoad(),
                  address.getCity(),
                  address.getZipCode()
              )
          )
      );
    }
    return listOfPersonsDTO;
  }

  public List<PersonDTO> getAdultsDTO(Address address) throws Exception {
    List<PersonDTO> listOfPersonsDTO = new ArrayList<>();
    Collection<Person> persons = address.getPersons();
    for (Person person : persons) {
      if (ageCalculatorService.isStrictlyOverEighteen(person.getBirthDate())) {
        listOfPersonsDTO.add(
            new PersonDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getPhone(),
                person.getBirthDate(),
                new AddressDTO(
                    address.getRoad(),
                    address.getCity(),
                    address.getZipCode()
                )
            )
        );
      }
    }
    return listOfPersonsDTO;
  }

  public FireDTO getPersonFromAddressInFire(String road) {
    Address address = findByRoad(road);

    Collection<PersonForFireDTO> personToAdd =
        personService.getPersonsFromAddressInFire(
            address.getPersons()
        );

    FireDTO fireDTO = new FireDTO();
    fireDTO.setFireStationNumber(address.getFireStation().getNumber());
    fireDTO.setPersonsList(personToAdd);
    return fireDTO;
  }
}
