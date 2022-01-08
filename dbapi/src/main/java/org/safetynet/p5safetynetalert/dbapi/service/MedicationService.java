package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Medication;
import org.safetynet.p5safetynetalert.dbapi.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

  public Medication save(Medication medication) {
    return medicationRepository.save(medication);
  }

  public Iterable<Medication> saveAll (Iterable<Medication> medications) {
    return medicationRepository.saveAll(medications);
  }

  /**
   * Check if an allergy already exists in the database
   *
   * @param medication
   * @return True if it exists.
   */
  public boolean exists(String medication) {
    return medicationRepository.existsByName(medication);
  }
}
