package org.safetynet.p5safetynetalert.data;

import org.safetynet.p5safetynetalert.model.initPersist.*;

import java.util.ArrayList;
import java.util.List;

public class JsonDataGenerator {

  public static JsonData generate() {
    JsonPerson jsonPerson1 = new JsonPerson();
    jsonPerson1.setFirstName("john");
    jsonPerson1.setLastName("doe");
    jsonPerson1.setPhone("somePhone");
    jsonPerson1.setEmail("e@e.com");
    jsonPerson1.setAddress("someRoad");
    jsonPerson1.setCity("someCity");
    jsonPerson1.setZip("someZip");

    List<JsonPerson> jsonPersonList = new ArrayList<>();
    jsonPersonList.add(jsonPerson1);

    JsonPersons jsonPersons = new JsonPersons();
    jsonPersons.setPersons(jsonPersonList);

    JsonFireStation jsonFireStation = new JsonFireStation();
    jsonFireStation.setStation("someStation");
    jsonFireStation.setAddress("someRoad");

    List<JsonFireStation> jsonFireStationList = new ArrayList<>();
    jsonFireStationList.add(jsonFireStation);

    JsonFireStations jsonFireStations = new JsonFireStations();
    jsonFireStations.setFirestations(jsonFireStationList);

    JsonMedicalRecord jsonMedicalRecord = new JsonMedicalRecord();
    jsonMedicalRecord.setFirstName("jenny");
    jsonMedicalRecord.setLastName("Fer");
    jsonMedicalRecord.setBirthdate("someBirthDate");
    jsonMedicalRecord.setAllergies(List.of("one allergy", "twoAllergies"));
    jsonMedicalRecord.setMedications(List.of("one meds", "two meds"));

    List<JsonMedicalRecord> jsonMedicalRecordList = new ArrayList<>();
    jsonMedicalRecordList.add(jsonMedicalRecord);

    JsonMedicalRecords jsonMedicalRecords = new JsonMedicalRecords();
    jsonMedicalRecords.setMedicalrecords(jsonMedicalRecordList);

    JsonData jsonData = new JsonData();
    jsonData.setPersons(jsonPersons);
    jsonData.setFireStations(jsonFireStations);
    jsonData.setMedicalRecords(jsonMedicalRecords);

    return jsonData;
  }
}
