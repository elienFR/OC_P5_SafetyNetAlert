package org.safetynet.p5safetynetalert.dbapi.repository;

import org.safetynet.p5safetynetalert.dbapi.model.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllergyRepository extends CrudRepository<Allergy, Integer> {
  public Allergy findByName(String name);
  }
