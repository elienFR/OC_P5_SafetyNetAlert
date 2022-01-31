package org.safetynet.p5safetynetalert.dbapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.dto.MedicalRecordsDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.*;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MedicalRecordsServiceTest {

  @Autowired
  private IMedicalRecordsService iMedicalRecordsService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorServiceMocked;
  @MockBean
  private IPersonsAllergyService iPersonsAllergyServiceMocked;
  @MockBean
  private IPersonsMedicationService iPersonsMedicationServiceMocked;
  @MockBean
  private MedicationService medicationServiceMocked;
  @MockBean
  private AllergyService allergyServiceMocked;

  @Test
  public void getMedicalRecords() {
    //given
    Person givenPerson = new Person();
    List<String> givenMedications = new ArrayList<>();
    List<String> givenAllergies = new ArrayList<>();
    MedicalRecordsDTO expectedMedicalRecords = new MedicalRecordsDTO();
    expectedMedicalRecords.setMedications(givenMedications);
    expectedMedicalRecords.setAllergies(givenAllergies);
    when(iPersonsMedicationServiceMocked.getMedicationsFromPersonsMedications(
      givenPerson.getPersonsMedications())).thenReturn(givenMedications);
    when(iPersonsAllergyServiceMocked.getAllergiesFromPersonsMedications(
      givenPerson.getPersonsAllergies())).thenReturn(givenAllergies);

    //when
    MedicalRecordsDTO result = iMedicalRecordsService.getMedicalRecords(givenPerson);

    //then
    assertThat(result).isEqualTo(expectedMedicalRecords);
    assertThat(result.getMedications()).isEqualTo(givenMedications);
    assertThat(result.getAllergies()).isEqualTo(givenAllergies);
  }

  @Test
  public void existsFromPersonTest() {
    //given
    Person givenPerson = new Person("john", "doe", "1/1/1", null, null, null);

    List<String> givenMedications = new ArrayList<>();
    givenMedications.add("some meds");
    List<String> givenAllergies = new ArrayList<>();
    givenAllergies.add("some allergies");

    when(iPersonsMedicationServiceMocked.getMedicationsFromPersonsMedications(
      givenPerson.getPersonsMedications())).thenReturn(givenMedications);
    when(iPersonsAllergyServiceMocked.getAllergiesFromPersonsMedications(
      givenPerson.getPersonsAllergies())).thenReturn(givenAllergies);

    //when
    boolean result = iMedicalRecordsService.existsFromPerson(givenPerson);

    //then
    assertThat(result).isTrue();
  }

  @Test
  public void existsFromPersonTestBlankBirthDate() {
    //given
    Person givenPerson = new Person("john", "doe", "", null, null, null);

    List<String> givenMedications = new ArrayList<>();
    givenMedications.add("some meds");
    List<String> givenAllergies = new ArrayList<>();
    givenAllergies.add("some allergies");

    when(iPersonsMedicationServiceMocked.getMedicationsFromPersonsMedications(
      givenPerson.getPersonsMedications())).thenReturn(givenMedications);
    when(iPersonsAllergyServiceMocked.getAllergiesFromPersonsMedications(
      givenPerson.getPersonsAllergies())).thenReturn(givenAllergies);

    //when
    boolean result = iMedicalRecordsService.existsFromPerson(givenPerson);

    //then
    assertThat(result).isTrue();
  }

  @Test
  public void existsFromPersonTestNullBirthDate() {
    //given
    Person givenPerson = new Person("john", "doe", null, null, null, null);

    List<String> givenMedications = new ArrayList<>();
    givenMedications.add("some meds");
    List<String> givenAllergies = new ArrayList<>();
    givenAllergies.add("some allergies");

    when(iPersonsMedicationServiceMocked.getMedicationsFromPersonsMedications(
      givenPerson.getPersonsMedications())).thenReturn(givenMedications);
    when(iPersonsAllergyServiceMocked.getAllergiesFromPersonsMedications(
      givenPerson.getPersonsAllergies())).thenReturn(givenAllergies);

    //when
    boolean result = iMedicalRecordsService.existsFromPerson(givenPerson);

    //then
    assertThat(result).isTrue();
  }

  @Test
  public void existsFromPersonTestNoMeds() {
    //given
    Person givenPerson = new Person("john", "doe", "1/1/1", null, null, null);

    List<String> givenMedications = new ArrayList<>();
    List<String> givenAllergies = new ArrayList<>();
    givenAllergies.add("some allergies");

    when(iPersonsMedicationServiceMocked.getMedicationsFromPersonsMedications(
      givenPerson.getPersonsMedications())).thenReturn(givenMedications);
    when(iPersonsAllergyServiceMocked.getAllergiesFromPersonsMedications(
      givenPerson.getPersonsAllergies())).thenReturn(givenAllergies);

    //when
    boolean result = iMedicalRecordsService.existsFromPerson(givenPerson);

    //then
    assertThat(result).isTrue();
  }

  @Test
  public void existsFromPersonTestNoAllergies() {
    //given
    Person givenPerson = new Person("john", "doe", "1/1/1", null, null, null);

    List<String> givenMedications = new ArrayList<>();
    givenMedications.add("some meds");
    List<String> givenAllergies = new ArrayList<>();

    when(iPersonsMedicationServiceMocked.getMedicationsFromPersonsMedications(
      givenPerson.getPersonsMedications())).thenReturn(givenMedications);
    when(iPersonsAllergyServiceMocked.getAllergiesFromPersonsMedications(
      givenPerson.getPersonsAllergies())).thenReturn(givenAllergies);

    //when
    boolean result = iMedicalRecordsService.existsFromPerson(givenPerson);

    //then
    assertThat(result).isTrue();
  }

  @Test
  public void existsFromPersonTestFullNull() {
    //given
    Person givenPerson = new Person("john", "doe", null, null, null, null);

    List<String> givenMedications = new ArrayList<>();
    List<String> givenAllergies = new ArrayList<>();

    when(iPersonsMedicationServiceMocked.getMedicationsFromPersonsMedications(
      givenPerson.getPersonsMedications())).thenReturn(givenMedications);
    when(iPersonsAllergyServiceMocked.getAllergiesFromPersonsMedications(
      givenPerson.getPersonsAllergies())).thenReturn(givenAllergies);

    //when
    boolean result = iMedicalRecordsService.existsFromPerson(givenPerson);

    //then
    assertThat(result).isFalse();
  }



  @Test
  public void createMedicationsFromJsonPersonTest() {
    //given
    JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setMedications(List.of("first meds exists", "second meds does not exist"));

    Medication expectedMedicationToFind = new Medication("first meds");
    Medication expectedMedicationToSave = new Medication("second meds does not exist");

    //here we consider the medication already exists
    when(medicationServiceMocked.existsByName(
      givenJsonMedicalRecord.getMedications().get(0)
    )).thenReturn(true);
    when(medicationServiceMocked.existsByName(
      givenJsonMedicalRecord.getMedications().get(1)
    )).thenReturn(false);
    when(medicationServiceMocked.getByName(
      givenJsonMedicalRecord.getMedications().get(0)
    )).thenReturn(expectedMedicationToFind);
    when(medicationServiceMocked.getByName(
      givenJsonMedicalRecord.getMedications().get(1)
    )).thenReturn(expectedMedicationToSave);
    when(medicationServiceMocked.save(new Medication("second meds does not exist")
    )).thenReturn(expectedMedicationToSave);

    Person givenPerson = new Person("john", "doe", "1/1/1", null, null, null);
    PersonsMedication expectedPersonsMedicationToSave1 = new PersonsMedication();
    expectedPersonsMedicationToSave1.setPerson(givenPerson);
    expectedPersonsMedicationToSave1.setMedication(expectedMedicationToFind);
    PersonsMedication expectedPersonsMedicationToSave2 = new PersonsMedication();
    expectedPersonsMedicationToSave2.setPerson(givenPerson);
    expectedPersonsMedicationToSave2.setMedication(expectedMedicationToSave);

    //when
    iMedicalRecordsService.createMedicationsFromJsonPerson(givenJsonMedicalRecord, givenPerson);

    //then
    verify(medicationServiceMocked, Mockito.times(1))
      .existsByName("first meds exists");
    verify(medicationServiceMocked, Mockito.times(1))
      .getByName("first meds exists");
    verify(medicationServiceMocked, Mockito.times(1))
      .existsByName("second meds does not exist");
    verify(medicationServiceMocked, Mockito.times(1))
      .save(any(Medication.class));
    verify(iPersonsMedicationServiceMocked, Mockito.times(2))
      .save(any(PersonsMedication.class));
  }

  @Test
  public void createAllergiesFromJsonPersonTest() {
    //given
    JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setAllergies(List.of("first allergy exists", "second allergy does not exist"));

    Allergy expectedAllergyToFind = new Allergy("first allergy exists");
    Allergy expectedAllergyToSave = new Allergy("second allergy does not exist");

    //here we consider the medication already exists
    when(allergyServiceMocked.exists(
      givenJsonMedicalRecord.getAllergies().get(0)
    )).thenReturn(true);
    when(allergyServiceMocked.exists(
      givenJsonMedicalRecord.getAllergies().get(1)
    )).thenReturn(false);
    when(allergyServiceMocked.getByName(
      givenJsonMedicalRecord.getAllergies().get(0)
    )).thenReturn(expectedAllergyToFind);
    when(allergyServiceMocked.getByName(
      givenJsonMedicalRecord.getAllergies().get(1)
    )).thenReturn(expectedAllergyToSave);
    when(allergyServiceMocked.save(new Allergy("second allergy does not exist")
    )).thenReturn(expectedAllergyToSave);

    Person givenPerson = new Person("john", "doe", "1/1/1", null, null, null);
    PersonsAllergy expectedPersonsAllergyToSave1 = new PersonsAllergy();
    expectedPersonsAllergyToSave1.setPerson(givenPerson);
    expectedPersonsAllergyToSave1.setAllergy(expectedAllergyToFind);
    PersonsAllergy expectedPersonsAllergyToSave2 = new PersonsAllergy();
    expectedPersonsAllergyToSave2.setPerson(givenPerson);
    expectedPersonsAllergyToSave2.setAllergy(expectedAllergyToSave);

    //when
    iMedicalRecordsService.createAllergiesFromJsonPerson(givenJsonMedicalRecord, givenPerson);

    //then
    verify(allergyServiceMocked, Mockito.times(1))
      .exists("first allergy exists");
    verify(allergyServiceMocked, Mockito.times(1))
      .getByName("first allergy exists");
    verify(allergyServiceMocked, Mockito.times(1))
      .exists("second allergy does not exist");
    verify(allergyServiceMocked, Mockito.times(1))
      .save(any(Allergy.class));
    verify(iPersonsAllergyServiceMocked, Mockito.times(2))
      .save(any(PersonsAllergy.class));
  }

  @Test
  public void deletePersonsMedicationsFromPersonTest() {
    //given
    Person givenPerson = new Person();
    //when
    iMedicalRecordsService.deletePersonsMedicationsFromPerson(givenPerson);
    //then
    verify(iPersonsMedicationServiceMocked,Mockito.times(1))
      .deleteAllFromPerson(givenPerson);
  }
  @Test
  public void deletePersonsAllergiesFromPersonTest() {
    //given
    Person givenPerson = new Person();
    //when
    iMedicalRecordsService.deletePersonsAllergiesFromPerson(givenPerson);
    //then
    verify(iPersonsAllergyServiceMocked,Mockito.times(1))
      .deleteAllFromPerson(givenPerson);
  }


}
