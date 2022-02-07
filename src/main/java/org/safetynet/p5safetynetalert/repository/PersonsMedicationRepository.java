package org.safetynet.p5safetynetalert.repository;

import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.entity.PersonsMedication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonsMedicationRepository extends CrudRepository<PersonsMedication, Integer> {
  Iterable<PersonsMedication> findAllByPerson(Person person);
}
