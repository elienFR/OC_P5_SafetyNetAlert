package org.safetynet.p5safetynetalert.dbapi.repository;

import org.safetynet.p5safetynetalert.dbapi.model.entity.Allergy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends CrudRepository<Allergy, Integer> {
  public Allergy findByName(String name);

  boolean existsByName(String allergy);
}
