package org.safetynet.p5safetynetalert.dbapi.service;

import com.google.common.collect.Iterables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.entity.*;
import org.safetynet.p5safetynetalert.dbapi.model.dto.MedicalRecordsDTO;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordsService implements IMedicalRecordsService {

  private static final Logger LOGGER = LogManager.getLogger(MedicalRecordsService.class);
  @Autowired
  private IPersonsMedicationService iPersonsMedicationService;
  @Autowired
  private IPersonsAllergyService iPersonsAllergyService;
  @Autowired
  private MedicationService medicationService;
  @Autowired
  private AllergyService allergyService;

  /**
   * This method returns the medical records DTO of a Person object.
   *
   * @param person is the person you want to retrieve the medical record DTO.
   * @return see description.
   */
  @Override
  public MedicalRecordsDTO getMedicalRecords(Person person) {
    LOGGER.debug("Fetching Medical records DTO from Person.");
    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();

    List<String> medications = new ArrayList<>(
      iPersonsMedicationService.getMedicationsFromPersonsMedications(
        person.getPersonsMedications()
      )
    );

    List<String> allergies = new ArrayList<>(
      iPersonsAllergyService.getAllergiesFromPersonsMedications(
        person.getPersonsAllergies()
      )
    );

    medicalRecordsDTO.setMedications(medications);
    medicalRecordsDTO.setAllergies(allergies);
    LOGGER.debug("Medical records DTO fetched.");
    return medicalRecordsDTO;
  }

  /**
   * Check the existence of a medical record assigned to a person.
   * true if at least one medical record is associated with one person.
   *
   * @param person person to check medical record from
   * @return a boolean. true for existence.
   */
  @Override
  public boolean existsFromPerson(Person person) {
    MedicalRecordsDTO medicalRecordsDTO = getMedicalRecords(person);
    Iterable<String> medications = medicalRecordsDTO.getMedications();
    Iterable<String> allergies = medicalRecordsDTO.getAllergies();
    return !((person.getBirthDate() == null || person.getBirthDate().isBlank())
      && (medications == null || Iterables.size(medications) == 0)
      && (allergies == null || Iterables.size(allergies) == 0));
  }

  private boolean getMedicationExistence(String medication) {
    return medicationService.existsByName(medication);
  }

  private Medication saveMedication(String medication) {
    Medication medicationToSave = new Medication();
    medicationToSave.setName(medication);
    return medicationService.save(medicationToSave);
  }

  private Medication getMedication(String medication) {
    return medicationService.getByName(medication);
  }

  private PersonsMedication savePersonsMedication(PersonsMedication personsMedication) {
    return iPersonsMedicationService.save(personsMedication);
  }

  private boolean getAllergyExistence(String allergy) {
    return allergyService.exists(allergy);
  }

  private Allergy saveAllergy(String allergy) {
    Allergy allergyToSave = new Allergy();
    allergyToSave.setName(allergy);
    return allergyService.save(allergyToSave);
  }

  private Allergy getAllergyByName(String allergy) {
    return allergyService.getByName(allergy);
  }

  private PersonsAllergy savePersonsAllergy(PersonsAllergy personsAllergy) {
    return iPersonsAllergyService.save(personsAllergy);
  }

  /**
   * This method creates a medications in DB from a json medical record for a specific person
   *
   * @param jsonMedicalRecord
   * @param person
   */
  @Override
  public void createMedicationsFromJsonPerson(JsonMedicalRecord jsonMedicalRecord, Person person) {
    Iterable<String> medicationToAdd = jsonMedicalRecord.getMedications();
    for (String medication : medicationToAdd) {
      if (medication != null && !medication.isBlank()) {
        Medication medicationToAssign;
        if (!getMedicationExistence(medication)) {
          medicationToAssign = saveMedication(medication);
        } else {
          medicationToAssign = getMedication(medication);
        }
        PersonsMedication personsMedicationToSave = new PersonsMedication();
        personsMedicationToSave.setPerson(person);
        personsMedicationToSave.setMedication(medicationToAssign);

        savePersonsMedication(personsMedicationToSave);
      }
    }

  }

  /**
   * This method creates allergies in DB from a json medical record for a specific person
   *
   * @param jsonMedicalRecord
   * @param person
   */
  @Override
  public void createAllergiesFromJsonPerson(JsonMedicalRecord jsonMedicalRecord, Person person) {
    Iterable<String> allergiesToAdd = jsonMedicalRecord.getAllergies();
    for (String allergy : allergiesToAdd) {
      if (allergy != null && !allergy.isBlank()) {
        Allergy allergyToAssign;
        if (!getAllergyExistence(allergy)) {
          allergyToAssign = saveAllergy(allergy);
        } else {
          allergyToAssign = getAllergyByName(allergy);
        }
        PersonsAllergy personsAllergyToSave = new PersonsAllergy();
        personsAllergyToSave.setAllergy(allergyToAssign);
        personsAllergyToSave.setPerson(person);

        savePersonsAllergy(personsAllergyToSave);
      }
    }
  }

  /**
   * This method deletes all medications for one person
   *
   * @param person
   */
  @Override
  public void deletePersonsMedicationsFromPerson(Person person) {
    iPersonsMedicationService.deleteAllFromPerson(person);
  }

  /**
   * This method deletes all allergies for one person
   *
   * @param person
   */
  @Override
  public void deletePersonsAllergiesFromPerson(Person person) {
    iPersonsAllergyService.deleteAllFromPerson(person);
  }
}
