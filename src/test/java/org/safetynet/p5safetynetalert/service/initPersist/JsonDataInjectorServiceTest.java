package org.safetynet.p5safetynetalert.service.initPersist;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.safetynet.p5safetynetalert.model.initPersist.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JsonDataInjectorServiceTest {

  private static IJsonDataInjectorService iJsonDataInjectorServiceUnderTest;

  @Mock
  private static IJsonFileExtractorService iJsonFileExtractorServiceMocked;

  private static JsonData jsonData;

  @BeforeAll
  public static void init() {
    JsonPerson jsonPerson1 = new JsonPerson();
    jsonPerson1.setFirstName("john");
    jsonPerson1.setLastName("doe");
    jsonPerson1.setPhone("somePhone");
    jsonPerson1.setEmail("e@e.com");
    jsonPerson1.setCity("someCity");
    jsonPerson1.setZip("someZip");

    List<JsonPerson> jsonPersonList = new ArrayList<>();
    jsonPersonList.add(jsonPerson1);

    JsonPersons jsonPersons = new JsonPersons();
    jsonPersons.setPersons(jsonPersonList);

    JsonFireStation jsonFireStation = new JsonFireStation();
    jsonFireStation.setStation("someStation");
    jsonFireStation.setAddress("someAddress");

    List<JsonFireStation> jsonFireStationList = new ArrayList<>();
    jsonFireStationList.add(jsonFireStation);

    JsonFireStations jsonFireStations = new JsonFireStations();
    jsonFireStations.setFirestations(jsonFireStationList);

    JsonMedicalRecord jsonMedicalRecord = new JsonMedicalRecord();
    jsonMedicalRecord.setFirstName("jenny");
    jsonMedicalRecord.setLastName("Fer");
    jsonMedicalRecord.setBirthdate("someBirthDate");
    jsonMedicalRecord.setAllergies(List.of("one allergy","twoAllergies"));
    jsonMedicalRecord.setMedications(List.of("one meds","two meds"));

    List<JsonMedicalRecord> jsonMedicalRecordList = new ArrayList<>();
    jsonMedicalRecordList.add(jsonMedicalRecord);

    JsonMedicalRecords jsonMedicalRecords = new JsonMedicalRecords();
    jsonMedicalRecords.setMedicalrecords(jsonMedicalRecordList);

    jsonData = new JsonData();
    jsonData.setPersons(jsonPersons);
    jsonData.setFireStations(jsonFireStations);
    jsonData.setMedicalRecords(jsonMedicalRecords);
  }

  @BeforeEach
  public void setup(){
    when(iJsonFileExtractorServiceMocked.fromFile("test")).thenReturn(jsonData);
  }

  @Disabled
  @Test
  public void test1(){
    iJsonDataInjectorServiceUnderTest = new JsonDataInjectorServiceImpl();
    iJsonDataInjectorServiceUnderTest.initDb();
    System.out.println("print test");
  }

}
