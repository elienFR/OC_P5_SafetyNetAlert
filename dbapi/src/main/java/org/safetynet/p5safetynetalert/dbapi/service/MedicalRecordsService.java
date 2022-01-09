package org.safetynet.p5safetynetalert.dbapi.service;

import com.google.common.collect.Iterables;
import org.safetynet.p5safetynetalert.dbapi.model.entity.*;
import org.safetynet.p5safetynetalert.dbapi.model.dto.MedicalRecordsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordsService {

  @Autowired
  private PersonsMedicationService personsMedicationService;
  @Autowired
  private PersonsAllergyService personsAllergyService;
  @Autowired
  private MedicationService medicationService;
  @Autowired
  private AllergyService allergyService;

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

  public boolean existsFromPerson(Person person) {
    Iterable<String> medications = getMedicalRecords(person).getMedications();
    Iterable<String> allergies = getMedicalRecords(person).getAllergies();
    return !((person.getBirthDate() == null || person.getBirthDate().equals(""))
      && (medications == null || Iterables.size(medications) == 0)
      && (allergies == null || Iterables.size(allergies) == 0));
  }

  public boolean getMedicationExistence(String medication) {
    return medicationService.existsByName(medication);
  }

  public Medication saveMedication(String medication) {
    Medication medicationToSave = new Medication();
    medicationToSave.setName(medication);
    return medicationService.save(medicationToSave);
  }

  public Medication getMedication(String medication) {
    return medicationService.getByName(medication);
  }

  public PersonsMedication savePersonsMedication(PersonsMedication personsMedication) {
    return personsMedicationService.save(personsMedication);
  }

  public boolean getAllergyExistence(String allergy) {
    return allergyService.existsByName(allergy);
  }

  public Allergy saveAllergy(String allergy) {
    Allergy allergyToSave = new Allergy();
    allergyToSave.setName(allergy);
    return allergyService.save(allergyToSave);
  }

  public Allergy getAllergyByName(String allergy) {
    return allergyService.getByName(allergy);
  }

  public PersonsAllergy savePersonsAllergy(PersonsAllergy personsAllergy) {
    return personsAllergyService.save(personsAllergy);
  }
}
