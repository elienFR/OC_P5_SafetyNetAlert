package org.safetynet.p5safetynetalert.service.initPersist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.data.JsonDataGenerator;
import org.safetynet.p5safetynetalert.model.entity.PersonsAllergy;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.repository.AllergyRepository;
import org.safetynet.p5safetynetalert.repository.PersonRepository;
import org.safetynet.p5safetynetalert.repository.PersonsAllergyRepository;
import org.safetynet.p5safetynetalert.service.initPersist.utils.PersonsAllergyImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonsAllergyImportServiceTest {

  @Autowired
  private PersonsAllergyImportService personsAllergyImportServiceUnderTest;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorServiceMocked;
  @MockBean
  private PersonsAllergyRepository personsAllergyRepositoryMocked;
  @MockBean
  private AllergyRepository allergyRepositoryMocked;
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
    personsAllergyImportServiceUnderTest.importPersonsAllergies(jsonData);

    //then
    verify(personRepositoryMocked, Mockito.atLeastOnce()).findByFirstNameAndLastNameAndBirthDate(anyString(),anyString(),anyString());
    verify(allergyRepositoryMocked,Mockito.atLeastOnce()).findByName(anyString());
    verify(personsAllergyRepositoryMocked, Mockito.atLeastOnce()).save(any(PersonsAllergy.class));
  }
}
