package org.safetynet.p5safetynetalert.dbapi.service.initPersist;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.*;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.dbapi.repository.AllergyRepository;
import org.safetynet.p5safetynetalert.dbapi.repository.FireStationRepository;
import org.safetynet.p5safetynetalert.dbapi.repository.MedicationRepository;
import org.safetynet.p5safetynetalert.dbapi.repository.initPersist.JsonPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  @Autowired
  FireStationRepository fireStationRepository;
  @Autowired
  MedicationRepository medicationRepository;


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

    importJsonPersons();
  }


  private void importAllergies() {
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
      myList.add(new Allergy(allergy));
    }

    //Import in H2 DB
    allergyRepository.saveAll(myList);
  }

  private void importAddresses() {
    Set<String> mySetOfRoad = new TreeSet<>();
    Set<String> mySetOfCity = new TreeSet<>();
    Set<String> mySetOfZip = new TreeSet<>();
    List<Address> myList = new ArrayList<>();

    //create a unique set of road
    //create three independent set of road city and zip from persons
    int lenght = jsonData.getPersons().getPersons().size();
    for (int i = 0; i < lenght; i++) {
      String road = jsonData.getPersons().getPersons().get(i).getAddress();
      String city = jsonData.getPersons().getPersons().get(i).getCity();
      String zip = jsonData.getPersons().getPersons().get(i).getZip();

      mySetOfRoad.add(road);
      mySetOfCity.add(city);
      mySetOfZip.add(zip);
    }

    //create set of road from firestations
    int length = jsonData.getFireStations().getFirestations().size();
    for (int i = 0; i < length; i++) {
      String road = jsonData.getFireStations().getFirestations().get(i).getAddress();
      mySetOfRoad.add(road);
    }

    //TODO : fix this part with a list of unique triplet address
    JsonPerson personAnalysed = jsonData.getPersons().getPersons().get(i);
    for (String roadFromSet : mySetOfRoad) {
      for (String cityFormSet : mySetOfCity) {
        for (String zipFromSet : mySetOfZip) {
          if (personAnalysed.getAddress().equals(roadFromSet)
              && personAnalysed.getCity().equals(cityFormSet)
              && personAnalysed.getZip().equals(zipFromSet)) {
            myList.add(new Address(roadFromSet,cityFormSet,zipFromSet,null));
          }
        }
      }
    }


    //create list
    System.out.println("test");

    //associates firestation

    //import into db
  }

  private void importPersons() {
  }

  private void importJsonPersons() {
    jsonPersonRepository.saveAll(jsonData.getPersons().getPersons());
  }

  private void importFireStations() {
    Set<String> mySet = new TreeSet<>();
    List<FireStation> myList = new ArrayList<>();

    //create a unique set
    int length = jsonData.getFireStations().getFirestations().size();
    for (int i = 0; i < length; i++) {
      String stringToAdd = jsonData.getFireStations()
          .getFirestations()
          .get(i)
          .getStation();
      mySet.add(stringToAdd);
    }

    //generate proper list to insert into DataBase
    for (String fireStationNumber : mySet) {
      myList.add(new FireStation(fireStationNumber));
    }

    //Import in H2 DB
    fireStationRepository.saveAll(myList);
  }

  private void importMedications() {
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
      myList.add(new Medication(medicationName));
    }

    //Import in H2 DB
    medicationRepository.saveAll(myList);
  }

  private void importPersonsMedications() {
  }

  private void importPersonsAllergies() {
  }

}
