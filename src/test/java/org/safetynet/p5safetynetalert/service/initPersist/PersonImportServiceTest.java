package org.safetynet.p5safetynetalert.service.initPersist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.data.JsonDataGenerator;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.repository.AddressRepository;
import org.safetynet.p5safetynetalert.repository.PersonRepository;
import org.safetynet.p5safetynetalert.service.initPersist.utils.PersonImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonImportServiceTest {

  @Autowired
  private PersonImportService personImportServiceUnderTest;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private PersonRepository personRepository;
  @MockBean
  private AddressRepository addressRepositoryMocked;

  private JsonData jsonData;

  private JsonDataGenerator jsonDataGenerator = new JsonDataGenerator();

  @BeforeEach
  public void setup() {
    jsonData = jsonDataGenerator.generate();
  }

  @Test
  public void importPersons() {
    //when
    personImportServiceUnderTest.importPersons(jsonData);

    //then
    verify(addressRepositoryMocked, Mockito.atLeastOnce()).findByRoadAndCityAndZipCode(anyString(), anyString(), anyString());
    verify(personRepository, Mockito.atLeastOnce()).save(any(Person.class));
  }

}