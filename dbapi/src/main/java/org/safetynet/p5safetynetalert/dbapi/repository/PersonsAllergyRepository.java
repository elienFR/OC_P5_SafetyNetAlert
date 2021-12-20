package org.safetynet.p5safetynetalert.dbapi.repository;

import org.safetynet.p5safetynetalert.dbapi.model.PersonsAllergy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonsAllergyRepository extends CrudRepository<PersonsAllergy, Integer> {

}
