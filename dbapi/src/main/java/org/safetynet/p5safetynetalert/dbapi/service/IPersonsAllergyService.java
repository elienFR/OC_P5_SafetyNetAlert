package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.model.entity.PersonsAllergy;

import java.util.List;

public interface IPersonsAllergyService {

  /**
   * This method creates a list of allergy strings from a persons medications Iterable.
   *
   * @param personsAllergies is the person's allergies Iterable
   * @return see description.
   */
  List<String> getAllergiesFromPersonsMedications(Iterable<PersonsAllergy> personsAllergies);

  /**
   * Delete all allergies records from one person
   * @param person person you want to delete allergies from.
   */
  void deleteAllFromPerson(Person person);

  /**
   * This method saves a person's allergy in DB.
   *
   * @param personsAllergy
   * @return
   */
  PersonsAllergy save(PersonsAllergy personsAllergy);

  /**
   * delete all PersonsAllergy in Iterable
   *
   * @param personsAllergies Iterable of PersonsAllergy to delete from DB.
   */
  void deleteAll(Iterable<PersonsAllergy> personsAllergies);
}
