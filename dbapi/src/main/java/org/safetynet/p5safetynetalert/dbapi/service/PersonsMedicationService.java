package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.model.PersonsMedication;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonsMedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class PersonsMedicationService {

  @Autowired
  private PersonsMedicationRepository personsMedicationRepository;
  
  public Optional<PersonsMedication> getPersonsMedication(final Integer id){
    return personsMedicationRepository.findById(id);
  }
  
  public Iterable<PersonsMedication> getPersonsMedications() {
    return personsMedicationRepository.findAll();
  }
  
  public PersonsMedication savePersonsMedication(PersonsMedication personsMedication) {
    PersonsMedication savedPersonsMedication = personsMedicationRepository.save(personsMedication);
    return savedPersonsMedication;
  }

  public List<String> getMedicationsFromPersonsMedications(
      Iterable<PersonsMedication> personsMedications) {

    List<String> medications = new ArrayList<>();

    for(PersonsMedication personsMedication : personsMedications){
      medications.add(personsMedication.getMedication().getName());
    }

    return medications;
  }
  
}
