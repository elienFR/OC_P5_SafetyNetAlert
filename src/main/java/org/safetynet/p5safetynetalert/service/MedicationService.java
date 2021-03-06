package org.safetynet.p5safetynetalert.service;

import org.safetynet.p5safetynetalert.model.entity.Medication;
import org.safetynet.p5safetynetalert.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicationService {

  @Autowired
  private MedicationRepository medicationRepository;

  public Iterable<Medication> getMedications() {
    return medicationRepository.findAll();
  }

  public Medication save(Medication medication) {
    return medicationRepository.save(medication);
  }

  /**
   * Check if an allergy already exists in the database
   *
   * @param medication
   * @return True if it exists.
   */
  public boolean existsByName(String medication) {
    return medicationRepository.existsByName(medication);
  }

  public Medication getByName(String meds) {
    return medicationRepository.findByName(meds);
  }
}
