package org.safetynet.p5safetynetalert.service.initPersist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.model.entity.*;
import org.safetynet.p5safetynetalert.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JsonDataInjectorServiceImpl implements IJsonDataInjectorService {

  @Autowired
  IJsonFileExtractorService IJsonFileExtractorService;
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
  @Autowired
  PersonsMedicationRepository personsMedicationRepository;
  @Autowired
  PersonsAllergyRepository personsAllergyRepository;
  private static final Logger LOGGER = LogManager.getLogger(JsonDataInjectorServiceImpl.class);
  private JsonData jsonData;

  /**
   * File name of the file to be imported. It's location must be in src/main/resources/ .
   */
  private String fileName = "data.json";


  /**
   * Script that initialise the database
   */
  @Override
  public void initDb() {
    LOGGER.debug("Starting database initialisation.");

    //Extracting file
    jsonData = IJsonFileExtractorService.fromFile(fileName);

    if (jsonData == null) {
      LOGGER.error("The object jsonData from json file is null.");
      throw new NullPointerException("JsonData is null. information cannot be imported.");
    } else {
      importAllergies();
      importFireStations();
      importAddresses();
      importMedications();
      importPersons();
      importPersonsMedications();
      importPersonsAllergies();
      LOGGER.debug("Database initialisation complete.");
    }
  }

  /**
   * script that import allergies from a jsondata object
   */
  private void importAllergies() {
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

  /**
   * script that import addresses from a jsondata object
   */
  private void importAddresses() {
    LOGGER.debug("importing addresses...");

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

  /**
   * script that import persons from a jsondata object
   */
  private void importPersons() {
    LOGGER.debug("importing persons...");

    Set<String> mySet = new TreeSet<>();
    for (JsonPerson jsonPerson : jsonData.getPersons().getPersons()) {
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
      Address addressToAdd = addressRepository.findByRoadAndCityAndZipCode(road, city, zip);
      personToAdd.setAddress(addressToAdd);

      //Setting BirthDate
      for (JsonMedicalRecord jsonMedicalRecord : jsonData.getMedicalRecords().getMedicalrecords()) {
        if (jsonMedicalRecord.getFirstName().equals(jsonPerson.getFirstName())
            && jsonMedicalRecord.getLastName().equals(jsonPerson.getLastName())) {
          personToAdd.setBirthDate(jsonMedicalRecord.getBirthdate());
          break;
        }
      }

      //Uniqueness of each people imported
      String uniqueKey = jsonPerson.getFirstName() + jsonPerson.getLastName() + jsonPerson.getAddress();
      if (!mySet.contains(uniqueKey)) {
        personRepository.save(personToAdd);
      }
      mySet.add(uniqueKey);
    }
  }

  /**
   * script that import fire stations from a json data object
   */
  private void importFireStations() {
    LOGGER.debug("importing fire stations...");

    Set<String> mySet = new TreeSet<>();
    for (JsonFireStation jsonFireStation : jsonData.getFireStations().getFirestations()) {
      if (!mySet.contains(jsonFireStation.getStation())) {
        FireStation fireStationToSave = new FireStation();
        fireStationToSave.setNumber(jsonFireStation.getStation());
        fireStationRepository.save(fireStationToSave);
      }
      mySet.add(jsonFireStation.getStation());
    }
  }

  /**
   * script that import medications from a json data object
   */
  private void importMedications() {
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

  /**
   * script that import persons medications from a json data object
   */
  private void importPersonsMedications() {
    LOGGER.debug("importing person's medications...");

    Set<String> mySet = new TreeSet<>();
    //Analyse each line in data.json concerning Medical records
    for (JsonMedicalRecord jsonMedicalRecord : jsonData.getMedicalRecords().getMedicalrecords()) {
      //Constructing unique key
      String firstName = jsonMedicalRecord.getFirstName();
      String lastName = jsonMedicalRecord.getLastName();
      String birthDate = jsonMedicalRecord.getBirthdate();
      String uniqueKey = firstName + lastName + birthDate;

      //if json object is unique
      if (!mySet.contains(uniqueKey)) {
        //Recover the already existing person from database regarding first/last name and birthday
        Person personToAdd = personRepository
            .findByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);

        //Recover each medication for one person
        for (String medicationName : jsonMedicalRecord.getMedications()) {
          Medication medicationToAdd = medicationRepository.findByName(medicationName);
          PersonsMedication personsMedicationToSave = new PersonsMedication();
          personsMedicationToSave.setPerson(personToAdd);
          personsMedicationToSave.setMedication(medicationToAdd);
          personsMedicationRepository.save(personsMedicationToSave);
        }
      }

      mySet.add(uniqueKey);
    }
  }

  /**
   * Scrip that import person's allergies from a json data object
   */
  private void importPersonsAllergies() {
    LOGGER.debug("importing person's allergies...");

    Set<String> mySet = new TreeSet<>();
    //Analyse each line in data.json concerning Medical records
    for (JsonMedicalRecord jsonMedicalRecord : jsonData.getMedicalRecords().getMedicalrecords()) {
      //Constructing unique key
      String firstName = jsonMedicalRecord.getFirstName();
      String lastName = jsonMedicalRecord.getLastName();
      String birthDate = jsonMedicalRecord.getBirthdate();
      String uniqueKey = firstName + lastName + birthDate;

      //if json object is unique
      if (!mySet.contains(uniqueKey)) {
        //Recover the already existing person from database regarding first/last name and birthday
        Person personToAdd = personRepository
            .findByFirstNameAndLastNameAndBirthDate(firstName, lastName, birthDate);

        //Recover each allergy for one person
        for (String allergy : jsonMedicalRecord.getAllergies()) {
          Allergy allergyToAdd = allergyRepository.findByName(allergy);
          PersonsAllergy personsAllergyToSave = new PersonsAllergy();
          personsAllergyToSave.setPerson(personToAdd);
          personsAllergyToSave.setAllergy(allergyToAdd);
          personsAllergyRepository.save(personsAllergyToSave);
        }
      }
      mySet.add(uniqueKey);
    }

  }

}
