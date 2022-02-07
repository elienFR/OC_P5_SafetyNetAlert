package org.safetynet.p5safetynetalert.service;

import com.google.common.collect.Iterables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.dto.*;
import org.safetynet.p5safetynetalert.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.repository.PersonRepository;
import org.safetynet.p5safetynetalert.model.entity.Address;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class PersonService implements IPersonService {

  private static final Logger LOGGER = LogManager.getLogger(PersonService.class);
  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private AgeService ageService;
  @Autowired
  private IPersonsMedicationService iPersonsMedicationService;
  @Autowired
  private IPersonsAllergyService iPersonsAllergyService;
  @Autowired
  private IMedicalRecordsService iMedicalRecordsService;
  @Autowired
  private IAddressService iAddressService;

  /**
   * This method checks if a person already exists in the database according to its first name and
   * last name giving a person object.
   *
   * @param person is the person object to check in database
   * @return true if the person exists, and false if it does not.
   */
  private boolean existsByFirstNameAndLastName(Person person) {
    LOGGER.debug("Finding person id DB...");
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
  private boolean existsByFirstNameAndLastName(String firstName, String lastName) {
    LOGGER.debug("Finding person id DB...");
    return personRepository.existsByFirstNameAndLastName(
      firstName,
      lastName
    );
  }

  /**
   * This method saves a person in DB.
   *
   * @param person is the person you want to save.
   * @return the person saved in DB.
   */
  private Person save(Person person) {
    return personRepository.save(person);
  }


  private Collection<PersonDTO> getPersonDTOsFromAddress(Address address) {
    List<PersonDTO> listOfPersonsDTO = new ArrayList<>();
    Collection<Person> persons = address.getPersons();
    for (Person person : persons) {
      AddressDTO addressDTO = iAddressService.convertAddressToAddressDTO(address);

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

  private Collection<PersonDTO> getAdultsFromPersonsDTOs(
    Collection<PersonDTO> personsDTOs) {
    Collection<PersonDTO> adultsList = new ArrayList<>();
    for (PersonDTO personDTO : personsDTOs) {
      if (ageService.isStrictlyOverEighteen(personDTO.getBirthDate())) {
        adultsList.add(personDTO);
      }
    }
    return adultsList;
  }

  private Collection<ChildDTO> getChildrenFromPersonsDTOs(
    Collection<PersonDTO> personDTOs) {
    Collection<ChildDTO> childrenList = new ArrayList<>();
    for (PersonDTO personDTO : personDTOs) {
      if (!ageService.isStrictlyOverEighteen(personDTO.getBirthDate())) {
        ChildDTO childDTOToAdd = new ChildDTO();
        childDTOToAdd.setFirstName(personDTO.getFirstName());
        childDTOToAdd.setLastName(personDTO.getLastName());
        childDTOToAdd.setAge(ageService.getAge(personDTO.getBirthDate()));
        childrenList.add(childDTOToAdd);
      }
    }
    return childrenList;
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

  /**
   * This method converts a Person object into a PersonForFloodDTO Object
   *
   * @param person A Person object to be converted.
   * @return A PersonForFloodDTO Object from a Peron Object.
   */
  private PersonForFloodDTO convertPersonToPersonForFloodDTO(Person person) {
    PersonForFloodDTO personForFloodDTO = new PersonForFloodDTO();
    personForFloodDTO.setFirstName(person.getFirstName());
    personForFloodDTO.setLastName(person.getLastName());
    personForFloodDTO.setPhone(person.getPhone());
    personForFloodDTO.setAge(ageService.getAge(person.getBirthDate()));
    personForFloodDTO.setMedicalRecords(
      iMedicalRecordsService.getMedicalRecords(person)
    );

    return personForFloodDTO;
  }

  /**
   * Convert a JsonPerson into a Person Object. For the purpose of the exercise the address given
   * is always in Culver with zip code 97451 and the birthdate is null (provided by a medicalRecord
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
   * Update the birthdate of a person to new values in DB from the person, and the birthdate found
   * in a jsonMedicalRecord object.
   *
   * @param jsonMedicalRecord A jsonMedicalRecord object used to find the person in DB.
   * @return A Person object with a null birthdate.
   */
  private Person updateBirthDateFromJsonMedicalRecords(JsonMedicalRecord jsonMedicalRecord) {
    Person personConcerned = getByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName());

    if (!jsonMedicalRecord.getBirthdate().equals(personConcerned.getBirthDate()) && !jsonMedicalRecord.getBirthdate().isBlank()) {
      personConcerned.setBirthDate(jsonMedicalRecord.getBirthdate());
      personConcerned = save(personConcerned);
    }
    return personConcerned;
  }

  /**
   * Set the birthdate of a person to null in DB from the person found in a jsonMedicalRecord object.
   *
   * @param jsonMedicalRecord A jsonMedicalRecord object used to find the person in DB.
   * @return A Person object with a null birthdate.
   */
  private Person deleteBirthDateFromJsonMedicalRecords(JsonMedicalRecord jsonMedicalRecord) {
    Person personConcerned = getByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName());
    personConcerned.setBirthDate(null);
    personConcerned = save(personConcerned);
    return personConcerned;
  }

  /**
   * This method returns a person from DB with its first name and its last name.
   *
   * @param firstName is the first name of the person you look for.
   * @param lastName  is the last name of the person you look for.
   * @return a Person object with corresponding first name and last name.
   */
  @Override
  public Person getByFirstNameAndLastName(String firstName, String lastName) {
    LOGGER.debug("Finding person id DB...");
    return personRepository.findByFirstNameAndLastName(firstName, lastName);
  }

  /**
   * Return an Iterable of Persons that have the same last name
   *
   * @param lastName is the last name used to find persons
   * @return see description
   */
  public Iterable<Person> getAllByName(String lastName) {
    LOGGER.debug("Finding persons id DB with their last name...");
    return personRepository.findAllByLastName(lastName);
  }

  /**
   * This method returns all the email contained in a collection of persons.
   *
   * @param persons is the collection of person
   * @return is a list of String containing emails.
   */
  @Override
  public List<String> getEmails(Collection<Person> persons) {
    LOGGER.debug("Getting email list from a collection of persons.");
    if (persons == null || persons.size() == 0) {
      LOGGER.debug("The collection of persons is null.");
      return null;
    } else {
      List<String> emails = new ArrayList<>();
      for (Person person : persons) {
        emails.add(person.getEmail());
      }
      LOGGER.debug("Emails properly got.");
      return emails;
    }
  }

  /**
   * This method returns a collection of string. Each one represent a phone number extracted from a
   * collection of Persons.
   *
   * @param persons is the collection of persons.
   * @return is the collection of phone' string.
   */
  @Override
  public Collection<String> getPhones(Collection<Person> persons) {
    LOGGER.debug("Creating a list of phones...");
    //we use HashSet so we do not get
    Collection<String> phoneCollection = new HashSet<>();
    for (Person person : persons) {
      phoneCollection.add(
        person.getPhone()
      );
    }
    LOGGER.debug("List of phones created.");
    return phoneCollection;
  }

  /**
   * This method convert a collection of PersonForFireDTO from a collection of persons
   *
   * @param persons is the collection of persons
   * @return is the collection of PersonForFireDTOs
   */
  @Override
  public Collection<PersonForFireDTO> convertPersonsInPersonForFireDTO(
    Collection<Person> persons) {
    LOGGER.debug("Loading collection of PersonForFireDTOs...");
    List<PersonForFireDTO> personsToAdd = new ArrayList<>();
    for (Person person : persons) {
      PersonForFireDTO personForFireDTOToAdd = new PersonForFireDTO();
      personForFireDTOToAdd.setFirstName(person.getFirstName());
      personForFireDTOToAdd.setLastName(person.getLastName());
      personForFireDTOToAdd.setPhone(person.getPhone());
      personForFireDTOToAdd.setMedicalRecords(iMedicalRecordsService.getMedicalRecords(person));
      personsToAdd.add(personForFireDTOToAdd);
    }
    LOGGER.debug("Collection of PersonForFireDTOs properly loaded.");
    return personsToAdd;
  }

  /**
   * This method loads a collection of persons from a collection of addresses.
   *
   * @param addresses is the collection of addresses.
   * @return a collection of persons.
   */
  @Override
  public Collection<PersonDTO> getPersonDTOsFromAddresses(Collection<Address> addresses) {
    LOGGER.debug("Loading the collection of PersonDTOs from the collection of addresses...");
    List<PersonDTO> listOfPersonsDTO = new ArrayList<>();
    for (Address address : addresses) {
      listOfPersonsDTO.addAll(getPersonDTOsFromAddress(address));
    }
    LOGGER.debug("Collection of PersonsDTOs properly loaded.");
    return listOfPersonsDTO;
  }

  /**
   * This method collects each Person from a collection of addresses and add them in a collection of
   * PersonForFloodDTOs.
   *
   * @param addresses is the collection of addresses
   * @return see description.
   */
  @Override
  public Collection<PersonForFloodDTO> getPersonsForFlood(
    Collection<Address> addresses) {
    LOGGER.debug("Creating the collection of PersonForFloodDTOs...");
    Collection<PersonForFloodDTO> personsToReturn = new ArrayList<>();

    for (Address address : addresses) {
      Collection<Person> personsToAdd = address.getPersons();
      personsToReturn.addAll(
        convertPersonsToPersonForFloodDTOs(personsToAdd)
      );
    }
    LOGGER.debug("Collection of PersonForFloodDTOs created.");
    return personsToReturn;
  }

  /**
   * This method returns a collection of adults DTO from an Address
   *
   * @param address is the address where to extract ChildrenDTO
   * @return A collection of PersonDTO
   */
  @Override
  public Collection<PersonDTO> getAdultsDTO(Address address) {
    return getAdultsFromPersonsDTOs(getPersonDTOsFromAddress(address));
  }

  /**
   * This method returns a collection of children DTO from an Address
   *
   * @param address is the address where to extract ChildrenDTO
   * @return A collection of ChildDTO
   */
  @Override
  public Collection<ChildDTO> getChildrenDTO(Address address) {
    return getChildrenFromPersonsDTOs(getPersonDTOsFromAddress(address));
  }

  /**
   * This method loads a collection of person form an address object.
   *
   * @param address is the object with which the collection of persons is created.
   * @return a collection of persons.
   */
  @Override
  public Collection<Person> getPersonsFromAddress(Address address) {
    LOGGER.debug("Loading persons form address...");
    Collection<Person> personCollection = address.getPersons();
    if (personCollection == null) {
      LOGGER.debug("Collection of Person is null.");
      return null;
    } else {
      LOGGER.debug("Persons properly loaded from address.");
      return personCollection;
    }
  }

  /**
   * This method returns a Collection of Person found in a specific address.
   *
   * @param addresses A Collection of addresses object used to get persons from.
   * @return A collection of persons from a collection of addresses.
   */
  @Override
  public Collection<Person> getPersonsFromAddresses(Collection<Address> addresses) {
    LOGGER.debug("Getting the collection of persons from the collection of addresses.");
    if (addresses == null || addresses.size() == 0) {
      LOGGER.debug("The collection of addresses given is null or does not contain any addresses.");
      return null;
    } else {
      Collection<Person> persons = new ArrayList<>();
      for (Address address : addresses) {
        persons.addAll(address.getPersons());
      }
      LOGGER.debug("The collection of persons has been properly extracted.");
      return persons;
    }
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
  @Override
  public JsonPerson createPerson(JsonPerson newJsonPerson) {
    LOGGER.debug("Saving a new person in DB...");
    Person person = convertJsonPersonIntoPerson(newJsonPerson);
    if (person.getFirstName() != null && person.getLastName() != null
      && !person.getFirstName().isBlank() && !person.getLastName().isBlank()) {
      //check that person does not
      if (!existsByFirstNameAndLastName(person)) {
        //check that address does not exist
        if (!iAddressService.existsByRoadAndCityAndZipCode(person.getAddress())) {
          iAddressService.save(person.getAddress());
        } else {
          person.setAddress(
            iAddressService.getByRoadAndCityAndZipCode(person.getAddress())
          );
        }
        save(person);
        LOGGER.debug("Person saved in DB.");
        return newJsonPerson;
      } else {
        LOGGER.debug("Person already exists in DB.");
        return null;
      }
    } else {
      LOGGER.error("There are no name and last name in jsonPerson object provided.");
      return null;
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
  @Override
  public JsonPerson updatePersonWithJsonPerson(JsonPerson putJsonPerson) {
    LOGGER.debug("Updating an existing person...");
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
      if (!iAddressService.existsByRoadAndCityAndZipCode(addressToUpdate)
      ) {
        LOGGER.debug("A new address is being saved.");
        addressToUpdate = iAddressService.save(addressToUpdate);
      } else {
        addressToUpdate.setId(iAddressService.getByRoadAndCityAndZipCode(addressToUpdate).getId());
      }
      personToUpdate.setAddress(addressToUpdate);

      save(personToUpdate);
      LOGGER.debug("Person updated.");
      return putJsonPerson;
    } else {
      LOGGER.debug("The person does not exists.");
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
  @Override
  public JsonPerson delete(JsonPerson jsonPerson) {
    LOGGER.debug("Person is being deleted...");
    if (jsonPerson.getFirstName() != null && jsonPerson.getLastName() != null) {
      if (existsByFirstNameAndLastName(convertJsonPersonIntoPerson(jsonPerson))) {
        Person personToDelete = personRepository
          .findByFirstNameAndLastName(jsonPerson.getFirstName(), jsonPerson.getLastName());
        iPersonsAllergyService.deleteAll(personToDelete.getPersonsAllergies());
        iPersonsMedicationService.deleteAll(personToDelete.getPersonsMedications());
        personRepository.delete(personToDelete);


        LOGGER.debug("Person deleted.");
        return jsonPerson;
      } else {
        LOGGER.error("The person does not exists in DB.");
        return null;
      }
    } else {
      LOGGER.error("First name and last name not found in json object.");
      return null;
    }
  }

  /**
   * This method creates a medical record for a specific person found from a jsonMedicalRecord. It
   * works only if the person provided in jsonMedicalRecord already exists, and it has no birthdate
   * and no allergies and no medications already informed in DB.
   *
   * @param jsonMedicalRecord Java Object corresponding to a json Medical Record entity.
   * @return it returns a java object that can be serialized into a jsonMedicalRecord
   */
  @Override
  public JsonMedicalRecord createMedicalRecords(JsonMedicalRecord jsonMedicalRecord) {
    LOGGER.debug("Creating medical record...");
    if (
      existsByFirstNameAndLastName(
        jsonMedicalRecord.getFirstName(),
        jsonMedicalRecord.getLastName()
      )
    ) {
      Person personConcerned = getByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName());
      Iterable<String> medicationsFromPerson = iMedicalRecordsService.getMedicalRecords(personConcerned).getMedications();
      Iterable<String> allergiesFromPerson = iMedicalRecordsService.getMedicalRecords(personConcerned).getAllergies();

      //if there is no record we create it, else we drop the call.
      if (!iMedicalRecordsService.existsFromPerson(personConcerned)) {

        //Update birthdate
        personConcerned = updateBirthDateFromJsonMedicalRecords(jsonMedicalRecord);

        //write new medications
        if (Iterables.size(medicationsFromPerson) == 0) {
          iMedicalRecordsService.createMedicationsFromJsonPerson(jsonMedicalRecord, personConcerned);
        }

        //write new allergies
        if (Iterables.size(allergiesFromPerson) == 0) {
          iMedicalRecordsService.createAllergiesFromJsonPerson(jsonMedicalRecord, personConcerned);
        }

        LOGGER.debug("Medical record created.");
        return jsonMedicalRecord;
      } else {
        LOGGER.error("A medical record already exists for that person.");
        return null;
      }
    } else {
      LOGGER.error("The person does not exists.");
      return null;
    }
  }

  /**
   * This method updates a medical record for a specific person found from a jsonMedicalRecord. It
   * works only if the person provided in jsonMedicalRecord already exists.
   *
   * @param jsonMedicalRecord Java Object corresponding to a json Medical Record entity.
   * @return it returns a java object that can be serialized into a jsonMedicalRecord
   */
  @Override
  public JsonMedicalRecord updateMedicalRecords(JsonMedicalRecord jsonMedicalRecord) {
    if (existsByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName())) {
      Person personConcerned;

      //update birthdate
      personConcerned = updateBirthDateFromJsonMedicalRecords(jsonMedicalRecord);

      //delete existing medication
      iMedicalRecordsService.deletePersonsMedicationsFromPerson(personConcerned);
      //update medications
      iMedicalRecordsService.createMedicationsFromJsonPerson(jsonMedicalRecord, personConcerned);
      //delete existing allergies
      iMedicalRecordsService.deletePersonsAllergiesFromPerson(personConcerned);
      //update allergies
      iMedicalRecordsService.createAllergiesFromJsonPerson(jsonMedicalRecord, personConcerned);

      return jsonMedicalRecord;

    } else {
      return null;
    }
  }

  /**
   * This method deletes a medical record for a specific person found from a jsonMedicalRecord. It
   * works only if the person provided in jsonMedicalRecord already exists.
   *
   * @param jsonMedicalRecord Java Object corresponding to a json Medical Record entity.
   * @return it returns a java object that can be serialized into a jsonMedicalRecord.
   */
  @Override
  public JsonMedicalRecord deleteMedicalRecords(JsonMedicalRecord jsonMedicalRecord) {
    if (existsByFirstNameAndLastName(jsonMedicalRecord.getFirstName(), jsonMedicalRecord.getLastName())) {
      Person personConcerned;

      //delete birthdate
      personConcerned = deleteBirthDateFromJsonMedicalRecords(jsonMedicalRecord);
      //delete existing medication
      iMedicalRecordsService.deletePersonsMedicationsFromPerson(personConcerned);
      //delete existing allergies
      iMedicalRecordsService.deletePersonsAllergiesFromPerson(personConcerned);

      List<String> deletedMedications = new ArrayList<>();

      List<String> deletedAllergies = new ArrayList<>();

      jsonMedicalRecord.setBirthdate("");
      jsonMedicalRecord.setMedications(deletedMedications);
      jsonMedicalRecord.setAllergies(deletedAllergies);

      return jsonMedicalRecord;

    } else {
      return null;
    }
  }

}