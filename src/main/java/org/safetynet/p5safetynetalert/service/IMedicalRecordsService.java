package org.safetynet.p5safetynetalert.service;

import org.safetynet.p5safetynetalert.model.dto.MedicalRecordsDTO;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.initPersist.JsonMedicalRecord;

public interface IMedicalRecordsService {

  /**
   * This method returns the medical records DTO of a Person object.
   *
   * @param person is the person you want to retrieve the medical record DTO.
   * @return see description.
   */
  MedicalRecordsDTO getMedicalRecords(Person person);

  /**
   * Check the existence of a medical record assigned to a person.
   * true if at least one medical record is associated with one person.
   *
   * @param person person to check medical record from
   * @return a boolean. true for existence.
   */
  boolean existsFromPerson(Person person);

  /**
   * This method creates medications in DB from a json medical record for a specific person
   *
   * @param jsonMedicalRecord
   * @param personConcerned
   */
  void createMedicationsFromJsonPerson(JsonMedicalRecord jsonMedicalRecord, Person personConcerned);

  /**
   * This method creates allergies in DB from a json medical record for a specific person
   *
   * @param jsonMedicalRecord
   * @param personConcerned
   */
  void createAllergiesFromJsonPerson(JsonMedicalRecord jsonMedicalRecord, Person personConcerned);

  /**
   * This method deletes all medications for one person
   *
   * @param person
   */
  void deletePersonsMedicationsFromPerson(Person person);

  /**
   * This method deletes all allergies for one person
   *
   * @param person
   */
  void deletePersonsAllergiesFromPerson(Person person);
}
