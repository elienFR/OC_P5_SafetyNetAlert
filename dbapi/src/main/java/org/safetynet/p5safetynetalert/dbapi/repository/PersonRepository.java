package org.safetynet.p5safetynetalert.dbapi.repository;

import org.safetynet.p5safetynetalert.dbapi.model.Medication;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
  Person findByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, String birthDate);

  Iterable<Person> findAllByFirstNameAndLastName(String firstName, String lastName);

  Iterable<Person> findAllByLastName(String lastName);
}
