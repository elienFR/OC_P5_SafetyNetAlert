package org.safetynet.p5safetynetalert.repository;

import org.safetynet.p5safetynetalert.model.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
  Person findByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, String birthDate);

  Person findByFirstNameAndLastName(String firstName, String lastName);

  Iterable<Person> findAllByFirstNameAndLastName(String firstName, String lastName);

  Iterable<Person> findAllByLastName(String lastName);

  boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
