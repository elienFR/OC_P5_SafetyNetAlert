package org.safetynet.p5safetynetalert.dbapi.service;

import com.google.common.collect.Iterables;
import com.sun.istack.NotNull;
import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.entity.*;
import org.safetynet.p5safetynetalert.dbapi.model.dto.*;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
  @Autowired
  private AddressService addressService;

  /**
   * This method checks if a person already exists in the database according to its first name and
   * last name giving a person object.
   *
   * @param person is the person object to check in database
   * @return true if the person exists, and false if it does not.
   */
  public boolean existsByFirstNameAndLastName(Person person) {
    return personRepository.existsByFirstNameAndLastName(
      person.getFirstName(),
      person.getLastName()
    );
  }

  /**
   * This method checks if a person already exists in the database according to its first name and
   * last name.
   *
   * @param firstName is the person first name
   * @param lastName  is the person last name
   * @return true if the person exists, and false if it does not.
   */
  public boolean existsByFirstNameAndLastName(String firstName, String lastName) {
    return personRepository.existsByFirstNameAndLastName(
      firstName,
      lastName
    );
  }

  public Person save(Person person) {
    return personRepository.save(person);
  }

  public Person getByFirstNameAndLastName(String firstName, String lastName) {
    return personRepository.findByFirstNameAndLastName(firstName, lastName);
  }

  /**
   * Return an Iterable of Person that have the same last name
   *
   * @param lastName is the last name used to find persons
   * @return see description
   */
  public Iterable<Person> getAllByName(String lastName) {
    return personRepository.findAllByLastName(lastName);
  }

  private Iterable<Person> getAllPersonsByFirstNameAndLastName(String firstName, String lastName) {
    return personRepository.findAllByFirstNameAndLastName(firstName, lastName);
  }

  private Iterable<Person> getAllPersonsByLastName(String lastName) {
    return personRepository.findAllByLastName(lastName);
  }

  private int getAge(Person person) {
    return ageService.getAge(person.getBirthDate());
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

  public Collection<String> getPhones(Collection<Person> persons) {
    Collection<String> phoneCollection = new ArrayList<>();
    for (Person person : persons) {
      phoneCollection.add(
        person.getPhone()
      );
    }
    return phoneCollection;
  }

//  /**
//   * This method returns the name, address, age, email address and medical history (drugs,
//   * dosage, allergies) of each inhabitant. If more than one person has the same last name, they
//   * all appear.
//   *
//   * @param firstName it is the first name of the person you desire the info
//   * @param lastName  not null - not blank - it is the last name of the person you desire the info
//   * @return an object PersonsInfoDTO (see description)
//   */
//  public PersonsInfoDTO getPersonInfoFromFirstAndOrLastName(String firstName, @NotNull @NotBlank String lastName) {
//    if ((firstName != null && lastName != null) || (!firstName.equals("") && !lastName.equals(""))) {
//      Iterable<Person> persons;
//      if (firstName == null || firstName.isBlank()) {
//        persons = getAllPersonsByLastName(lastName);
//      } else {
//        persons = getAllPersonsByFirstNameAndLastName(firstName, lastName);
//      }
//
//      List<PersonInfoDTO> personsInfoDTOToAdd = new ArrayList<>();
//      for (Person person : persons) {
//        PersonInfoDTO personInfoDTO = new PersonInfoDTO();
//
//        personInfoDTO.setFirstName(person.getFirstName());
//        personInfoDTO.setLastName(person.getLastName());
//        personInfoDTO.setAge(getAge(person)
//        );
//        personInfoDTO.setMail(person.getEmail());
//        personInfoDTO.setMedicalRecords(
//          medicalRecordsService.getMedicalRecords(person)
//        );
//
//        personsInfoDTOToAdd.add(personInfoDTO);
//      }
//
//      PersonsInfoDTO personsInfoDTO = new PersonsInfoDTO();
//      personsInfoDTO.setPersonsInfoDTO(personsInfoDTOToAdd);
//
//      //checks if at least one person exists
//      if (personsInfoDTOToAdd.size() > 0) {
//        return personsInfoDTO;
//      } else {
//        return null;
//      }
//    } else {
//      return new PersonsInfoDTO();
//    }
//  }



  public Collection<PersonForFireDTO> getPersonsForFireDTOFromAddressInFire(
    Collection<Person> persons) {
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
      AddressDTO addressDTO = new AddressDTO();
      addressDTO.setRoad(address.getRoad());
      addressDTO.setCity(address.getCity());
      addressDTO.setZip(address.getZipCode());

      PersonDTO personDTO = new PersonDTO();
      personDTO.setFirstName(person.getFirstName());
      personDTO.setLastName(person.getLastName());
      personDTO.setPhone(person.getPhone());
      personDTO.setBirthDate(person.getBirthDate());
      personDTO.setAddress(addressDTO);

      listOfPersonsDTO.add(personDTO);
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

  public Collection<PersonDTO> getAdultsFromPersonsDTOs(
    Collection<PersonDTO> personsDTOs) throws Exception {
    Collection<PersonDTO> adultsList = new ArrayList<>();
    for (PersonDTO personDTO : personsDTOs) {
      if (ageService.isStrictlyOverEighteen(personDTO.getBirthDate())) {
        adultsList.add(personDTO);
      }
    }
    return adultsList;
  }

  public Collection<ChildDTO> getChildrenFromPersonsDTOs(
    Collection<PersonDTO> personDTOs) throws Exception {
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

  public Collection<PersonForFloodDTO> getPersonsForFlood(
    Collection<Address> addresses) {
    Collection<PersonForFloodDTO> personsToReturn = new ArrayList<>();

    for (Address address : addresses) {
      Collection<Person> personsToAdd = address.getPersons();
      personsToReturn.addAll(
        convertPersonsToPersonForFloodDTOs(personsToAdd)
      );
    }

    return personsToReturn;
  }

  private Collection<PersonForFloodDTO> convertPersonsToPersonForFloodDTOs(
    Collection<Person> persons) {
    Collection<PersonForFloodDTO> personForFloodDTOCollection = new ArrayList<>();
    for (Person person : persons) {
      personForFloodDTOCollection.add(
        convertPersonToPersonForFloodDTO(person)
      );
    }
    return personForFloodDTOCollection;
  }

  public Collection<PersonDTO> getAdultsDTO(Address address) throws Exception {
    Collection<PersonDTO> listOfAdults = getAdultsFromPersonsDTOs(getPersonDTOsFromAddress(address));
    return listOfAdults;
  }

  public Collection<ChildDTO> getChildrenDTO(Address address) throws Exception {
    Collection<ChildDTO> listOfChildren = getChildrenFromPersonsDTOs(getPersonDTOsFromAddress(address));

    return listOfChildren;
  }

  public Collection<Person> getPersonsFromAddress(Address address) {
    return address.getPersons();
  }

  public Collection<Person> getPersonsFromAddresses(Collection<Address> addresses) {
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

  private PersonForFloodDTO convertPersonToPersonForFloodDTO(Person person) {
    PersonForFloodDTO personForFloodDTO = new PersonForFloodDTO();

    personForFloodDTO.setFirstName(person.getFirstName());
    personForFloodDTO.setLastName(person.getLastName());
    personForFloodDTO.setPhone(person.getPhone());
    personForFloodDTO.setAge(ageService.getAge(person.getBirthDate()));
    personForFloodDTO.setMedicalRecords(
      medicalRecordsService.getMedicalRecords(person)
    );

    return personForFloodDTO;
  }

  /**
   * Convert a JsonPerson into a Person Object. For the purpose of the exercise the address given
   * is always in Culver with zip code 97451 and the birthdate is null (provided by a medicalrecord
   * object).
   *
   * @param jsonPerson is the JsonPerson Object to convert
   * @return a Peron object with null birthdate and an address located in Culver 97451.
   */
  private Person convertJsonPersonIntoPerson(JsonPerson jsonPerson) {
    Person person = new Person();
    person.setFirstName(jsonPerson.getFirstName());
    person.setLastName(jsonPerson.getLastName());
    person.setPhone(jsonPerson.getPhone());
    person.setEmail(jsonPerson.getEmail());
    person.setBirthDate(null);

    Address addressToSet = new Address();
    addressToSet.setRoad(jsonPerson.getAddress());
    addressToSet.setCity(jsonPerson.getCity());
    addressToSet.setZipCode(jsonPerson.getZip());
    person.setAddress(addressToSet);
    return person;
  }

  /**
   * This method create a Person into the database from a JsonPerson.
   * This Person has a null birthdate and an address always located in culver,
   * that is because of the properties of a JsonPerson. See convertJsonPersonIntoPerson method for
   * more information about this.
   * This method also saves the address if it does not exist already.
   * And it checks if the person already exists (according to first and last name)
   *
   * @param newJsonPerson is the JsonPerson to add into DB.
   * @return the save JsonPerson
   */
  public JsonPerson createPerson(JsonPerson newJsonPerson) {
    Person person = convertJsonPersonIntoPerson(newJsonPerson);
    if (person.getFirstName() != null && person.getLastName() != null
      && !person.getFirstName().isBlank() && !person.getLastName().isBlank()) {
      //check that person does not
      if (!existsByFirstNameAndLastName(person)) {
        //check that address does not exist
        if (!addressService.existsByRoadAndCityAndZipCode(person.getAddress())) {
          addressService.save(person.getAddress());
        } else {
          person.setAddress(
            addressService.getByRoadAndCityAndZipCode(person.getAddress())
          );
        }
        save(person);
        return newJsonPerson;
      } else {
        return null;
        //todo : throw exception to say person already exists
      }
    } else {
      return null;
      //todo : throw a exception to stipulate to furnish at least a first name AND a last name.
    }
  }

  /**
   * This method update an existing person. Ii checks if the person already exists. If it does not
   * it will return null. If it does, it will update it with the information found in putJsonPerson
   * parameter.
   *
   * @param putJsonPerson is the JsonPerson you want to update
   * @return null or a JsonPersonObject which has been updated. See description.
   */
  public JsonPerson updatePersonWithJsonPerson(JsonPerson putJsonPerson) {
    //if the person to update exists.
    if (existsByFirstNameAndLastName(convertJsonPersonIntoPerson(putJsonPerson))) {
      //Recover person from DB
      Person personToUpdate = getByFirstNameAndLastName(
        putJsonPerson.getFirstName(), putJsonPerson.getLastName());

      personToUpdate.setPhone(putJsonPerson.getPhone());
      personToUpdate.setEmail(putJsonPerson.getEmail());

      Address addressToUpdate = new Address();
      addressToUpdate.setRoad(putJsonPerson.getAddress());
      addressToUpdate.setCity(putJsonPerson.getCity());
      addressToUpdate.setZipCode(putJsonPerson.getZip());
      //check address difference
      if (!addressService.existsByRoadAndCityAndZipCode(addressToUpdate)
      ) {
        addressToUpdate = addressService.save(addressToUpdate);
      } else {
        addressToUpdate.setId(addressService.getByRoadAndCityAndZipCode(addressToUpdate).getId());
      }
      personToUpdate.setAddress(addressToUpdate);

      save(personToUpdate);
      return putJsonPerson;
    } else {
      return null;
    }
  }

  /**
   * This method deletes a person from database. We consider a person is unique by
   * its first name and last name combination.
   *
   * @param jsonPerson is the deserialized jsonObject
   * @return the jsonObject of deleted person if method is executed successfully.
   */
  public JsonPerson delete(JsonPerson jsonPerson) {
    if (jsonPerson.getFirstName() != null && jsonPerson.getLastName() != null) {
      if (existsByFirstNameAndLastName(convertJsonPersonIntoPerson(jsonPerson))) {
        Person personToDelete = personRepository
          .findByFirstNameAndLastName(jsonPerson.getFirstName(), jsonPerson.getLastName());

        personsAllergyService.delete(personToDelete.getPersonsAllergies());
        personsMedicationService.delete(personToDelete.getPersonsMedications());
        personRepository.delete(personToDelete);

        return jsonPerson;
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public Person updateBirthDateFromJsonMedicalRecords(JsonMedicalRecord jsonMedicalRecord) {
    Person personConcerned = getByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName());

    if (!jsonMedicalRecord.getBirthdate().equals(personConcerned.getBirthDate()) && !jsonMedicalRecord.getBirthdate().isBlank()) {
      personConcerned.setBirthDate(jsonMedicalRecord.getBirthdate());
      personConcerned = save(personConcerned);
    }
    return personConcerned;
  }

  private Person deleteBirthDateFromJsonMedicalRecords(JsonMedicalRecord jsonMedicalRecord) {
    Person personConcerned = getByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName());
    personConcerned.setBirthDate(null);
    personConcerned = save(personConcerned);
    return personConcerned;
  }

  public JsonMedicalRecord createMedicalRecords(JsonMedicalRecord jsonMedicalRecord) {
    if (existsByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName())) {

      Person personConcerned = getByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName());
      Iterable<String> medicationsFromPerson = medicalRecordsService.getMedicalRecords(personConcerned).getMedications();
      Iterable<String> allergiesFromPerson = medicalRecordsService.getMedicalRecords(personConcerned).getAllergies();

      //if there is no record we create it, else we drop the call.
      if (!medicalRecordsService.existsFromPerson(personConcerned)) {

        //Update birthdate
        personConcerned = updateBirthDateFromJsonMedicalRecords(jsonMedicalRecord);

        //write new medications
        if (Iterables.size(medicationsFromPerson) == 0) {
          medicalRecordsService.createMedicationsFromJsonPerson(jsonMedicalRecord, personConcerned);
        }

        //write new allergies
        if (Iterables.size(allergiesFromPerson) == 0) {
          medicalRecordsService.createAllergiesFromJsonPerson(jsonMedicalRecord, personConcerned);
        }

        return jsonMedicalRecord;

      } else {
        return null;
      }
    } else {
      return null;
    }
  }



  public JsonMedicalRecord updateMedicalRecords(JsonMedicalRecord jsonMedicalRecord) {
    if (existsByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName())) {
      Person personConcerned;

      //update birthdate
      personConcerned = updateBirthDateFromJsonMedicalRecords(jsonMedicalRecord);

      //delete existing medication
      medicalRecordsService.deletePersonsMedicationsFromPerson(personConcerned);
      //update medications
      medicalRecordsService.createMedicationsFromJsonPerson(jsonMedicalRecord, personConcerned);
      //delete existing allergies
      medicalRecordsService.deletePersonsAllergiesFromPerson(personConcerned);
      //update allergies
      medicalRecordsService.createAllergiesFromJsonPerson(jsonMedicalRecord, personConcerned);

      return jsonMedicalRecord;

    } else {
      return null;
    }
  }

  public JsonMedicalRecord deleteMedicalRecords(JsonMedicalRecord jsonMedicalRecord) {
    if (existsByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName())) {
      Person personConcerned;

      //delete birthdate
      personConcerned = deleteBirthDateFromJsonMedicalRecords(jsonMedicalRecord);
      //delete existing medication
      medicalRecordsService.deletePersonsMedicationsFromPerson(personConcerned);
      //delete existing allergies
      medicalRecordsService.deletePersonsAllergiesFromPerson(personConcerned);

      MedicalRecordsDTO deletedMedicalRecordsDTO = medicalRecordsService.getMedicalRecords(personConcerned);

      List<String> deletedMedications = new ArrayList<>();
//      deletedMedications.add("");
//      for(String medication : deletedMedicalRecordsDTO.getMedications()){
//        deletedMedications.add(medication);
//      }

      List<String> deletedAllergies = new ArrayList<>();
//      deletedAllergies.add("");
//      for(String allergies : deletedMedicalRecordsDTO.getAllergies()){
//        deletedAllergies.add(allergies);
//      }

      jsonMedicalRecord.setBirthdate("");
      jsonMedicalRecord.setMedications(deletedMedications);
      jsonMedicalRecord.setAllergies(deletedAllergies);

      return jsonMedicalRecord;

    } else {
      return null;
    }
  }


}