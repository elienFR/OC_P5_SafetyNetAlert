package org.safetynet.p5safetynetalert.dbapi.service.initPersist;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.*;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;
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
  @Autowired
  AddressRepository addressRepository;


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

  public void importAddresses() {
    //Comparison set to verify unicity of address
    Set<String> comparisonSet = new TreeSet<>();

    //create three independent set of road city and zip from persons
    for (JsonPerson jsonPerson : jsonData.getPersons().getPersons()) {
      String road = jsonPerson.getAddress();
      String city = jsonPerson.getCity();
      String zip = jsonPerson.getZip();
      String addressKey = road+city+zip;
      Address addressToAdd = new Address();

      if(!comparisonSet.contains(addressKey)){
        for(JsonFireStation fireStation : jsonData.getFireStations().getFirestations()){
          if(fireStation.getAddress().equals(jsonPerson.getAddress())){
            addressToAdd.setRoad(road);
            addressToAdd.setCity(city);
            addressToAdd.setZipCode(zip);
            addressToAdd.setFireStation(fireStationRepository.findByNumber(fireStation.getStation()));
            addressRepository.save(addressToAdd);
          }
        }
      }
      //it is a set so even if the key already exists keys will be unique, never mind in adding it.
      comparisonSet.add(addressKey);
    }
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
