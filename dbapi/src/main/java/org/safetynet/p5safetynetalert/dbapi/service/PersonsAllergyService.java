package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.entity.PersonsAllergy;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonsAllergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class PersonsAllergyService {

  @Autowired
  private PersonsAllergyRepository personsAllergyRepository;

  public List<String> getAllergiesFromPersonsMedications(
      Iterable<PersonsAllergy> personsAllergies) {
    List<String> allergies = new ArrayList<>();

    for(PersonsAllergy personsAllergy : personsAllergies){
      allergies.add(personsAllergy.getAllergy().getName());
    }

    return allergies;
  }
}
