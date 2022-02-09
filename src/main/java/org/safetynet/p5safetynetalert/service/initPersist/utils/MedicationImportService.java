package org.safetynet.p5safetynetalert.service.initPersist.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.entity.Medication;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.repository.MedicationRepository;
import org.safetynet.p5safetynetalert.service.initPersist.JsonDataInjectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class MedicationImportService {

  @Autowired
  private MedicationRepository medicationRepository;

  private static final Logger LOGGER = LogManager.getLogger(JsonDataInjectorServiceImpl.class);

  /**
   * script that import medications from a json data object
   */
  public void importMedications(JsonData jsonData) {
    LOGGER.debug("importing medications...");

    Set<String> mySet = new TreeSet<>();
    List<Medication> myList = new ArrayList<>();

    //Create a unique set
    int length = jsonData.getMedicalRecords().getMedicalrecords().size();
    for (int i = 0; i < length; i++) {
      int subLength = jsonData.getMedicalRecords().getMedicalrecords().get(i).getMedications().size();
      for (int j = 0; j < subLength; j++) {
        String stringToAdd = jsonData.getMedicalRecords()
          .getMedicalrecords()
          .get(i)
          .getMedications()
          .get(j);
        mySet.add(stringToAdd);
      }
    }

    //Generate proper list to insert into DataBase
    for (String medicationName : mySet) {
      Medication medicationToAdd = new Medication();
      medicationToAdd.setName(medicationName);
      myList.add(medicationToAdd);
    }

    //Import in H2 DB
    medicationRepository.saveAll(myList);
  }
}
