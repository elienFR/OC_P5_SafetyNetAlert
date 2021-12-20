package org.safetynet.p5safetynetalert.dbapi.repository;

import org.safetynet.p5safetynetalert.dbapi.model.Medication;
import org.springframework.data.repository.CrudRepository;

public interface MedicationRepository extends CrudRepository<Medication, Integer> {
}
