package org.safetynet.p5safetynetalert.dbapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends CrudRepository<FireStationRepository, Integer> {
}
