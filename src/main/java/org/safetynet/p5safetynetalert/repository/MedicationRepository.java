package org.safetynet.p5safetynetalert.repository;

import org.safetynet.p5safetynetalert.model.entity.Medication;
import org.springframework.data.repository.CrudRepository;

public interface MedicationRepository extends CrudRepository<Medication, Integer> {
  Medication findByName(String name);

  boolean existsByName(String medication);
}
