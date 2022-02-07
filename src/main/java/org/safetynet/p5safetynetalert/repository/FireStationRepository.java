package org.safetynet.p5safetynetalert.repository;

import org.safetynet.p5safetynetalert.model.entity.FireStation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends CrudRepository<FireStation, Integer> {
  FireStation findByNumber(String s);

  Boolean existsByNumber(String number);
}
