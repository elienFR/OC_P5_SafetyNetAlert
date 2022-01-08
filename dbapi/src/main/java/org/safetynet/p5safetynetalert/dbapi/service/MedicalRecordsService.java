package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.entity.*;
import org.safetynet.p5safetynetalert.dbapi.model.dto.MedicalRecordsDTO;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordsService {

  @Autowired
  PersonsMedicationService personsMedicationService;
  @Autowired
  PersonsAllergyService personsAllergyService;

  public MedicalRecordsDTO getMedicalRecords(Person person) {
    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();

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

  /**
   * This method get the medical record object (list of medications and allergies) assciated to
   * a person.
   *
   * @param person is the person you want to get the medical records from.
   * @return a MedicalRecordsDTO object linked to one person.
   */
  public MedicalRecordsDTO getMedicalRecordsDTOFromPerson(Person person) {
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

    return medicalRecordsDTOs;
  }

}
