package org.safetynet.p5safetynetalert.dbapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonRepository;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceTest {

  @Autowired
  private PersonService personService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private PersonRepository personRepositoryMocked;
  @MockBean
  private AgeService ageServiceMocked;
  @MockBean
  private IPersonsMedicationService iPersonsMedicationServiceMocked;
  @MockBean
  private IPersonsAllergyService iPersonsAllergyServiceMocked;
  @MockBean
  private IMedicalRecordsService iMedicalRecordsServiceMocked;
  @MockBean
  private IAddressService iAddressServiceMocked;

  @Test
  public void getByFirstNameAndLastNameTest() {
    //given
    String firstName = "firstName";
    String lastName = "lastName";
    Person expectedPerson = new Person("firstName","lastName",null,null,null,null);
    when(personRepositoryMocked.findByFirstNameAndLastName(firstName,lastName)).thenReturn(expectedPerson);
    //when
    Person result = personService.getByFirstNameAndLastName(firstName,lastName);
    //then
    assertThat(result).isEqualTo(expectedPerson);
    verify(personRepositoryMocked, Mockito.times(1)).findByFirstNameAndLastName(firstName,lastName);
  }


}
