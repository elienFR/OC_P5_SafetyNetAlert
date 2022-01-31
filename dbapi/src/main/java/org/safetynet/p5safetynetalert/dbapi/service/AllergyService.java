package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.entity.Allergy;
import org.safetynet.p5safetynetalert.dbapi.repository.AllergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllergyService {

  @Autowired
  private AllergyRepository allergyRepository;

  public Iterable<Allergy> getAllergies() {
    return allergyRepository.findAll();
  }

  public Allergy save(Allergy allergy) {
    return allergyRepository.save(allergy);
  }

  public boolean exists(String allergy) {
    return allergyRepository.existsByName(allergy);
  }

  public Allergy getByName(String allergy) {
    return allergyRepository.findByName(allergy);
  }
}
