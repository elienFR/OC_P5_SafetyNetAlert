package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.Allergy;
import org.safetynet.p5safetynetalert.dbapi.repository.AllergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class AllergyService {

  @Autowired
  private AllergyRepository allergyRepository;

  public Optional<Allergy> getAllergy(final Integer id) {
    return allergyRepository.findById(id);
  }

  public Iterable<Allergy> getAllergyes() {
    return allergyRepository.findAll();
  }

  public void deleteAllergy(final Integer id) {
    allergyRepository.deleteById(id);
  }

  public Allergy saveAllergy(Allergy allergy) {
    Allergy savedAllergy = allergyRepository.save(allergy);
    return savedAllergy;
  }

}
