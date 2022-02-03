package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.dto.ChildDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonForFireDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonForFloodDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPerson;

import java.util.Collection;

public interface IPersonService {

  /**
   * This method creates a medical record for a specific person found from a jsonMedicalRecord. It
   * works only if the person provided in jsonMedicalRecord already exists and it has no birthdate
   * and no allergies and no medications already informed in DB.
   *
   * @param jsonMedicalRecord Java Object corresponding to a json Medical Record entity.
   * @return it returns a java object that can be serialized into a jsonMedicalRecord
   */
  JsonMedicalRecord createMedicalRecords(JsonMedicalRecord jsonMedicalRecord);

  /**
   * This method updates a medical record for a specific person found from a jsonMedicalRecord. It
   * works only if the person provided in jsonMedicalRecord already exists.
   *
   * @param jsonMedicalRecord Java Object corresponding to a json Medical Record entity.
   * @return it returns a java object that can be serialized into a jsonMedicalRecord
   */
  JsonMedicalRecord updateMedicalRecords(JsonMedicalRecord jsonMedicalRecord);

  /**
   * This method deletes a medical record for a specific person found from a jsonMedicalRecord. It
   * works only if the person provided in jsonMedicalRecord already exists.
   *
   * @param jsonMedicalRecord Java Object corresponding to a json Medical Record entity.
   * @return it returns a java object that can be serialized into a jsonMedicalRecord.
   */
  JsonMedicalRecord deleteMedicalRecords(JsonMedicalRecord jsonMedicalRecord);

  /**
   * This method create a Person into the database from a JsonPerson.
   * This Person has a null birthdate and an address always located in culver,
   * that is because of the properties of a JsonPerson. See convertJsonPersonIntoPerson method for
   * more information about this.
   * This method also saves the address if it does not exist already.
   * And it checks if the person already exists (according to first and last name)
   *
   * @param jsonPerson is the JsonPerson to add into DB.
   * @return the save JsonPerson
   */
  JsonPerson createPerson(JsonPerson jsonPerson);

  /**
   * This method update an existing person. Ii checks if the person already exists. If it does not
   * it will return null. If it does, it will update it with the information found in putJsonPerson
   * parameter.
   *
   * @param jsonPerson is the JsonPerson you want to update
   * @return null or a JsonPersonObject which has been updated. See description.
   */
  JsonPerson updatePersonWithJsonPerson(JsonPerson jsonPerson);

  /**
   * This method deletes a person from database. We consider a person is unique by
   * its first name and last name combination.
   *
   * @param jsonPerson is the deserialized jsonObject
   * @return the jsonObject of deleted person if method is executed successfully.
   */
  JsonPerson delete(JsonPerson jsonPerson);

  /**
   * This method returns a collection of children DTO from an Address
   *
   * @param address is the address where to extract ChildrenDTO
   * @return A collection of ChildDTO
   */
  Collection<ChildDTO> getChildrenDTO(Address address);

  /**
   * This method returns a collection of Person DTO from an Address
   *
   * @param address is the address where to extract ChildrenDTO
   * @return A collection of PersonDTO
   */
  Collection<PersonDTO> getAdultsDTO(Address address);

  /**
   * This method returns a Collection of Person found in a specific address.
   *
   * @param addresses A Collection of addresses object used to get persons from.
   * @return A collection of persons from a collection of addresses.
   */
  Collection<Person> getPersonsFromAddresses(Collection<Address> addresses);

  /**
   * This method returns all the email contained in a collection of persons.
   *
   * @param persons is the collection of person
   * @return is a list of String containing emails.
   */
  Collection<String> getEmails(Collection<Person> persons);

  /**
   * This method loads a collection of person form an address object.
   *
   * @param address is the object with which the collection of persons is created.
   * @return a collection of persons.
   */
  Collection<Person> getPersonsFromAddress(Address address);

  /**
   * This method convert a collection of PersonForFireDTO from a collection of persons
   *
   * @param persons is the collection of persons
   * @return is the collection of PersonForFireDTOs
   */
  Collection<PersonForFireDTO> convertPersonsInPersonForFireDTO(Collection<Person> persons);

  /**
   * This method loads a collection of persons from a collection of addresses.
   *
   * @param addresses is the collection of addresses.
   * @return a collection of persons.
   */
  Collection<PersonDTO> getPersonDTOsFromAddresses(Collection<Address> addresses);

  /**
   * This method returns a collection of string. Each one represent a phone number extracted from a
   * collection of Persons.
   *
   * @param personsFromAddresses is the collection of persons.
   * @return is the collection of phone' string.
   */
  Collection<String> getPhones(Collection<Person> personsFromAddresses);

  /**
   * This method collects each Person from a collection of addresses and add them in a collection of
   * PersonForFloodDTOs.
   *
   * @param addresses is the collection of addresses
   * @return see description.
   */
  Collection<PersonForFloodDTO> getPersonsForFlood(Collection<Address> addresses);

  /**
   * This method returns a person from DB with its first name and its last name.
   *
   * @param firstName is the first name of the person you look for.
   * @param lastName  is the last name of the person you look for.
   * @return a Person object with corresponding first name and last name.
   */
  Person getByFirstNameAndLastName(String firstName, String lastName);

  /**
   * Return an Iterable of Persons that have the same last name
   *
   * @param lastName is the last name used to find persons
   * @return see description
   */
  Iterable<Person> getAllByName(String lastName);
}
