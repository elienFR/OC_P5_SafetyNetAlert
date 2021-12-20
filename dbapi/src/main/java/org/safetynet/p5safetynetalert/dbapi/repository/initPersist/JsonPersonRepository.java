package org.safetynet.p5safetynetalert.dbapi.repository.initPersist;

import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPerson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JsonPersonRepository extends CrudRepository<JsonPerson, Integer> {
}
