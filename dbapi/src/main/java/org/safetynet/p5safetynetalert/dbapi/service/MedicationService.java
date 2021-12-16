package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.Medication;
import org.safetynet.p5safetynetalert.dbapi.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class MedicationService {

  @Autowired
  private MedicationRepository medicationRepository;

  public Optional<Medication> getMedication(final Integer id) {
    return medicationRepository.findById(id);
  }

  public Iterable<Medication> getMedications() {
    return medicationRepository.findAll();
  }

  public void deleteMedication(final Integer id) {
    medicationRepository.deleteById(id);
  }

  public Medication saveMedication(Medication medication) {
    Medication savedMedication = medicationRepository.save(medication);
    return savedMedication;
  }

}
