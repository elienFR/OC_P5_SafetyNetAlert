package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.PersonsAllergy;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonsAllergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class PersonsAllergyService {

  @Autowired
  private PersonsAllergyRepository personsAllergyRepository;
  
  public Optional<PersonsAllergy> getPersonsAllergy(final Integer id){
    return personsAllergyRepository.findById(id);
  }
  
  public Iterable<PersonsAllergy> getPersonsAllergies() {
    return personsAllergyRepository.findAll();
  }
  
  public PersonsAllergy savePersonsAllergy(PersonsAllergy personsAllergy) {
    PersonsAllergy savedPersonsAllergy = personsAllergyRepository.save(personsAllergy);
    return savedPersonsAllergy;
  }
  
}
