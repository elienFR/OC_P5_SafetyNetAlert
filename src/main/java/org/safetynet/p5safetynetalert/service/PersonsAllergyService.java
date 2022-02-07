package org.safetynet.p5safetynetalert.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.entity.PersonsAllergy;
import org.safetynet.p5safetynetalert.repository.PersonsAllergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonsAllergyService implements IPersonsAllergyService {

  private static final Logger LOGGER = LogManager.getLogger(PersonsAllergyService.class);
  @Autowired
  private PersonsAllergyRepository personsAllergyRepository;


  private Iterable<PersonsAllergy> getAllFromPerson(Person person) {
    return personsAllergyRepository.findAllByPerson(person);
  }

  /**
   * This method creates a list of allergy strings from a persons medications Iterable.
   *
   * @param personsAllergies is the person's allergies Iterable
   * @return see description.
   */
  @Override
  public List<String> getAllergiesFromPersonsMedications(
    Iterable<PersonsAllergy> personsAllergies) {
    LOGGER.debug("Getting persons' allergies...");
    List<String> allergies = new ArrayList<>();
    for (PersonsAllergy personsAllergy : personsAllergies) {
      allergies.add(personsAllergy.getAllergy().getName());
    }
    LOGGER.debug("Persons' allergies got.");
    return allergies;
  }

  /**
   * delete all PersonsAllergy in Iterable
   *
   * @param personsAllergies Iterable of PersonsAllergy to delete from DB.
   */
  @Override
  public void deleteAll(Iterable<PersonsAllergy> personsAllergies) {
    personsAllergyRepository.deleteAll(personsAllergies);
  }

  /**
   * This method saves a person's allergy in DB.
   *
   * @param personsAllergy
   * @return
   */
  @Override
  public PersonsAllergy save(PersonsAllergy personsAllergy) {
    return personsAllergyRepository.save(personsAllergy);
  }

  /**
   * Delete all allergies records from one person
   * @param person person you want to delete allergies from.
   */
  @Override
  public void deleteAllFromPerson(Person person) {
    Iterable<PersonsAllergy> personsAllergiesToDelete = getAllFromPerson(person);
    personsAllergyRepository.deleteAll(personsAllergiesToDelete);
  }
}
