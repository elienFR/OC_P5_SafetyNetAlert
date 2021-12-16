package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.FireStation;
import org.safetynet.p5safetynetalert.dbapi.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class FireStationService {

  @Autowired
  private FireStationRepository fireStationRepository;

  public Optional<FireStation> getFireStation(final Integer id) {
    return fireStationRepository.findById(id);
  }

  public Iterable<FireStation> getFireStations() {
    return fireStationRepository.findAll();
  }

  public void deleteFireStation(final Integer id) {
    fireStationRepository.deleteById(id);
  }

  public FireStation saveFireStation(FireStation fireStation) {
    FireStation savedFireStation = fireStationRepository.save(fireStation);
    return savedFireStation;
  }

}
