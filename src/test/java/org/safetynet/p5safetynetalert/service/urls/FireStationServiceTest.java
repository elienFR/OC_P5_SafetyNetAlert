package org.safetynet.p5safetynetalert.service.urls;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.model.dto.PersonDTO;
import org.safetynet.p5safetynetalert.model.dto.PersonForFloodDTO;
import org.safetynet.p5safetynetalert.model.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.model.dto.PhonesDTO;
import org.safetynet.p5safetynetalert.model.entity.Address;
import org.safetynet.p5safetynetalert.model.entity.FireStation;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.repository.FireStationRepository;
import org.safetynet.p5safetynetalert.service.AgeService;
import org.safetynet.p5safetynetalert.service.IAddressService;
import org.safetynet.p5safetynetalert.service.IPersonService;
import org.safetynet.p5safetynetalert.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FireStationServiceTest {

  @Autowired
  private IFireStationService iFireStationService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorServiceMocked;
  @MockBean
  private FireStationRepository fireStationRepositoryMocked;
  @MockBean
  private AgeService ageServiceMocked;
  @MockBean
  private IAddressService iAddressServiceMocked;
  @MockBean
  private IPersonService iPersonService;

  @Test
  public void getPersonsAndCountTestNullFireStation() {
    //given
    String givenFireStationNumber = "someNumber";
    when(fireStationRepositoryMocked.findByNumber(givenFireStationNumber)).thenReturn(null);
    //when
    PersonsFromFireStationDTO result = iFireStationService.getPersonsAndCount(givenFireStationNumber);
    //then
    verify(iPersonService, Mockito.times(0)).getPersonDTOsFromAddresses(any(Collection.class));
    verify(ageServiceMocked, Mockito.times(0)).countAdultsAndChildren(any(Collection.class));
    assertThat(result).isNull();
  }

  @Test
  public void getPersonsAndCountTest() {
    //given
    String givenFireStationNumber = "someNumber";

    FireStation foundFireStation = new FireStation();
    foundFireStation.setNumber("1");
    Collection<Address> fireStationAddresses = new ArrayList<>();
    foundFireStation.setAddresses(fireStationAddresses);
    when(fireStationRepositoryMocked.findByNumber(givenFireStationNumber)).thenReturn(foundFireStation);

    PersonDTO personDTO1 = new PersonDTO();
    PersonDTO personDTO2 = new PersonDTO();
    PersonDTO personDTO3 = new PersonDTO();
    Collection<PersonDTO> foundPersonDTOs = new ArrayList<>();
    foundPersonDTOs.add(personDTO1);
    foundPersonDTOs.add(personDTO2);
    foundPersonDTOs.add(personDTO3);
    when(iPersonService.getPersonDTOsFromAddresses(fireStationAddresses)).thenReturn(foundPersonDTOs);

    Map<String, Integer> countAdultsAndChildren = new HashMap<>();
    countAdultsAndChildren.put("adults", 28);
    countAdultsAndChildren.put("children", 42);
    when(ageServiceMocked.countAdultsAndChildren(foundPersonDTOs)).thenReturn(countAdultsAndChildren);

    PersonsFromFireStationDTO expected = new PersonsFromFireStationDTO();
    expected.setChildrenCount(42);
    expected.setAdultCount(28);
    expected.setPersonsList(foundPersonDTOs);

    //when
    PersonsFromFireStationDTO result = iFireStationService.getPersonsAndCount(givenFireStationNumber);

    //then
    assertThat(result).isEqualTo(expected);
    verify(iPersonService, Mockito.times(1)).getPersonDTOsFromAddresses(fireStationAddresses);
    verify(ageServiceMocked, Mockito.times(2)).countAdultsAndChildren(foundPersonDTOs);
  }

  @Test
  public void getPersonsForFloodTestNullFireStation() {
    //given
    String givenFireStationNumber = "2";
    when(fireStationRepositoryMocked.findByNumber(givenFireStationNumber)).thenReturn(null);
    //when
    Collection<PersonForFloodDTO> result = iFireStationService.getPersonsForFlood(givenFireStationNumber);
    //then
    assertThat(result).isNull();
  }

  @Test
  public void getPersonsForFloodTest() {
    //given
    String givenFireStationNumber = "someNumber";

    FireStation foundFireStation = new FireStation();
    foundFireStation.setNumber("1");
    Collection<Address> fireStationAddresses = new ArrayList<>();
    foundFireStation.setAddresses(fireStationAddresses);
    when(fireStationRepositoryMocked.findByNumber(givenFireStationNumber)).thenReturn(foundFireStation);

    PersonForFloodDTO personForFloodDTO1 = new PersonForFloodDTO();
    PersonForFloodDTO personForFloodDTO2 = new PersonForFloodDTO();
    PersonForFloodDTO personForFloodDTO3 = new PersonForFloodDTO();
    Collection<PersonForFloodDTO> expected = new ArrayList<>();
    expected.add(personForFloodDTO1);
    expected.add(personForFloodDTO2);
    expected.add(personForFloodDTO3);
    when(iPersonService.getPersonsForFlood(fireStationAddresses)).thenReturn(expected);

    //when
    Collection<PersonForFloodDTO> result = iFireStationService.getPersonsForFlood(givenFireStationNumber);

    //then
    assertThat(result).isEqualTo(expected);
    verify(fireStationRepositoryMocked, Mockito.times(1)).findByNumber(givenFireStationNumber);
    verify(iPersonService, Mockito.times(1)).getPersonsForFlood(fireStationAddresses);
  }

  @Test
  public void getPhonesFromFireStationNumberTestNullFireStation() {
    //given
    String givenFireStationNumber = "2";
    when(fireStationRepositoryMocked.findByNumber(givenFireStationNumber)).thenReturn(null);
    //when
    PhonesDTO result = iFireStationService.getPhonesFromFireStationNumber(givenFireStationNumber);
    //then
    assertThat(result).isNull();
    verify(fireStationRepositoryMocked, Mockito.times(1)).findByNumber(givenFireStationNumber);
    verify(iPersonService, Mockito.times(0)).getPersonsFromAddresses(any(Collection.class));
    verify(iPersonService, Mockito.times(0)).getPhones(any(Collection.class));
  }

  @Test
  public void getPhonesFromFireStationNumberTest() {
    //given
    String givenFireStationNumber = "2";

    FireStation foundFireStation = new FireStation();
    foundFireStation.setNumber("1");

    Collection<Address> fireStationAddresses = new ArrayList<>();
    foundFireStation.setAddresses(fireStationAddresses);
    when(fireStationRepositoryMocked.findByNumber(givenFireStationNumber)).thenReturn(foundFireStation);

    Person person1 = new Person();
    Person person2 = new Person();
    Person person3 = new Person();
    Collection<Person> foundPersons = new ArrayList<>();
    foundPersons.add(person1);
    foundPersons.add(person2);
    foundPersons.add(person3);
    when(iPersonService.getPersonsFromAddresses(fireStationAddresses)).thenReturn(foundPersons);

    Collection<String> expectedPhonesList = new ArrayList<>(List.of("someNumbers", "someOtherNumbers"));
    when(iPersonService.getPhones(foundPersons)).thenReturn(expectedPhonesList);

    PhonesDTO expected = new PhonesDTO();
    expected.setPhonesList(expectedPhonesList);

    //when
    PhonesDTO result = iFireStationService.getPhonesFromFireStationNumber(givenFireStationNumber);

    //then
    assertThat(result).isEqualTo(expected);
    verify(fireStationRepositoryMocked, Mockito.times(1)).findByNumber(givenFireStationNumber);
    verify(iPersonService, Mockito.times(1)).getPersonsFromAddresses(any(Collection.class));
    verify(iPersonService, Mockito.times(1)).getPhones(any(Collection.class));
  }

  @Test
  public void saveAddressFireStationMappingTestNullRoad() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setStation("someStation");

    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isNull();
    verify(iAddressServiceMocked, Mockito.times(0)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked, Mockito.times(0)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked, Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked, Mockito.times(0)).save(any(Address.class));
  }

  @Test
  public void saveAddressFireStationMappingTestBlankRoad() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("");
    givenJsonFireStation.setStation("someStation");


    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isNull();
    verify(iAddressServiceMocked, Mockito.times(0)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked, Mockito.times(0)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked, Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked, Mockito.times(0)).save(any(Address.class));
  }

  @Test
  public void saveAddressFireStationMappingTestSomeAddressGivenButNotExistingWithNullFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someAddress");

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(false);

    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isEqualTo(givenJsonFireStation);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked, Mockito.times(0)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked, Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked, Mockito.times(1)).save(any(Address.class));
  }

  @Test
  public void saveAddressFireStationMappingTestSomeAddressGivenAndExistingWithNullFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someAddress");

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    FireStation linkedFireStation = new FireStation();
    linkedFireStation.setNumber("someNumber");
    Address foundAddress = new Address();
    foundAddress.setRoad(givenJsonFireStation.getAddress());
    foundAddress.setCity("Culver");
    foundAddress.setZipCode("97451");
    foundAddress.setFireStation(linkedFireStation);
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);
    Address savedAddress = new Address();
    savedAddress.setRoad(givenJsonFireStation.getAddress());
    savedAddress.setCity("Culver");
    savedAddress.setZipCode("97451");
    when(iAddressServiceMocked.save(foundAddress)).thenReturn(savedAddress);


    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isEqualTo(givenJsonFireStation);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked, Mockito.times(1)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked, Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked, Mockito.times(1)).save(foundAddress);
  }

  @Test
  public void saveAddressFireStationMappingTestSomeAddressGivenAndExistingWithBlankFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("SomeAddress");
    givenJsonFireStation.setStation("");

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    FireStation linkedFireStation = new FireStation();
    linkedFireStation.setNumber("someNumber");
    Address foundAddress = new Address();
    foundAddress.setRoad(givenJsonFireStation.getAddress());
    foundAddress.setCity("Culver");
    foundAddress.setZipCode("97451");
    foundAddress.setFireStation(linkedFireStation);
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);
    Address savedAddress = new Address();
    savedAddress.setRoad(givenJsonFireStation.getAddress());
    savedAddress.setCity("Culver");
    savedAddress.setZipCode("97451");
    when(iAddressServiceMocked.save(foundAddress)).thenReturn(savedAddress);


    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isEqualTo(givenJsonFireStation);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked, Mockito.times(1)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked, Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked, Mockito.times(1)).save(foundAddress);
  }

  @Test
  public void saveAddressFireStationMappingTestSomeAddressGivenAndExistingWithNonExistingFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someAddress");
    givenJsonFireStation.setStation("someNumber");

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    FireStation linkedFireStation = new FireStation();
    linkedFireStation.setNumber("someNumber");
    Address foundAddress = new Address();
    foundAddress.setRoad(givenJsonFireStation.getAddress());
    foundAddress.setCity("Culver");
    foundAddress.setZipCode("97451");
    foundAddress.setFireStation(linkedFireStation);
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);
    Address savedAddress = new Address();
    savedAddress.setRoad(givenJsonFireStation.getAddress());
    savedAddress.setCity("Culver");
    savedAddress.setZipCode("97451");
    when(iAddressServiceMocked.save(foundAddress)).thenReturn(savedAddress);

    when(fireStationRepositoryMocked.existsByNumber(givenJsonFireStation.getStation())).thenReturn(false);
    FireStation savedFireStation = new FireStation();
    savedFireStation.setNumber(givenJsonFireStation.getStation());
    when(fireStationRepositoryMocked.save(any(FireStation.class))).thenReturn(savedFireStation);

    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isEqualTo(givenJsonFireStation);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked, Mockito.times(1)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked, Mockito.times(1)).existsByNumber(givenJsonFireStation.getStation());
    verify(fireStationRepositoryMocked, Mockito.times(1)).save(any(FireStation.class));
    verify(iAddressServiceMocked, Mockito.times(1)).save(foundAddress);
  }

  @Test
  public void saveAddressFireStationMappingTestSomeAddressGivenAndExistingWithExistingFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someAddress");
    givenJsonFireStation.setStation("someNumber");

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    FireStation linkedFireStation = new FireStation();
    linkedFireStation.setNumber("someNumber");
    Address foundAddress = new Address();
    foundAddress.setRoad(givenJsonFireStation.getAddress());
    foundAddress.setCity("Culver");
    foundAddress.setZipCode("97451");
    foundAddress.setFireStation(linkedFireStation);
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);

    when(fireStationRepositoryMocked.existsByNumber(givenJsonFireStation.getStation())).thenReturn(true);
    FireStation foundFireStation = new FireStation();
    foundFireStation.setNumber(givenJsonFireStation.getStation());
    when(fireStationRepositoryMocked.findByNumber(givenJsonFireStation.getStation())).thenReturn(foundFireStation);

    Address savedAddress = new Address();
    savedAddress.setRoad(givenJsonFireStation.getAddress());
    savedAddress.setCity("Culver");
    savedAddress.setZipCode("97451");
    savedAddress.setFireStation(foundFireStation);
    when(iAddressServiceMocked.save(foundAddress)).thenReturn(savedAddress);

    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isEqualTo(givenJsonFireStation);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked, Mockito.times(1)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked, Mockito.times(1)).existsByNumber(givenJsonFireStation.getStation());
    verify(fireStationRepositoryMocked, Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked, Mockito.times(1)).save(foundAddress);
  }

  @Test
  public void updateAddressFireStationMappingTestWithNullFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someRoad");

    //when
    JsonFireStation result = iFireStationService.updateAddressFireStationMapping(givenJsonFireStation);

    //then
    verify(fireStationRepositoryMocked,Mockito.times(0)).existsByNumber(givenJsonFireStation.getStation());
    verify(fireStationRepositoryMocked,Mockito.times(0)).save(any(FireStation.class));
    assertThat(result).isNull();
  }

  @Test
  public void updateAddressFireStationMappingTestWithBlankFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someRoad");
    givenJsonFireStation.setStation("");

    //when
    JsonFireStation result = iFireStationService.updateAddressFireStationMapping(givenJsonFireStation);

    //then
    verify(fireStationRepositoryMocked,Mockito.times(0)).existsByNumber(givenJsonFireStation.getStation());
    verify(fireStationRepositoryMocked,Mockito.times(0)).save(any(FireStation.class));
    assertThat(result).isNull();
  }

  @Test
  public void updateAddressFireStationMappingTestWithNullAddress() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setStation("someNumber");

    //when
    JsonFireStation result = iFireStationService.updateAddressFireStationMapping(givenJsonFireStation);

    //then
    verify(iAddressServiceMocked,Mockito.times(0)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(0)).save(any(Address.class));
    assertThat(result).isNull();
  }

  @Test
  public void updateAddressFireStationMappingTestWithNonExistingFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someRoad");
    givenJsonFireStation.setStation("someNumber");

    when(fireStationRepositoryMocked.existsByNumber(givenJsonFireStation.getStation())).thenReturn(false);

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    Address foundAddress = new Address();
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);

    //when
    JsonFireStation result = iFireStationService.updateAddressFireStationMapping(givenJsonFireStation);


    //then
    verify(fireStationRepositoryMocked,Mockito.times(1)).existsByNumber(givenJsonFireStation.getStation());
    verify(fireStationRepositoryMocked,Mockito.times(1)).save(any(FireStation.class));
    assertThat(result).isEqualTo(givenJsonFireStation);
  }

  @Test
  public void updateAddressFireStationMappingTestWithNonExistingAddress() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someRoad");
    givenJsonFireStation.setStation("someNumber");

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(false);

    //when
    JsonFireStation result = iFireStationService.updateAddressFireStationMapping(givenJsonFireStation);

    //then
    verify(iAddressServiceMocked,Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(0)).save(any(Address.class));
    assertThat(result).isNull();
  }

  @Test
  public void updateAddressFireStationMappingTestExistingAddressAndFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someRoad");
    givenJsonFireStation.setStation("someNumber");

    when(fireStationRepositoryMocked.existsByNumber(givenJsonFireStation.getStation())).thenReturn(true);
    FireStation foundFireStation = new FireStation();
    foundFireStation.setNumber(givenJsonFireStation.getStation());
    when(fireStationRepositoryMocked.findByNumber(givenJsonFireStation.getStation())).thenReturn(foundFireStation);

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    Address foundAddress = new Address();
    foundAddress.setRoad("someRoad");
    foundAddress.setCity("Culver");
    foundAddress.setZipCode("SomeZip");
    foundAddress.setFireStation(new FireStation());
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);
    Address addressToSave = foundAddress;
    addressToSave.setFireStation(foundFireStation);
    when(iAddressServiceMocked.save(addressToSave)).thenReturn(any(Address.class));

    //when
    JsonFireStation result = iFireStationService.updateAddressFireStationMapping(givenJsonFireStation);

    //then
    verify(fireStationRepositoryMocked,Mockito.times(1)).existsByNumber(givenJsonFireStation.getStation());
    verify(fireStationRepositoryMocked,Mockito.times(1)).findByNumber(givenJsonFireStation.getStation());
    verify(iAddressServiceMocked,Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(1)).getByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(1)).save(addressToSave);
    assertThat(result).isEqualTo(givenJsonFireStation);
  }

  @Test
  public void eraseAddressFireStationMappingTestNonExistingAddress() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someRoad");
    givenJsonFireStation.setStation("someNumber");

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(false);

    //when
    JsonFireStation result = iFireStationService.eraseAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isNull();
    verify(iAddressServiceMocked,Mockito.times(0)).save(any(Address.class));
  }

  @Test
  public void eraseAddressFireStationMappingTest() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation();
    givenJsonFireStation.setAddress("someRoad");
    givenJsonFireStation.setStation("someNumber");

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    Address foundAddress = new Address();
    foundAddress.setRoad("someRoad");
    foundAddress.setCity("someCity");
    foundAddress.setZipCode("someZipCode");
    foundAddress.setFireStation(new FireStation());
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);
    Address addressToSave = foundAddress;
    addressToSave.setFireStation(null);
    when(iAddressServiceMocked.save(addressToSave)).thenReturn(any(Address.class));

    JsonFireStation expected = givenJsonFireStation;
    expected.setStation(null);

    //when
    JsonFireStation result = iFireStationService.eraseAddressFireStationMapping(givenJsonFireStation);

    //then
    verify(iAddressServiceMocked,Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(1)).getByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(1)).save(addressToSave);
    assertThat(result).isEqualTo(expected);
  }


}
