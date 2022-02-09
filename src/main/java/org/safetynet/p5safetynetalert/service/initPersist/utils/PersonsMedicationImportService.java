package org.safetynet.p5safetynetalert.service.initPersist.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.entity.Medication;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.entity.PersonsMedication;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.repository.MedicationRepository;
import org.safetynet.p5safetynetalert.repository.PersonRepository;
import org.safetynet.p5safetynetalert.repository.PersonsMedicationRepository;
import org.safetynet.p5safetynetalert.service.initPersist.JsonDataInjectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class PersonsMedicationImportService {

  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private MedicationRepository medicationRepository;
  @Autowired
  private PersonsMedicationRepository personsMedicationRepository;

  private static final Logger LOGGER = LogManager.getLogger(JsonDataInjectorServiceImpl.class);

  /**
   * script that import persons medications from a json data object
   */
  public void importPersonsMedications(JsonData jsonData) {
    LOGGER.debug("importing person's medications...");

    Set<String> mySet = new TreeSet<>();
    //Analyse each line in data.json concerning Medical records
    for (JsonMedicalRecord jsonMedicalRecord : jsonData.getMedicalRecords().getMedicalrecords()) {
      //Constructing unique key
      String firstName = jsonMedicalRecord.getFirstName();
      String lastName = jsonMedicalRecord.getLastName();
      String birthDate = jsonMedicalRecord.getBirthdate();
      String uniqueKey = firstName + lastName + birthDate;

      //if json object is unique
      if (!mySet.contains(uniqueKey)) {
        //Recover the already existing person from database regarding first/last name and birthday
        Person personToAdd = personRepository
          .findByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);

        //Recover each medication for one person
        for (String medicationName : jsonMedicalRecord.getMedications()) {
          Medication medicationToAdd = medicationRepository.findByName(medicationName);
          PersonsMedication personsMedicationToSave = new PersonsMedication();
          personsMedicationToSave.setPerson(personToAdd);
          personsMedicationToSave.setMedication(medicationToAdd);
          personsMedicationRepository.save(personsMedicationToSave);
        }
      }

      mySet.add(uniqueKey);
    }
  }

}
