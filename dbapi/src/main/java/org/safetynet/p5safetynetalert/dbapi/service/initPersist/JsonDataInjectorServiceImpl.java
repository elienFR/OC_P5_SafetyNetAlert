package org.safetynet.p5safetynetalert.dbapi.service.initPersist;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.*;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.dbapi.repository.*;
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
  AllergyRepository allergyRepository;
  @Autowired
  FireStationRepository fireStationRepository;
  @Autowired
  MedicationRepository medicationRepository;
  @Autowired
  AddressRepository addressRepository;
  @Autowired
  PersonRepository personRepository;


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
    //Comparison set to verify uniqueness of address
    Set<String> comparisonSet = new TreeSet<>();

    //create three independent set of road city and zip from persons
    for (JsonPerson jsonPerson : jsonData.getPersons().getPersons()) {
      String road = jsonPerson.getAddress();
      String city = jsonPerson.getCity();
      String zip = jsonPerson.getZip();
      String addressKey = road + city + zip;
      Address addressToAdd = new Address();

      if (!comparisonSet.contains(addressKey)) {
        for (JsonFireStation fireStation : jsonData.getFireStations().getFirestations()) {
          if (fireStation.getAddress().equals(jsonPerson.getAddress())) {
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
    Set<String> mySet = new TreeSet<>();
    for(JsonPerson jsonPerson : jsonData.getPersons().getPersons()) {
      Person personToAdd = new Person();
      //Setting FirstName, LastName, Phone, Email
      personToAdd.setFirstName(jsonPerson.getFirstName());
      personToAdd.setLastName(jsonPerson.getLastName());
      personToAdd.setPhone(jsonPerson.getPhone());
      personToAdd.setEmail(jsonPerson.getEmail());

      //Setting the Address
      String road = jsonPerson.getAddress();
      String city = jsonPerson.getCity();
      String zip = jsonPerson.getZip();
      Address addressToAdd = addressRepository.findByRoadAndCityAndZipCode(road,city,zip);
      personToAdd.setAddress(addressToAdd);

      //Setting BirthDate
      for (JsonMedicalRecord jsonMedicalRecord : jsonData.getMedicalRecords().getMedicalrecords()) {
        if(jsonMedicalRecord.getFirstName().equals(jsonPerson.getFirstName())
        && jsonMedicalRecord.getLastName().equals(jsonPerson.getLastName())){
          personToAdd.setBirthDate(jsonMedicalRecord.getBirthdate());
          break;
        }
      }

      //Uniqueness of each people imported
      String uniqueKey = jsonPerson.getFirstName()+jsonPerson.getLastName()+jsonPerson.getAddress();
      if(!mySet.contains(uniqueKey)) {
        personRepository.save(personToAdd);
      }
      mySet.add(uniqueKey);
    }
  }

  private void importFireStations() {
    Set<String> mySet = new TreeSet<>();
    for (JsonFireStation jsonFireStation : jsonData.getFireStations().getFirestations()) {
      if (!mySet.contains(jsonFireStation.getStation())) {
        fireStationRepository.save(new FireStation(jsonFireStation.getStation()));
      }
      mySet.add(jsonFireStation.getStation());
    }
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
