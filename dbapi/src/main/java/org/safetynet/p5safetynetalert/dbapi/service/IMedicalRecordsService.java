package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.dto.MedicalRecordsDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;

public interface IMedicalRecordsService {

  /**
   * This method get the medical record object (list of medications and allergies) assciated to
   * a person.
   *
   * @param person is the person you want to get the medical records from.
   * @return a MedicalRecordsDTO object linked to one person.
   */
  MedicalRecordsDTO getMedicalRecordsDTOFromPerson(Person person);

}
