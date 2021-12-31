package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.dto.FireDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.MedicalRecordsDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonForFireDTO;
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


  public FireDTO getPersonFromAddressInFire(String road) {
    List<PersonForFireDTO> personToAdd = new ArrayList<>();
    Address address = findByRoad(road);

    Collection<Person> persons = address.getPersons();
    for (Person person : persons) {
      List<String> medicationsListToAdd = new ArrayList<>();
      List<String> allergiesToAdd = new ArrayList<>();
      MedicalRecordsDTO medicalRecordsDTOs = new MedicalRecordsDTO();

      Iterable<PersonsMedication> personsMedications = person.getPersonsMedications();
      for (PersonsMedication personsMedication : personsMedications) {
        medicationsListToAdd.add(personsMedication.getMedication().getName());
      }

      Iterable<PersonsAllergy> personsAllergies = person.getPersonsAllergies();
      for (PersonsAllergy personsAllergy : personsAllergies) {
        allergiesToAdd.add(personsAllergy.getAllergy().getName());
      }

      medicalRecordsDTOs.setMedications(medicationsListToAdd);
      medicalRecordsDTOs.setAllergies(allergiesToAdd);

      personToAdd.add(
          new PersonForFireDTO(
              person.getFirstName(),
              person.getLastName(),
              person.getPhone(),
              medicalRecordsDTOs
          ));
    }


    FireDTO fireDTO = new FireDTO();
    fireDTO.setFireStationNumber(address.getFireStation().getNumber());
    fireDTO.setPersonsList(personToAdd);
    return fireDTO;
  }
}
