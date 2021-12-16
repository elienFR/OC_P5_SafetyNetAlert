package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  public Optional<Person> getPerson(final Integer id){
    return personRepository.findById(id);
  }

  public Iterable<Person> getPersons() {
    return personRepository.findAll();
  }

  public Person savePerson(Person person) {
    Person savedPerson = personRepository.save(person);
    return savedPerson;
  }

}
