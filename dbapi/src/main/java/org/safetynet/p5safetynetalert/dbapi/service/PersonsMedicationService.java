package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.model.entity.PersonsMedication;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonsMedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class PersonsMedicationService implements IPersonsMedicationService {

  private static final Logger LOGGER = LogManager.getLogger(PersonsMedicationService.class);
  @Autowired
  private PersonsMedicationRepository personsMedicationRepository;

  public Iterable<PersonsMedication> getAllFromPerson(Person person) {
    return personsMedicationRepository.findAllByPerson(person);
  }

  /**
   * This method creates a list of medication strings from a persons medications Iterable.
   *
   * @param personsMedications is the person's medications Iterable
   * @return see description.
   */
  public List<String> getMedicationsFromPersonsMedications(
      Iterable<PersonsMedication> personsMedications) {
    LOGGER.debug("Getting persons' medications...");
    List<String> medications = new ArrayList<>();

    for(PersonsMedication personsMedication : personsMedications){
      medications.add(personsMedication.getMedication().getName());
    }
    LOGGER.debug("Persons' medications got.");
    return medications;
  }

  public void delete(Iterable<PersonsMedication> personsMedications) {
    personsMedicationRepository.deleteAll(personsMedications);
  }

  /**
   * This method saves a person's medication in DB.
   *
   * @param personsMedication
   * @return
   */
  @Override
  public PersonsMedication save(PersonsMedication personsMedication) {
    return personsMedicationRepository.save(personsMedication);
  }

  /**
   * Delete all medication records from one person
   * @param person person you want to delete medication from.
   */
  @Override
  public void deleteAllFromPerson(Person person) {
    Iterable<PersonsMedication> personsMedicationsToDelete = getAllFromPerson(person);
    personsMedicationRepository.deleteAll(personsMedicationsToDelete);
  }

}
