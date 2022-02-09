package org.safetynet.p5safetynetalert.service.initPersist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.data.JsonDataGenerator;
import org.safetynet.p5safetynetalert.model.entity.PersonsMedication;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.repository.*;
import org.safetynet.p5safetynetalert.service.initPersist.utils.PersonsMedicationImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonsMedicationImportServiceTest {

  @Autowired
  private PersonsMedicationImportService personsMedicationImportServiceUnderTest;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorServiceMocked;
  @MockBean
  private PersonsMedicationRepository personsMedicationRepositoryMocked;
  @MockBean
  private MedicationRepository medicationRepositoryMocked;
  @MockBean
  private PersonRepository personRepositoryMocked;

  private JsonData jsonData;

  private JsonDataGenerator jsonDataGenerator = new JsonDataGenerator();

  @BeforeEach
  public void setup() {
    jsonData = jsonDataGenerator.generate();
  }

  @Test
  public void importPersonsAllergiesTest() {
    //when
    personsMedicationImportServiceUnderTest.importPersonsMedications(jsonData);

    //then
    verify(personRepositoryMocked, Mockito.atLeastOnce()).findByFirstNameAndLastNameAndBirthDate(anyString(),anyString(),anyString());
    verify(medicationRepositoryMocked,Mockito.atLeastOnce()).findByName(anyString());
    verify(personsMedicationRepositoryMocked, Mockito.atLeastOnce()).save(any(PersonsMedication.class));
  }

}
