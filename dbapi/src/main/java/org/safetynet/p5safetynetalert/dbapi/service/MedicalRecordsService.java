package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.model.PersonsAllergy;
import org.safetynet.p5safetynetalert.dbapi.model.PersonsMedication;
import org.safetynet.p5safetynetalert.dbapi.model.dto.MedicalRecordsDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordsService {

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
