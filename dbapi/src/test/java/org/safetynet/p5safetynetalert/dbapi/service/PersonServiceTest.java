package org.safetynet.p5safetynetalert.dbapi.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonRepository;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    Person expectedPerson = new Person("firstName", "lastName", null, null, null, null);
    when(personRepositoryMocked.findByFirstNameAndLastName(firstName, lastName)).thenReturn(expectedPerson);
    //when
    Person result = personService.getByFirstNameAndLastName(firstName, lastName);
    //then
    assertThat(result).isEqualTo(expectedPerson);
    verify(personRepositoryMocked, Mockito.times(1)).findByFirstNameAndLastName(firstName, lastName);
  }

  @Test
  public void getAllByNameTest() {
    //given
    String lastName = "lastName";
    Iterable<Person> expectedPersons = new ArrayList<>(
      List.of(
        new Person("joe", "lastname", null, null, null, null),
        new Person("june", "lastname", null, null, null, null)
      ));
    when(personRepositoryMocked.findAllByLastName(lastName)).thenReturn(expectedPersons);
    //when
    Iterable<Person> result = personService.getAllByName(lastName);
    //then
    assertThat(result).isEqualTo(expectedPersons);
    verify(personRepositoryMocked, Mockito.times(1)).findAllByLastName(lastName);
  }

  @Test
  public void getEmailsTest() {
    //given
    Collection<Person> givenPersons = new ArrayList<>();
    givenPersons.add(new Person(null, null, null, null, "one mail", null));
    givenPersons.add(new Person(null, null, null, null, "another mail", null));
    givenPersons.add(new Person(null, null, null, null, "last mail", null));

    List<String> expected = new ArrayList<>(
      List.of(
        "one mail",
        "another mail",
        "last mail"
      )
    );
    //when
    List<String> results = personService.getEmails(givenPersons);
    //then
    assertThat(results.contains("one mail")).isTrue();
    assertThat(results.contains("another mail")).isTrue();
    assertThat(results.contains("last mail")).isTrue();
  }

  @Test
  public void getEmailsTestWithEmptyPersons() {
    //given
    Collection<Person> givenPersons = new ArrayList<>();
    //when
    List<String> results = personService.getEmails(givenPersons);
    //then
    assertThat(results).isNull();
  }
}

