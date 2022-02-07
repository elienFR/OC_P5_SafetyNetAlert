package org.safetynet.p5safetynetalert.service;

import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.entity.PersonsMedication;

import java.util.List;

public interface IPersonsMedicationService {

  /**
   * This method creates a list of medication strings from a persons medications Iterable.
   *
   * @param personsMedications is the person's medications Iterable
   * @return see description.
   */
  List<String> getMedicationsFromPersonsMedications(Iterable<PersonsMedication> personsMedications);

  /**
   * Delete all medication records from one person
   * @param person person you want to delete medication from.
   */
  void deleteAllFromPerson(Person person);

  /**
   * This method saves a person's medication in DB.
   *
   * @param personsMedication
   * @return
   */
  PersonsMedication save(PersonsMedication personsMedication);

  /**
   * delete all PersonsMedication in Iterable
   *
   * @param personsMedications Iterable of PersonsMedication to delete from DB.
   */
  void deleteAll(Iterable<PersonsMedication> personsMedications);
}
