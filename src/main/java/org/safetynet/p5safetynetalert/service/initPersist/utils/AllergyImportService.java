package org.safetynet.p5safetynetalert.service.initPersist.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.entity.Allergy;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.repository.AllergyRepository;
import org.safetynet.p5safetynetalert.service.initPersist.JsonDataInjectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class AllergyImportService {

  @Autowired
  private AllergyRepository allergyRepository;

  private static final Logger LOGGER = LogManager.getLogger(JsonDataInjectorServiceImpl.class);

  /**
   * script that import allergies from a jsondata object
   */
  public void importAllergies(JsonData jsonData) {
    LOGGER.debug("importing allergies...");
    Set<String> mySet = new TreeSet<>();
    List<Allergy> myList = new ArrayList<>();

    //create a unique set
    int length = jsonData.getMedicalRecords().getMedicalrecords().size();
    for (int i = 0; i < length; i++) {
      int subLength = jsonData.getMedicalRecords().getMedicalrecords().get(i).getAllergies()
        .size();
      for (int j = 0; j < subLength; j++) {
        String stringToAdd = jsonData.getMedicalRecords()
          .getMedicalrecords()
          .get(i)
          .getAllergies()
          .get(j);
        mySet.add(stringToAdd);
      }
    }

    //generate proper list to insert into DataBase
    for (String allergy : mySet) {
      Allergy allergyToAdd = new Allergy();
      allergyToAdd.setName(allergy);
      myList.add(allergyToAdd);
    }

    //Import in H2 DB
    allergyRepository.saveAll(myList);
  }

}
