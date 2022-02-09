package org.safetynet.p5safetynetalert.service.initPersist.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.entity.Allergy;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.entity.PersonsAllergy;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.repository.AllergyRepository;
import org.safetynet.p5safetynetalert.repository.PersonRepository;
import org.safetynet.p5safetynetalert.repository.PersonsAllergyRepository;
import org.safetynet.p5safetynetalert.service.initPersist.JsonDataInjectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class PersonsAllergyImportService {

  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private AllergyRepository allergyRepository;
  @Autowired
  private PersonsAllergyRepository personsAllergyRepository;

  private static final Logger LOGGER = LogManager.getLogger(JsonDataInjectorServiceImpl.class);

  /**
   * Scrip that import person's allergies from a json data object
   */
  public void importPersonsAllergies(JsonData jsonData) {
    LOGGER.debug("importing person's allergies...");

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

        //Recover each allergy for one person
        for (String allergy : jsonMedicalRecord.getAllergies()) {
          Allergy allergyToAdd = allergyRepository.findByName(allergy);
          PersonsAllergy personsAllergyToSave = new PersonsAllergy();
          personsAllergyToSave.setPerson(personToAdd);
          personsAllergyToSave.setAllergy(allergyToAdd);
          personsAllergyRepository.save(personsAllergyToSave);
        }
      }
      mySet.add(uniqueKey);
    }
  }
}
