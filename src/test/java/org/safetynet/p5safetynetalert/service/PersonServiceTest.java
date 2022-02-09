package org.safetynet.p5safetynetalert.service;

import org.apache.maven.doxia.site.decoration.inheritance.URIPathDescriptor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.model.dto.*;
import org.safetynet.p5safetynetalert.model.entity.Address;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.entity.PersonsAllergy;
import org.safetynet.p5safetynetalert.model.entity.PersonsMedication;
import org.safetynet.p5safetynetalert.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.repository.PersonRepository;
import org.safetynet.p5safetynetalert.service.initPersist.IJsonDataInjectorService;
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
  private IPersonService iPersonService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorServiceMocked;
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
    Person expectedPerson = new Person();
    expectedPerson.setFirstName("firstName");
    expectedPerson.setLastName("lastName");
    when(personRepositoryMocked.findByFirstNameAndLastName(firstName, lastName)).thenReturn(expectedPerson);

    //when
    Person result = iPersonService.getByFirstNameAndLastName(firstName, lastName);

    //then
    assertThat(result).isEqualTo(expectedPerson);
    verify(personRepositoryMocked, Mockito.times(1)).findByFirstNameAndLastName(firstName, lastName);
  }

  @Test
  public void getAllByNameTest() {
    //given
    String lastName = "lastName";

    Person expectedPerson1 = new Person();
    expectedPerson1.setFirstName("joe");
    expectedPerson1.setLastName("lastname");

    Person expectedPerson2 = new Person();
    expectedPerson2.setFirstName("joe");
    expectedPerson2.setLastName("lastname");

    Iterable<Person> expectedPersons = new ArrayList<>(
      List.of(expectedPerson1, expectedPerson2));
    when(personRepositoryMocked.findAllByLastName(lastName)).thenReturn(expectedPersons);

    //when
    Iterable<Person> result = iPersonService.getAllByName(lastName);

    //then
    assertThat(result).isEqualTo(expectedPersons);
    verify(personRepositoryMocked, Mockito.times(1)).findAllByLastName(lastName);
  }

  @Test
  public void getEmailsTest() {
    //given
    Person givenPerson1 = new Person();
    givenPerson1.setEmail("one mail");
    Person givenPerson2 = new Person();
    givenPerson2.setEmail("another mail");
    Person givenPerson3 = new Person();
    givenPerson3.setEmail("last mail");
    Collection<Person> givenPersons = new ArrayList<>();
    givenPersons.add(givenPerson1);
    givenPersons.add(givenPerson2);
    givenPersons.add(givenPerson3);

    List<String> expected = new ArrayList<>(List.of("one mail", "another mail", "last mail"));

    //when
    Collection<String> results = iPersonService.getEmails(givenPersons);

    //then
    assertThat(results.containsAll(expected)).isTrue();
    assertThat(results.size()).isEqualTo(expected.size());
  }

  @Test
  public void getEmailsTestWithEmptyPersons() {
    //given
    Collection<Person> givenPersons = new ArrayList<>();
    //when
    Collection<String> results = iPersonService.getEmails(givenPersons);
    //then
    assertThat(results).isNull();
  }

  @Test
  public void getEmailsTestWithNullPersons() {
    //given
    Collection<Person> givenPersons = null;
    //when
    Collection<String> results = iPersonService.getEmails(givenPersons);
    //then
    assertThat(results).isNull();
  }

  @Test
  public void getPhonesTest() {
    //given
    Person givenPerson1 = new Person();
    givenPerson1.setPhone("111");
    Person givenPerson2 = new Person();
    givenPerson2.setPhone("222");
    Person givenPerson3 = new Person();
    givenPerson3.setPhone("333");

    Collection<Person> givenPersons = new ArrayList<>();
    givenPersons.add(givenPerson1);
    givenPersons.add(givenPerson2);
    givenPersons.add(givenPerson3);

    Collection<String> expected = new ArrayList<>(List.of("111", "222", "333"));

    //when
    Collection<String> results = iPersonService.getPhones(givenPersons);

    //then
    assertThat(results.containsAll(expected)).isTrue();
    assertThat(results.size()).isEqualTo(3);
  }

  @Test
  public void convertPersonsInPersonForFireDTOTest() {
    //given
    Person givenPerson1 = new Person();
    givenPerson1.setFirstName("john");
    givenPerson1.setLastName("doe");
    givenPerson1.setPhone("111");
    Person givenPerson2 = new Person();
    givenPerson2.setFirstName("june");
    givenPerson2.setLastName("die");
    givenPerson2.setPhone("222");
    Person givenPerson3 = new Person();
    givenPerson3.setFirstName("ji");
    givenPerson3.setLastName("lo");
    givenPerson3.setPhone("333");
    
    Collection<Person> givenPersons = new ArrayList<>();
    givenPersons.add(givenPerson1);
    givenPersons.add(givenPerson2);
    givenPersons.add(givenPerson3);

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
    Collection<PersonForFireDTO> results = iPersonService.convertPersonsInPersonForFireDTO(givenPersons);

    //then
    assertThat(results.size()).isEqualTo(3);
    assertThat(results.contains(expected1)).isTrue();
    assertThat(results.contains(expected2)).isTrue();
    assertThat(results.contains(expected3)).isTrue();
  }

  @Test
  public void getPersonDTOsFromAddressesTest() {
    //given
    Address givenAddress1 = new Address();
    givenAddress1.setRoad("rue rincais");
    Address givenAddress2 = new Address();
    givenAddress2.setRoad("rue racine");

    Person person1FromAddress1 = new Person();
    person1FromAddress1.setFirstName("jules");
    person1FromAddress1.setAddress(givenAddress1);
    Person person2FromAddress1 = new Person();
    person2FromAddress1.setFirstName("jack");
    person2FromAddress1.setAddress(givenAddress1);
    Person person1FromAddress2 = new Person();
    person1FromAddress2.setFirstName("julie");
    person1FromAddress2.setAddress(givenAddress2);

    givenAddress1.setPersons(List.of(person1FromAddress1, person2FromAddress1));
    givenAddress2.setPersons(List.of(person1FromAddress2));

    Collection<Address> givenAddresses = new ArrayList<>(List.of(givenAddress1, givenAddress2));

    AddressDTO addressDTO1 = new AddressDTO();
    addressDTO1.setRoad("rue rincais");
    AddressDTO addressDTO2 = new AddressDTO();
    addressDTO2.setRoad("rue racine");

    PersonDTO personDTOToAdd1 = new PersonDTO();
    personDTOToAdd1.setFirstName("jules");
    personDTOToAdd1.setAddress(addressDTO1);
    PersonDTO personDTOToAdd2 = new PersonDTO();
    personDTOToAdd2.setFirstName("jack");
    personDTOToAdd2.setAddress(addressDTO1);
    PersonDTO personDTOToAdd3 = new PersonDTO();
    personDTOToAdd3.setFirstName("julie");
    personDTOToAdd3.setAddress(addressDTO2);

    List<PersonDTO> expectedPersonDTOs = new ArrayList<>(
      List.of(personDTOToAdd1, personDTOToAdd2, personDTOToAdd3)
    );
    when(iAddressServiceMocked.convertAddressToAddressDTO(givenAddress1)).thenReturn(addressDTO1);
    when(iAddressServiceMocked.convertAddressToAddressDTO(givenAddress2)).thenReturn(addressDTO2);

    //when
    Collection<PersonDTO> result = iPersonService.getPersonDTOsFromAddresses(givenAddresses);

    //then
    assertThat(result).isEqualTo(expectedPersonDTOs);
  }

  @Test
  public void getPersonsForFloodTest() {
    //given
    //preparation
    Address givenAddress1 = new Address();
    givenAddress1.setRoad("rue rincais");
    Address givenAddress2 = new Address();
    givenAddress2.setRoad("rue racine");

    Person person1FromAddress1 = new Person();
    person1FromAddress1.setFirstName("jules");
    person1FromAddress1.setAddress(givenAddress1);
    Person person2FromAddress1 = new Person();
    person2FromAddress1.setFirstName("jack");
    person2FromAddress1.setAddress(givenAddress1);
    Person person1FromAddress2 = new Person();
    person1FromAddress2.setFirstName("julie");
    person1FromAddress2.setAddress(givenAddress2);

    MedicalRecordsDTO medRecPerson1 = new MedicalRecordsDTO();
    MedicalRecordsDTO medRecPerson2 = new MedicalRecordsDTO();
    MedicalRecordsDTO medRecPerson3 = new MedicalRecordsDTO();
    givenAddress1.setPersons(List.of(person1FromAddress1, person2FromAddress1));
    givenAddress2.setPersons(List.of(person1FromAddress2));
    //given addresses
    Collection<Address> givenAddresses = new ArrayList<>(List.of(givenAddress1, givenAddress2));
    //expected result
    PersonForFloodDTO personForFloodDTOToAdd1 = new PersonForFloodDTO();
    personForFloodDTOToAdd1.setFirstName("jules");
    personForFloodDTOToAdd1.setAge(0);
    personForFloodDTOToAdd1.setMedicalRecords(medRecPerson1);
    PersonForFloodDTO personForFloodDTOToAdd2 = new PersonForFloodDTO();
    personForFloodDTOToAdd2.setFirstName("jack");
    personForFloodDTOToAdd2.setAge(0);
    personForFloodDTOToAdd2.setMedicalRecords(medRecPerson2);
    PersonForFloodDTO personForFloodDTOToAdd3 = new PersonForFloodDTO();
    personForFloodDTOToAdd3.setFirstName("julie");
    personForFloodDTOToAdd3.setAge(0);
    personForFloodDTOToAdd3.setMedicalRecords(medRecPerson3);
    Collection<PersonForFloodDTO> expected = new ArrayList<>(
      List.of(personForFloodDTOToAdd1, personForFloodDTOToAdd2, personForFloodDTOToAdd3)
    );
    //mocking
    when(iMedicalRecordsServiceMocked.getMedicalRecords(person1FromAddress1)).thenReturn(medRecPerson1);
    when(iMedicalRecordsServiceMocked.getMedicalRecords(person2FromAddress1)).thenReturn(medRecPerson2);
    when(iMedicalRecordsServiceMocked.getMedicalRecords(person1FromAddress2)).thenReturn(medRecPerson3);
    //when
    Collection<PersonForFloodDTO> result = iPersonService.getPersonsForFlood(givenAddresses);
    //then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getAdultsDTOTest() {
    //given
    Address givenAddress = new Address();
    givenAddress.setRoad("rue jourdan");
    givenAddress.setCity("nantes");
    givenAddress.setZipCode("44000");

    Person person1 = new Person();
    person1.setFirstName("jude");
    person1.setBirthDate("adult");
    person1.setAddress(givenAddress);
    Person person2 = new Person();
    person2.setFirstName("julia");
    person2.setBirthDate("adult");
    person2.setAddress(givenAddress);
    Person person3 = new Person();
    person3.setFirstName("maria");
    person3.setBirthDate("child");
    person3.setAddress(givenAddress);
    
    givenAddress.setPersons(List.of(person1, person2, person3));

    AddressDTO expectedAddressDTO = new AddressDTO();
    expectedAddressDTO.setRoad("rue jourdan");
    expectedAddressDTO.setCity("nantes");
    expectedAddressDTO.setZip("44000");

    PersonDTO personDTOToAdd1 = new PersonDTO();
    personDTOToAdd1.setFirstName("jude");
    personDTOToAdd1.setBirthDate("adult");
    personDTOToAdd1.setAddress(expectedAddressDTO);
    PersonDTO personDTOToAdd2 = new PersonDTO();
    personDTOToAdd2.setFirstName("julia");
    personDTOToAdd2.setBirthDate("adult");
    personDTOToAdd2.setAddress(expectedAddressDTO);
    Collection<PersonDTO> expected = new ArrayList<>(
      List.of(personDTOToAdd1, personDTOToAdd2)
    );

    when(iAddressServiceMocked.convertAddressToAddressDTO(givenAddress)).thenReturn(expectedAddressDTO);
    when(ageServiceMocked.isStrictlyOverEighteen("adult")).thenReturn(true);
    when(ageServiceMocked.isStrictlyOverEighteen("child")).thenReturn(false);

    //when
    Collection<PersonDTO> result = iPersonService.getAdultsDTO(givenAddress);
    //then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getChildrenDTOTest() {
//given
    Address givenAddress = new Address();
    givenAddress.setRoad("rue jourdan");
    givenAddress.setCity("nantes");
    givenAddress.setZipCode("44000");

    Person person1 = new Person();
    person1.setFirstName("jude");
    person1.setBirthDate("adult");
    person1.setAddress(givenAddress);
    Person person2 = new Person();
    person2.setFirstName("julia");
    person2.setBirthDate("adult");
    person2.setAddress(givenAddress);
    Person person3 = new Person();
    person3.setFirstName("maria");
    person3.setBirthDate("child");
    person3.setAddress(givenAddress);

    givenAddress.setPersons(List.of(person1, person2, person3));

    AddressDTO expectedAddressDTO = new AddressDTO();
    expectedAddressDTO.setRoad("rue jourdan");
    expectedAddressDTO.setCity("nantes");
    expectedAddressDTO.setZip("44000");

    ChildDTO childDTOToAdd = new ChildDTO();
    childDTOToAdd.setFirstName("maria");
    childDTOToAdd.setAge(3);
    Collection<ChildDTO> expected = new ArrayList<>(List.of(childDTOToAdd));

    when(iAddressServiceMocked.convertAddressToAddressDTO(givenAddress)).thenReturn(expectedAddressDTO);
    when(ageServiceMocked.isStrictlyOverEighteen("adult")).thenReturn(true);
    when(ageServiceMocked.isStrictlyOverEighteen("child")).thenReturn(false);
    when(ageServiceMocked.getAge("child")).thenReturn(3);

    //when
    Collection<ChildDTO> result = iPersonService.getChildrenDTO(givenAddress);
    //then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getPersonsFromAddressTest() {
    //given
    Address givenAddress = new Address();
    givenAddress.setRoad("rue racine");
    givenAddress.setCity("nantes");
    givenAddress.setZipCode("44000");

    Person givenPerson1 = new Person();
    givenPerson1.setFirstName("marion");
    givenPerson1.setAddress(givenAddress);
    Person givenPerson2 = new Person();
    givenPerson1.setFirstName("julian");
    givenPerson1.setAddress(givenAddress);

    givenAddress.setPersons(List.of(givenPerson1, givenPerson2));
    Collection<Person> expected = new ArrayList<>(
      List.of(
        givenPerson1,
        givenPerson2
      )
    );
    //when
    Collection<Person> result = iPersonService.getPersonsFromAddress(givenAddress);
    //then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getPersonsFromAddressTestWithNullCollection() {
    //given
    Address givenAddress = new Address();
    givenAddress.setRoad("rue racine");
    givenAddress.setCity("nantes");
    givenAddress.setZipCode("44000");
    givenAddress.setPersons(null);
    //when
    Collection<Person> result = iPersonService.getPersonsFromAddress(givenAddress);
    //then
    assertThat(result).isNull();
  }

  @Test
  public void getPersonsFromAddressesTest() {
    //given
    Address givenAddress1 = new Address();
    givenAddress1.setRoad("rue racine");
    givenAddress1.setCity("nantes");
    givenAddress1.setZipCode("44000");
    Address givenAddress2 = new Address();
    givenAddress2.setRoad("rue chave");
    givenAddress2.setCity("nantes");
    givenAddress2.setZipCode("44000");

    Person givenPerson1 = new Person();
    givenPerson1.setFirstName("marion");
    givenPerson1.setAddress(givenAddress1);
    Person givenPerson2 = new Person();
    givenPerson1.setFirstName("julian");
    givenPerson1.setAddress(givenAddress1);
    Person givenPerson3 = new Person();
    givenPerson1.setFirstName("julie");
    givenPerson1.setAddress(givenAddress2);

    givenAddress1.setPersons(List.of(givenPerson1, givenPerson2));
    givenAddress2.setPersons(List.of(givenPerson3));

    Collection<Address> givenAddresses = new ArrayList<>();
    givenAddresses.add(givenAddress1);
    givenAddresses.add(givenAddress2);

    Collection<Person> expected = new ArrayList<>(
      List.of(
        givenPerson1,
        givenPerson2,
        givenPerson3
      )
    );
    //when
    Collection<Person> result = iPersonService.getPersonsFromAddresses(givenAddresses);
    //then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getPersonsFromAddressesTestNullAddressesCollection() {
    //given
    Collection<Address> givenAddresses = null;
    //when
    Collection<Person> result = iPersonService.getPersonsFromAddresses(givenAddresses);
    //then
    assertThat(result).isNull();
  }

  @Test
  public void getPersonsFromAddressesTestBlankAddressesCollection() {
    //given
    Collection<Address> givenAddresses = new ArrayList<>();
    //when
    Collection<Person> result = iPersonService.getPersonsFromAddresses(givenAddresses);
    //then
    assertThat(result).isNull();
  }

  @Test
  public void createPersonTestWithNotExistingAddress() {
    //given
    String givenFirstName = "jules";
    String givenLastName = "michou";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    Address expectedAddress = new Address();
    expectedAddress.setRoad("rue racine");
    expectedAddress.setCity("nantes");
    expectedAddress.setZipCode("44000");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(false);
    when(iAddressServiceMocked.existsByRoadAndCityAndZipCode(expectedAddress)).thenReturn(false);

    //when
    JsonPerson result = iPersonService.createPerson(givenJsonPerson);
    //then
    verify(personRepositoryMocked, Mockito.times(1)).save(any(Person.class));
    verify(iAddressServiceMocked, Mockito.times(1)).save(any(Address.class));
    assertThat(result).isEqualTo(givenJsonPerson);
  }

  @Test
  public void createPersonTestWithExistingPerson() {
    //given
    String givenFirstName = "jules";
    String givenLastName = "michou";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(true);
    //when
    JsonPerson result = iPersonService.createPerson(givenJsonPerson);
    //then
    verify(personRepositoryMocked, Mockito.times(0)).save(any(Person.class));
    assertThat(result).isNull();
  }

  @Test
  public void createPersonTestWithExistingAddress() {
    //given
    String givenFirstName = "jules";
    String givenLastName = "michou";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(false);
    when(iAddressServiceMocked.existsByRoadAndCityAndZipCode(any(Address.class))).thenReturn(true);

    //when
    JsonPerson result = iPersonService.createPerson(givenJsonPerson);
    //then
    verify(personRepositoryMocked, Mockito.times(1)).save(any(Person.class));
    verify(iAddressServiceMocked, Mockito.times(0)).save(any(Address.class));
    verify(iAddressServiceMocked, Mockito.times(1)).getByRoadAndCityAndZipCode(any(Address.class));
    assertThat(result).isEqualTo(givenJsonPerson);
  }

  @Test
  public void createPersonTestWithBlankFirstName() {
    //given
    String givenFirstName = "";
    String givenLastName = "michou";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    //when
    JsonPerson result = iPersonService.createPerson(givenJsonPerson);
    //then
    verify(personRepositoryMocked, Mockito.times(0)).save(any(Person.class));
    verify(iAddressServiceMocked, Mockito.times(0)).save(any(Address.class));
    verify(iAddressServiceMocked, Mockito.times(0)).getByRoadAndCityAndZipCode(any(Address.class));
    assertThat(result).isNull();
  }

  @Test
  public void createPersonTestWithBlankLastName() {
    //given
    String givenFirstName = "jules";
    String givenLastName = "";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    //when
    JsonPerson result = iPersonService.createPerson(givenJsonPerson);
    //then
    verify(personRepositoryMocked, Mockito.times(0)).save(any(Person.class));
    verify(iAddressServiceMocked, Mockito.times(0)).save(any(Address.class));
    verify(iAddressServiceMocked, Mockito.times(0)).getByRoadAndCityAndZipCode(any(Address.class));
    assertThat(result).isNull();
  }

  @Test
  public void createPersonTestWithNullFirstName() {
    //given
    String givenFirstName = null;
    String givenLastName = "michou";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    //when
    JsonPerson result = iPersonService.createPerson(givenJsonPerson);
    //then
    verify(personRepositoryMocked, Mockito.times(0)).save(any(Person.class));
    verify(iAddressServiceMocked, Mockito.times(0)).save(any(Address.class));
    verify(iAddressServiceMocked, Mockito.times(0)).getByRoadAndCityAndZipCode(any(Address.class));
    assertThat(result).isNull();
  }

  @Test
  public void createPersonTestWithNullLastName() {
    //given
    String givenFirstName = "jules";
    String givenLastName = null;

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    //when
    JsonPerson result = iPersonService.createPerson(givenJsonPerson);
    //then
    verify(personRepositoryMocked, Mockito.times(0)).save(any(Person.class));
    verify(iAddressServiceMocked, Mockito.times(0)).save(any(Address.class));
    verify(iAddressServiceMocked, Mockito.times(0)).getByRoadAndCityAndZipCode(any(Address.class));
    assertThat(result).isNull();
  }

  @Test
  public void updatePersonWithJsonPersonTestWithNotExistingPerson() {
    //given
    String givenFirstName = "emile";
    String givenLastName = "denis";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(false);

    //when
    JsonPerson result = iPersonService.updatePersonWithJsonPerson(givenJsonPerson);

    //then
    verify(personRepositoryMocked, Mockito.times(1)).existsByFirstNameAndLastName(givenFirstName, givenLastName);
    assertThat(result).isNull();
  }

  @Test
  public void updatePersonWithJsonPersonTest() {
    //given
    String givenFirstName = "emile";
    String givenLastName = "denis";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    Address updatedAddress = new Address();
    Person foundPerson = new Person();

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(true);
    when(personRepositoryMocked.findByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPerson);
    when(iAddressServiceMocked.existsByRoadAndCityAndZipCode(any(Address.class))).thenReturn(false);
    when(iAddressServiceMocked.save(any(Address.class))).thenReturn(updatedAddress);

    //when
    JsonPerson result = iPersonService.updatePersonWithJsonPerson(givenJsonPerson);

    //then
    verify(personRepositoryMocked, Mockito.times(1)).existsByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoadAndCityAndZipCode(any(Address.class));
    verify(iAddressServiceMocked, Mockito.times(1)).save(any(Address.class));
    verify(personRepositoryMocked, Mockito.times(1)).save(foundPerson);
    assertThat(result).isEqualTo(givenJsonPerson);
  }

  @Test
  public void updatePersonWithJsonPersonTestWithExistingAddress() {
    //given
    String givenFirstName = "emile";
    String givenLastName = "denis";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    Address updatedAddress = new Address();
    updatedAddress.setId(1);
    updatedAddress.setRoad(givenJsonPerson.getAddress());
    updatedAddress.setCity(givenJsonPerson.getCity());
    updatedAddress.setZipCode(givenJsonPerson.getZip());

    Person foundPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setPhone(givenJsonPerson.getPhone());
    foundPerson.setEmail(givenJsonPerson.getEmail());

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(true);
    when(personRepositoryMocked.findByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPerson);
    when(iAddressServiceMocked.existsByRoadAndCityAndZipCode(any(Address.class))).thenReturn(true);
    when(iAddressServiceMocked.getByRoadAndCityAndZipCode(any(Address.class))).thenReturn(updatedAddress);
    //when
    JsonPerson result = iPersonService.updatePersonWithJsonPerson(givenJsonPerson);
    //then
    verify(personRepositoryMocked, Mockito.times(1)).existsByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoadAndCityAndZipCode(any(Address.class));
    verify(iAddressServiceMocked, Mockito.times(0)).save(any(Address.class));
    verify(iAddressServiceMocked, Mockito.times(1)).getByRoadAndCityAndZipCode(any(Address.class));
    verify(personRepositoryMocked, Mockito.times(1)).save(foundPerson);
    assertThat(result).isEqualTo(givenJsonPerson);
  }

  @Test
  public void deleteTestWithNullFirstName() {
    //given
    String givenFirstName = null;
    String givenLastName = "denis";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    //when
    JsonPerson result = iPersonService.delete(givenJsonPerson);

    //then
    verify(iPersonsAllergyServiceMocked, Mockito.times(0)).deleteAll(any(Iterable.class));
    verify(iPersonsMedicationServiceMocked, Mockito.times(0)).deleteAll(any(Iterable.class));
    verify(personRepositoryMocked, Mockito.times(0)).delete(any(Person.class));
    assertThat(result).isNull();
  }

  @Test
  public void deleteTestWithBlankFirstName() {
    //given
    String givenFirstName = "";
    String givenLastName = "denis";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    //when
    JsonPerson result = iPersonService.delete(givenJsonPerson);

    //then
    verify(iPersonsAllergyServiceMocked, Mockito.times(0)).deleteAll(any(Iterable.class));
    verify(iPersonsMedicationServiceMocked, Mockito.times(0)).deleteAll(any(Iterable.class));
    verify(personRepositoryMocked, Mockito.times(0)).delete(any(Person.class));
    assertThat(result).isNull();
  }

  @Test
  public void deleteTestWithNullLastName() {
    //given
    String givenFirstName = "emile";
    String givenLastName = null;

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    //when
    JsonPerson result = iPersonService.delete(givenJsonPerson);

    //then
    verify(iPersonsAllergyServiceMocked, Mockito.times(0)).deleteAll(any(Iterable.class));
    verify(iPersonsMedicationServiceMocked, Mockito.times(0)).deleteAll(any(Iterable.class));
    verify(personRepositoryMocked, Mockito.times(0)).delete(any(Person.class));
    assertThat(result).isNull();
  }

  @Test
  public void deleteTestWithBlankLastName() {
    //given
    String givenFirstName = "emile";
    String givenLastName = "";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    //when
    JsonPerson result = iPersonService.delete(givenJsonPerson);

    //then
    verify(iPersonsAllergyServiceMocked, Mockito.times(0)).deleteAll(any(Iterable.class));
    verify(iPersonsMedicationServiceMocked, Mockito.times(0)).deleteAll(any(Iterable.class));
    verify(personRepositoryMocked, Mockito.times(0)).delete(any(Person.class));
    assertThat(result).isNull();
  }

  @Test
  public void deleteTestWithNotExistingPerson() {
    //given
    String givenFirstName = "emile";
    String givenLastName = "denis";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(false);

    //when
    JsonPerson result = iPersonService.delete(givenJsonPerson);

    //then
    verify(iPersonsAllergyServiceMocked, Mockito.times(0)).deleteAll(any(Iterable.class));
    verify(iPersonsMedicationServiceMocked, Mockito.times(0)).deleteAll(any(Iterable.class));
    verify(personRepositoryMocked, Mockito.times(0)).delete(any(Person.class));
    assertThat(result).isNull();
  }

  @Test
  public void deleteTest() {
    //given
    String givenFirstName = "emile";
    String givenLastName = "denis";

    JsonPerson givenJsonPerson = new JsonPerson();
    givenJsonPerson.setFirstName(givenFirstName);
    givenJsonPerson.setLastName(givenLastName);
    givenJsonPerson.setAddress("rue racine");
    givenJsonPerson.setCity("nantes");
    givenJsonPerson.setZip("44000");
    givenJsonPerson.setPhone("123");
    givenJsonPerson.setEmail("mail@mail.com");

    Collection<PersonsAllergy> givenPersonsAllergies = new ArrayList<>();
    Collection<PersonsMedication> givenPersonsMedication = new ArrayList<>();

    Person foundPersonToDelete = new Person();
    foundPersonToDelete.setFirstName(givenFirstName);
    foundPersonToDelete.setLastName(givenLastName);
    foundPersonToDelete.setPhone(givenJsonPerson.getPhone());
    foundPersonToDelete.setEmail(givenJsonPerson.getEmail());
    foundPersonToDelete.setPersonsAllergies(givenPersonsAllergies);
    foundPersonToDelete.setPersonsMedications(givenPersonsMedication);

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(true);
    when(personRepositoryMocked.findByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPersonToDelete);

    //when
    JsonPerson result = iPersonService.delete(givenJsonPerson);

    //then
    verify(iPersonsAllergyServiceMocked, Mockito.times(1)).deleteAll(givenPersonsAllergies);
    verify(iPersonsMedicationServiceMocked, Mockito.times(1)).deleteAll(givenPersonsMedication);
    verify(personRepositoryMocked, Mockito.times(1)).delete(foundPersonToDelete);
    assertThat(result).isEqualTo(givenJsonPerson);
  }

  @Test
  public void createMedicalRecordsTestWithNotExistingPerson() {
    //given
    String givenFirstName = "emile";
    String givenLastName = "denis";
    String givenBirthDate = "01/01/1965";
    List<String> givenMedications = new ArrayList<>();
    List<String> givenAllergies = new ArrayList<>();

    JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setFirstName(givenFirstName);
    givenJsonMedicalRecord.setLastName(givenLastName);
    givenJsonMedicalRecord.setBirthdate(givenBirthDate);
    givenJsonMedicalRecord.setMedications(givenMedications);
    givenJsonMedicalRecord.setAllergies(givenAllergies);

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(false);

    //when
    JsonMedicalRecord result = iPersonService.createMedicalRecords(givenJsonMedicalRecord);

    //then
    verify(personRepositoryMocked, Mockito.times(1)).existsByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(personRepositoryMocked, Mockito.times(0)).findByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).getMedicalRecords(any(Person.class));
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).existsFromPerson(any(Person.class));
    verify(personRepositoryMocked, Mockito.times(0)).save(any(Person.class));
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).createMedicationsFromJsonPerson(any(JsonMedicalRecord.class), any(Person.class));
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).createAllergiesFromJsonPerson(any(JsonMedicalRecord.class), any(Person.class));
    assertThat(result).isNull();
  }

  @Test
  public void createMedicalRecordsTestWithAlreadyExistingMedicalRecord() {
    //given
    String givenFirstName = "emile";
    String givenLastName = "denis";
    String givenBirthDate = "01/01/1965";
    List<String> givenMedications = new ArrayList(List.of("someMed1", "someMed2"));
    List<String> givenAllergies = new ArrayList(List.of("someAllergy1", "someAllergy2"));
    MedicalRecordsDTO givenMedicalRecordDTO = new MedicalRecordsDTO();
    givenMedicalRecordDTO.setMedications(givenMedications);
    givenMedicalRecordDTO.setAllergies(givenAllergies);

    JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setFirstName(givenFirstName);
    givenJsonMedicalRecord.setLastName(givenLastName);
    givenJsonMedicalRecord.setBirthdate(givenBirthDate);
    givenJsonMedicalRecord.setMedications(givenMedications);
    givenJsonMedicalRecord.setAllergies(givenAllergies);

    Person foundPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setBirthDate(givenJsonMedicalRecord.getBirthdate());
    foundPerson.setPhone("123");
    foundPerson.setEmail("mail@mail.com");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(true);
    when(personRepositoryMocked.findByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPerson);
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundPerson)).thenReturn(givenMedicalRecordDTO);
    when(iMedicalRecordsServiceMocked.existsFromPerson(foundPerson)).thenReturn(true);

    //when
    JsonMedicalRecord result = iPersonService.createMedicalRecords(givenJsonMedicalRecord);

    //then
    verify(personRepositoryMocked, Mockito.times(1)).existsByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(personRepositoryMocked, Mockito.times(1)).findByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(iMedicalRecordsServiceMocked, Mockito.times(2)).getMedicalRecords(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).existsFromPerson(foundPerson);
    verify(personRepositoryMocked, Mockito.times(0)).save(any(Person.class));
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).createMedicationsFromJsonPerson(any(JsonMedicalRecord.class), any(Person.class));
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).createAllergiesFromJsonPerson(any(JsonMedicalRecord.class), any(Person.class));
    assertThat(result).isNull();
  }

  @Test
  public void createMedicalRecordsTestWithNonExistingMedicalRecord() {
    //given
    List<String> givenMedications = new ArrayList(List.of("someMed1", "someMed2"));
    List<String> givenAllergies = new ArrayList(List.of("someAllergy1", "someAllergy2"));

    List<String> existingMedications = new ArrayList<>();
    List<String> existingAllergies = new ArrayList<>();
    MedicalRecordsDTO existingMedicalRecordsDTO = new MedicalRecordsDTO();
    existingMedicalRecordsDTO.setMedications(existingMedications);
    existingMedicalRecordsDTO.setAllergies(existingAllergies);

    String givenFirstName = "emile";
    String givenLastName = "denis";
    String givenBirthDate = "01/01/1965";

    JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setFirstName(givenFirstName);
    givenJsonMedicalRecord.setLastName(givenLastName);
    givenJsonMedicalRecord.setBirthdate(givenBirthDate);
    givenJsonMedicalRecord.setMedications(givenMedications);
    givenJsonMedicalRecord.setAllergies(givenAllergies);

    Person foundPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setBirthDate(givenJsonMedicalRecord.getBirthdate());
    foundPerson.setPhone("123");
    foundPerson.setEmail("mail@mail.com");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(true);
    when(personRepositoryMocked.findByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPerson);
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundPerson)).thenReturn(existingMedicalRecordsDTO);
    when(iMedicalRecordsServiceMocked.existsFromPerson(foundPerson)).thenReturn(false);
    //when
    JsonMedicalRecord result = iPersonService.createMedicalRecords(givenJsonMedicalRecord);
    //then
    verify(personRepositoryMocked, Mockito.times(1)).existsByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(personRepositoryMocked, Mockito.times(2)).findByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(iMedicalRecordsServiceMocked, Mockito.times(2)).getMedicalRecords(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).existsFromPerson(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).createMedicationsFromJsonPerson(givenJsonMedicalRecord, foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).createAllergiesFromJsonPerson(givenJsonMedicalRecord, foundPerson);
    assertThat(result).isEqualTo(givenJsonMedicalRecord);
  }

  @Test
  public void createMedicalRecordsTestWithExistingMedicalRecord() {
    //given

    List<String> existingMedications = new ArrayList<>(List.of("existingMed"));
    List<String> existingAllergies = new ArrayList<>(List.of("existingAllergy"));
    MedicalRecordsDTO existingMedicalRecordsDTO = new MedicalRecordsDTO();
    existingMedicalRecordsDTO.setMedications(existingMedications);
    existingMedicalRecordsDTO.setAllergies(existingAllergies);

    List<String> givenMedications = new ArrayList(List.of("someMed1", "someMed2"));
    List<String> givenAllergies = new ArrayList(List.of("someAllergy1", "someAllergy2"));

    String givenFirstName = "emile";
    String givenLastName = "denis";
    String givenBirthDate = "01/01/1965";

    JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setFirstName(givenFirstName);
    givenJsonMedicalRecord.setLastName(givenLastName);
    givenJsonMedicalRecord.setBirthdate(givenBirthDate);
    givenJsonMedicalRecord.setMedications(givenMedications);
    givenJsonMedicalRecord.setAllergies(givenAllergies);

    Person foundPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setBirthDate(givenJsonMedicalRecord.getBirthdate());
    foundPerson.setPhone("123");
    foundPerson.setEmail("mail@mail.com");


    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(true);
    when(personRepositoryMocked.findByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPerson);
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundPerson)).thenReturn(existingMedicalRecordsDTO);
    when(iMedicalRecordsServiceMocked.existsFromPerson(foundPerson)).thenReturn(false);
    //when
    JsonMedicalRecord result = iPersonService.createMedicalRecords(givenJsonMedicalRecord);
    //then
    verify(personRepositoryMocked, Mockito.times(1)).existsByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(personRepositoryMocked, Mockito.times(2)).findByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(iMedicalRecordsServiceMocked, Mockito.times(2)).getMedicalRecords(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).existsFromPerson(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).createMedicationsFromJsonPerson(givenJsonMedicalRecord, foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).createAllergiesFromJsonPerson(givenJsonMedicalRecord, foundPerson);
    assertThat(result).isEqualTo(givenJsonMedicalRecord);
  }

  @Test
  public void updateMedicalRecordsTestWithNonExistingPerson() {
    //given
    List<String> givenMedications = new ArrayList(List.of("someMed1", "someMed2"));
    List<String> givenAllergies = new ArrayList(List.of("someAllergy1", "someAllergy2"));

    String givenFirstName = "emile";
    String givenLastName = "denis";
    String givenBirthDate = "01/01/1965";

    JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setFirstName(givenFirstName);
    givenJsonMedicalRecord.setLastName(givenLastName);
    givenJsonMedicalRecord.setBirthdate(givenBirthDate);
    givenJsonMedicalRecord.setMedications(givenMedications);
    givenJsonMedicalRecord.setAllergies(givenAllergies);

    Person foundPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setBirthDate(givenJsonMedicalRecord.getBirthdate());
    foundPerson.setPhone("123");
    foundPerson.setEmail("mail@mail.com");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(false);

    //when
    JsonMedicalRecord result = iPersonService.updateMedicalRecords(givenJsonMedicalRecord);

    //then
    verify(personRepositoryMocked, Mockito.times(1)).existsByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).deletePersonsMedicationsFromPerson(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).createMedicationsFromJsonPerson(givenJsonMedicalRecord, foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).deletePersonsAllergiesFromPerson(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).createAllergiesFromJsonPerson(givenJsonMedicalRecord, foundPerson);
    assertThat(result).isNull();
  }

  @Test
  public void updateMedicalRecordsTest() {
    //given
    List<String> givenMedications = new ArrayList(List.of("someMed1", "someMed2"));
    List<String> givenAllergies = new ArrayList(List.of("someAllergy1", "someAllergy2"));

    String givenFirstName = "emile";
    String givenLastName = "denis";
    String givenBirthDate = "01/01/1965";

    JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setFirstName(givenFirstName);
    givenJsonMedicalRecord.setLastName(givenLastName);
    givenJsonMedicalRecord.setBirthdate(givenBirthDate);
    givenJsonMedicalRecord.setMedications(givenMedications);
    givenJsonMedicalRecord.setAllergies(givenAllergies);

    Person foundPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setBirthDate(givenJsonMedicalRecord.getBirthdate());
    foundPerson.setPhone("123");
    foundPerson.setEmail("mail@mail.com");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(true);
    when(personRepositoryMocked.findByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPerson);

    //when
    JsonMedicalRecord result = iPersonService.updateMedicalRecords(givenJsonMedicalRecord);

    //then
    verify(personRepositoryMocked, Mockito.times(1)).existsByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).deletePersonsMedicationsFromPerson(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).createMedicationsFromJsonPerson(givenJsonMedicalRecord, foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).deletePersonsAllergiesFromPerson(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).createAllergiesFromJsonPerson(givenJsonMedicalRecord, foundPerson);
    assertThat(result).isEqualTo(givenJsonMedicalRecord);
  }

  @Test
  public void updateMedicalRecordsTestWithDifferenceInBirthDate() {
    //given
    List<String> givenMedications = new ArrayList<>(List.of("someMed1", "someMed2"));
    List<String> givenAllergies = new ArrayList<>(List.of("someAllergy1", "someAllergy2"));

    String givenFirstName = "emile";
    String givenLastName = "denis";
    String givenBirthDate = "01/01/1965";

   JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setFirstName(givenFirstName);
    givenJsonMedicalRecord.setLastName(givenLastName);
    givenJsonMedicalRecord.setBirthdate(givenBirthDate);
    givenJsonMedicalRecord.setMedications(givenMedications);
    givenJsonMedicalRecord.setAllergies(givenAllergies);

    Person foundPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setBirthDate("someDiffBirthDate");
    foundPerson.setPhone("123");
    foundPerson.setEmail("mail@mail.com");

    Person savedPerson = new Person();
    savedPerson.setFirstName(givenFirstName);
    savedPerson.setLastName(givenLastName);
    savedPerson.setBirthDate(givenJsonMedicalRecord.getBirthdate());
    savedPerson.setPhone("123");
    savedPerson.setEmail("mail@mail.com");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(true);
    when(personRepositoryMocked.findByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPerson);
    when(personRepositoryMocked.save(foundPerson)).thenReturn(savedPerson);

    //when
    JsonMedicalRecord result = iPersonService.updateMedicalRecords(givenJsonMedicalRecord);

    //then
    verify(personRepositoryMocked, Mockito.times(1)).existsByFirstNameAndLastName(givenFirstName, givenLastName);
    verify(personRepositoryMocked, Mockito.times(1)).save(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).deletePersonsMedicationsFromPerson(savedPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).createMedicationsFromJsonPerson(givenJsonMedicalRecord, savedPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).deletePersonsAllergiesFromPerson(savedPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).createAllergiesFromJsonPerson(givenJsonMedicalRecord, savedPerson);
    assertThat(result).isEqualTo(givenJsonMedicalRecord);
  }

  @Test
  public void deleteMedicalRecordsTest() {
    //given
    List<String> givenMedications = new ArrayList(List.of("someMed1", "someMed2"));
    List<String> givenAllergies = new ArrayList(List.of("someAllergy1", "someAllergy2"));

    String givenFirstName = "emile";
    String givenLastName = "denis";
    String givenBirthDate = "01/01/1965";

    JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setFirstName(givenFirstName);
    givenJsonMedicalRecord.setLastName(givenLastName);
    givenJsonMedicalRecord.setBirthdate(givenBirthDate);
    givenJsonMedicalRecord.setMedications(givenMedications);
    givenJsonMedicalRecord.setAllergies(givenAllergies);

    Person foundPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setBirthDate("01/01/1965");
    foundPerson.setPhone("123");
    foundPerson.setEmail("mail@mail.com");

    Person updatedPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setBirthDate("");
    foundPerson.setPhone("123");
    foundPerson.setEmail("mail@mail.com");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(true);
    when(personRepositoryMocked.findByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPerson);
    when(personRepositoryMocked.save(foundPerson)).thenReturn(updatedPerson);

    //when
    JsonMedicalRecord result = iPersonService.deleteMedicalRecords(givenJsonMedicalRecord);

    //then
    verify(personRepositoryMocked, Mockito.times(1)).save(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).deletePersonsMedicationsFromPerson(updatedPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(1)).deletePersonsAllergiesFromPerson(updatedPerson);
    assertThat(result.getBirthdate()).isEqualTo("");
    assertThat(result.getMedications().size()).isEqualTo(0);
    assertThat(result.getAllergies().size()).isEqualTo(0);
  }

  @Test
  public void deleteMedicalRecordsTestWithNonExistingPerson() {
    //given
    List<String> givenMedications = new ArrayList<>(List.of("someMed1", "someMed2"));
    List<String> givenAllergies = new ArrayList<>(List.of("someAllergy1", "someAllergy2"));

    String givenFirstName = "emile";
    String givenLastName = "denis";
    String givenBirthDate = "01/01/1965";

    JsonMedicalRecord givenJsonMedicalRecord = new JsonMedicalRecord();
    givenJsonMedicalRecord.setFirstName(givenFirstName);
    givenJsonMedicalRecord.setLastName(givenLastName);
    givenJsonMedicalRecord.setBirthdate(givenBirthDate);
    givenJsonMedicalRecord.setMedications(givenMedications);
    givenJsonMedicalRecord.setAllergies(givenAllergies);

    Person foundPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setBirthDate("01/01/1965");
    foundPerson.setPhone("123");
    foundPerson.setEmail("mail@mail.com");

    Person updatedPerson = new Person();
    foundPerson.setFirstName(givenFirstName);
    foundPerson.setLastName(givenLastName);
    foundPerson.setBirthDate("");
    foundPerson.setPhone("123");
    foundPerson.setEmail("mail@mail.com");

    when(personRepositoryMocked.existsByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(false);

    //when
    JsonMedicalRecord result = iPersonService.deleteMedicalRecords(givenJsonMedicalRecord);

    //then
    verify(personRepositoryMocked, Mockito.times(0)).save(foundPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).deletePersonsMedicationsFromPerson(updatedPerson);
    verify(iMedicalRecordsServiceMocked, Mockito.times(0)).deletePersonsAllergiesFromPerson(updatedPerson);
    assertThat(result).isNull();
  }
}

