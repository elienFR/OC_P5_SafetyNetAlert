package org.safetynet.p5safetynetalert.dbapi.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.dto.MedicalRecordsDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonForFireDTO;
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
    assertThat(results.size()).isEqualTo(3);
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

  @Test
  public void getPhonesTest() {
    //given
    Collection<Person> givenPersons = new ArrayList<>();
    givenPersons.add(new Person(null, null, null, "111", null, null));
    givenPersons.add(new Person(null, null, null, "222", null, null));
    givenPersons.add(new Person(null, null, null, "333", null, null));
    Collection<String> expected = new ArrayList<>(
      List.of(
        "111",
        "222",
        "333"
      )
    );
    //when
    Collection<String> results = personService.getPhones(givenPersons);
    //then
    assertThat(results.contains("111")).isTrue();
    assertThat(results.contains("222")).isTrue();
    assertThat(results.contains("333")).isTrue();
    assertThat(results.size()).isEqualTo(3);
  }

  @Test
  public void convertPersonsInPersonForFireDTOTest() {
    //given
    Collection<Person> givenPersons = new ArrayList<>();
    givenPersons.add(new Person("john", "doe", null, "111", null, null));
    givenPersons.add(new Person("june", "die", null, "222", null, null));
    givenPersons.add(new Person("ji", "lo", null, "333", null, null));

    List<MedicalRecordsDTO> medicalRecordsDTOList = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      medicalRecordsDTOList.add(new MedicalRecordsDTO());
    }

    int j = 0;
    for (Person person : givenPersons) {
      when(iMedicalRecordsServiceMocked.getMedicalRecords(person)).thenReturn(medicalRecordsDTOList.get(j));
      j++;
    }

    PersonForFireDTO expected1 = new PersonForFireDTO();
    expected1.setFirstName("john");
    expected1.setLastName("doe");
    expected1.setPhone("111");
    expected1.setMedicalRecords(medicalRecordsDTOList.get(0));
    PersonForFireDTO expected2 = new PersonForFireDTO();
    expected2.setFirstName("june");
    expected2.setLastName("die");
    expected2.setPhone("222");
    expected2.setMedicalRecords(medicalRecordsDTOList.get(1));
    PersonForFireDTO expected3 = new PersonForFireDTO();
    expected3.setFirstName("ji");
    expected3.setLastName("lo");
    expected3.setPhone("333");
    expected3.setMedicalRecords(medicalRecordsDTOList.get(2));

    //when
    Collection<PersonForFireDTO> results = personService.convertPersonsInPersonForFireDTO(givenPersons);

    //then
    assertThat(results.size()).isEqualTo(3);
    assertThat(results.contains(expected1)).isTrue();
    assertThat(results.contains(expected2)).isTrue();
    assertThat(results.contains(expected3)).isTrue();
  }

  @Test
  public void getPersonDTOsFromAddressesTest() {

  }
  @Disabled
  @Test
  public void getPersonsForFloodTest() {

  }
  @Disabled
  @Test
  public void getAdultsDTOTest() {

  }
  @Disabled
  @Test
  public void getChildrenDTOTest() {

  }
  @Disabled
  @Test
  public void getPersonsFromAddressTest() {

  }
  @Disabled
  @Test
  public void getPersonsFromAddressesTest() {

  }
  @Disabled
  @Test
  public void createPersonTest() {

  }
  @Disabled
  @Test
  public void updatePersonWithJsonPersonTest() {

  }
  @Disabled
  @Test
  public void deleteTest() {

  }
  @Disabled
  @Test
  public void createMedicalRecordsTest() {

  }
  @Disabled
  @Test
  public void updateMedicalRecordsTest() {

  }
  @Disabled
  @Test
  public void deleteMedicalRecordsTest() {

  }

}

