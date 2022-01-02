package org.safetynet.p5safetynetalert.dbapi.service;

import com.sun.istack.NotNull;
import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.PersonsMedication;
import org.safetynet.p5safetynetalert.dbapi.model.dto.*;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private AgeService ageService;
  @Autowired
  private PersonsMedicationService personsMedicationService;
  @Autowired
  private PersonsAllergyService personsAllergyService;
  @Autowired
  private MedicalRecordsService medicalRecordsService;

  public Optional<Person> getPerson(final Integer id) {
    return personRepository.findById(id);
  }

  public Iterable<Person> getPersons() {
    return personRepository.findAll();
  }

  public Iterable<Person> getAllPersonsByFirstNameAndLastName(String firstName, String lastName) {
    return personRepository.findAllByFirstNameAndLastName(firstName, lastName);
  }

  public Iterable<Person> getAllPersonsByLastName(String lastName) {
    return personRepository.findAllByLastName(lastName);
  }

  public MedicalRecordsDTO getMedicalRecords(Person person) {
    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();

    Iterable<PersonsMedication> personsMedications = person.getPersonsMedications();

    List<String> medications = new ArrayList<>(
      personsMedicationService.getMedicationsFromPersonsMedications(
        person.getPersonsMedications()
      )
    );

    List<String> allergies = new ArrayList<>(
      personsAllergyService.getAllergiesFromPersonsMedications(
        person.getPersonsAllergies()
      )
    );

    medicalRecordsDTO.setMedications(medications);
    medicalRecordsDTO.setAllergies(allergies);
    return medicalRecordsDTO;
  }

  public Person savePerson(Person person) {
    Person savedPerson = personRepository.save(person);
    return savedPerson;
  }

  public int getAge(Person person) {
    return ageService.getAge(person.getBirthDate());
  }

  public String getEmail(Person person) {
    return person.getEmail();
  }

  public List<String> getEmails(Collection<Person> persons) {
    if (persons == null || persons.size() == 0) {
      return null;
    } else {
      List<String> emails = new ArrayList<>();
      for (Person person : persons) {
        emails.add(person.getEmail());
      }
      return emails;
    }
  }

  /**
   * This url returns the name, address, age, email address and medical history (drugs,
   * dosage, allergies) of each inhabitant. If more than one person has the same last name, they
   * all appear.
   *
   * @param firstName it is the first name of the person you desire the info
   * @param lastName  not null - not blank - it is the last name of the person you desire the info
   * @return an object PersonsInfoDTO (see description)
   */
  public PersonsInfoDTO getPersonInfoFromFirstAndOrLastName(
    String firstName,
    @NotNull @NotBlank String lastName
  ) {
    if ((firstName != null && lastName != null) || (!firstName.equals("") && !lastName.equals(""))) {
      Iterable<Person> persons;
      if (firstName == null || firstName.equals("")) {
        persons = getAllPersonsByLastName(lastName);
      } else {
        persons = getAllPersonsByFirstNameAndLastName(firstName, lastName);
      }

      List<PersonInfoDTO> personsInfoDTOToAdd = new ArrayList<>();
      for (Person person : persons) {
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();

        personInfoDTO.setFirstName(person.getFirstName());
        personInfoDTO.setLastName(person.getLastName());
        personInfoDTO.setAge(getAge(person)
        );
        personInfoDTO.setMail(person.getEmail());
        personInfoDTO.setMedicalRecords(
          getMedicalRecords(person)
        );

        personsInfoDTOToAdd.add(personInfoDTO);
      }

      PersonsInfoDTO personsInfoDTO = new PersonsInfoDTO();
      personsInfoDTO.setPersonsInfoDTO(personsInfoDTOToAdd);

      //checks if at least one person exists
      if (personsInfoDTOToAdd.size() > 0) {
        return personsInfoDTO;
      } else {
        return null;
      }
    } else {
      return new PersonsInfoDTO();
    }
  }

  public Collection<PersonForFireDTO> getPersonsForFireDTOFromAddressInFire(Collection<Person> persons) {
    List<PersonForFireDTO> personToAdd = new ArrayList<>();
    for (Person person : persons) {
      personToAdd.add(
        new PersonForFireDTO(
          person.getFirstName(),
          person.getLastName(),
          person.getPhone(),
          medicalRecordsService.getMedicalRecordsDTOFromPerson(person)
        ));
    }
    return personToAdd;
  }

  public Collection<PersonDTO> getPersonDTOsFromAddress(Address address) {
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

  public Collection<PersonDTO> getPersonDTOsFromAddresses(Collection<Address> addresses) {
    List<PersonDTO> listOfPersonsDTO = new ArrayList<>();
    for (Address address : addresses) {
      listOfPersonsDTO.addAll(getPersonDTOsFromAddress(address));
    }
    return listOfPersonsDTO;
  }

  private Collection<PersonDTO> getPersonDTOsFromPersons(Collection<Person> persons) {
    Collection<PersonDTO> personDTOCollection = new ArrayList<>();
    for (Person person : persons) {
      personDTOCollection.add(
        new PersonDTO(
          person.getFirstName(),
          person.getLastName(),
          person.getPhone(),
          person.getBirthDate(),
          new AddressDTO(
            person.getAddress().getRoad(),
            person.getAddress().getCity(),
            person.getAddress().getZipCode()
          )
        )
      );
    }
    return personDTOCollection;
  }

  private Collection<PersonDTO> getAdultsFromPersons(Collection<Person> persons) throws Exception {
    Collection<PersonDTO> personsDTOs = getPersonDTOsFromPersons(persons);
    for (PersonDTO personDTO : personsDTOs) {
      if (!ageService.isStrictlyOverEighteen(personDTO.getBirthDate())) {
        personsDTOs.remove(personDTO);
      }
    }
    return personsDTOs;
  }

  public Collection<PersonDTO> getAdultsFromPersonsDTOs(Collection<PersonDTO> personsDTOs) throws Exception {
    Collection<PersonDTO> adultsList = new ArrayList<>();
    for (PersonDTO personDTO : personsDTOs) {
      if (ageService.isStrictlyOverEighteen(personDTO.getBirthDate())) {
        adultsList.add(personDTO);
      }
    }
    return adultsList;
  }

  public Collection<ChildDTO> getChildrenFromPersonsDTOs(Collection<PersonDTO> personDTOs) throws Exception {
    Collection<ChildDTO> childrenList = new ArrayList<>();
    for (PersonDTO personDTO : personDTOs) {
      if (!ageService.isStrictlyOverEighteen(personDTO.getBirthDate())) {
        childrenList.add(new ChildDTO(
            personDTO.getFirstName(),
            personDTO.getLastName(),
            ageService.getAge(personDTO.getBirthDate())
          )
        );
      }
    }
    return childrenList;
  }

}