package org.safetynet.p5safetynetalert.repository;

import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.entity.PersonsAllergy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonsAllergyRepository extends CrudRepository<PersonsAllergy, Integer> {

  Iterable<PersonsAllergy> findAllByPerson(Person person);
}
