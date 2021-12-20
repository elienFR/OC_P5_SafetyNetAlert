package org.safetynet.p5safetynetalert.dbapi.service.initPersist;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.Allergy;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.dbapi.repository.AllergyRepository;
import org.safetynet.p5safetynetalert.dbapi.repository.initPersist.JsonPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Data
@Service
public class JsonDataInjectorServiceImpl implements JsonDataInjectorService {

  @Autowired
  JsonFileExtractorService jsonFileExtractorService;

  //Repositories to inject data into databases
  @Autowired
  JsonPersonRepository jsonPersonRepository;
  @Autowired
  AllergyRepository allergyRepository;


  private JsonData jsonData;

  /**
   * File name of the file to be imported. It's location must be in src/main/resources/ .
   */
  private String fileName = "data.json";

  @Override
  public void initDb() {
    //Extracting file
    jsonData = jsonFileExtractorService.fromFile(fileName);

    importAllergies();
    importFireStations();
    importAddresses();
    importMedications();
    importPersons();
    importPersonsAllergies();
    importPersonsMedications();


    System.out.println("test");
  }

  private void importAllergies() {
    Set<String> mySet = new TreeSet<>();
    List<Allergy> myList = new ArrayList<>();

    //create the allergies' set
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

    //prepare unique allergies' list to insert into DataBase
    for (String allergy : mySet) {
      myList.add(new Allergy(allergy));
    }

    //Import in H2 DB
    allergyRepository.saveAll(myList);
  }

  private void importAddresses() {

  }

  private void importPersons() {
    jsonPersonRepository.saveAll(jsonData.getPersons().getPersons());
  }

  private void importFireStations() {
    Set<String> mySet = new TreeSet<>();
    List<Allergy> myList = new ArrayList<>();

    //create the allergies' set
    int length = jsonData.getMedicalRecords().getMedicalrecords().size();
    for (int i = 0; i < length; i++) {
      int allergiesLength = jsonData.getMedicalRecords().getMedicalrecords().get(i).getAllergies()
          .size();
      for (int j = 0; j < allergiesLength; j++) {
        String stringToAdd = jsonData.getMedicalRecords()
            .getMedicalrecords()
            .get(i)
            .getAllergies()
            .get(j);
        mySet.add(stringToAdd);
      }
    }

    //prepare unique allergies' list to insert into DataBase
    for (String allergy : mySet) {
      myList.add(new Allergy(allergy));
    }

    //Import in H2 DB
    allergyRepository.saveAll(myList);
  }

  private void importMedications() {

  }

  private void importPersonsMedications() {
  }

  private void importPersonsAllergies() {
  }

}
