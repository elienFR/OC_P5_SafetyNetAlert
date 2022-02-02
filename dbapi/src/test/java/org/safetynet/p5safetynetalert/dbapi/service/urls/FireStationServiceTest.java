package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonForFloodDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PhonesDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;
import org.safetynet.p5safetynetalert.dbapi.model.entity.FireStation;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.dbapi.repository.FireStationRepository;
import org.safetynet.p5safetynetalert.dbapi.service.AgeService;
import org.safetynet.p5safetynetalert.dbapi.service.IAddressService;
import org.safetynet.p5safetynetalert.dbapi.service.IPersonService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
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

    FireStation foundFireStation = new FireStation("1");
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

    FireStation foundFireStation = new FireStation("1");
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

    FireStation foundFireStation = new FireStation("1");
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
    JsonFireStation givenJsonFireStation = new JsonFireStation(
      null,
      "someStation"
    );

    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isNull();
    verify(iAddressServiceMocked, Mockito.times(0)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(0)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked,Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked,Mockito.times(0)).save(any(Address.class));
  }

  @Test
  public void saveAddressFireStationMappingTestBlankRoad() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation(
      "",
      "someStation"
    );

    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isNull();
    verify(iAddressServiceMocked, Mockito.times(0)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(0)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked,Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked,Mockito.times(0)).save(any(Address.class));
  }

  @Test
  public void saveAddressFireStationMappingTestSomeAddressGivenButNotExistingWithNullFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation(
      "someAddress",
      null
    );

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(false);

    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isEqualTo(givenJsonFireStation);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(0)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked,Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked,Mockito.times(1)).save(any(Address.class));
  }

  @Test
  public void saveAddressFireStationMappingTestSomeAddressGivenAndExistingWithNullFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation(
      "someAddress",
      null
    );

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    Address foundAddress = new Address(givenJsonFireStation.getAddress(),"Culver","97451",new FireStation("someNumber"));
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);
    Address savedAddress = new Address(givenJsonFireStation.getAddress(),"Culver","97451",null);
    when(iAddressServiceMocked.save(foundAddress)).thenReturn(savedAddress);


    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isEqualTo(givenJsonFireStation);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(1)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked,Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked,Mockito.times(1)).save(foundAddress);
  }

  @Test
  public void saveAddressFireStationMappingTestSomeAddressGivenAndExistingWithBlankFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation(
      "someAddress",
      ""
    );

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    Address foundAddress = new Address(givenJsonFireStation.getAddress(),"Culver","97451",new FireStation("someNumber"));
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);
    Address savedAddress = new Address(givenJsonFireStation.getAddress(),"Culver","97451",null);
    when(iAddressServiceMocked.save(foundAddress)).thenReturn(savedAddress);


    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isEqualTo(givenJsonFireStation);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(1)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked,Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked,Mockito.times(1)).save(foundAddress);
  }

  @Test
  public void saveAddressFireStationMappingTestSomeAddressGivenAndExistingWithNonExistingFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation(
      "someAddress",
      "someNumber"
    );

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    Address foundAddress = new Address(givenJsonFireStation.getAddress(),"Culver","97451",new FireStation("someNumber"));
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);
    Address savedAddress = new Address(givenJsonFireStation.getAddress(),"Culver","97451",null);
    when(iAddressServiceMocked.save(foundAddress)).thenReturn(savedAddress);

    when(fireStationRepositoryMocked.existsByNumber(givenJsonFireStation.getStation())).thenReturn(false);
    FireStation savedFireStation = new FireStation(givenJsonFireStation.getStation());
    when(fireStationRepositoryMocked.save(any(FireStation.class))).thenReturn(savedFireStation);

    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isEqualTo(givenJsonFireStation);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(1)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked,Mockito.times(1)).existsByNumber(givenJsonFireStation.getStation());
    verify(fireStationRepositoryMocked,Mockito.times(1)).save(any(FireStation.class));
    verify(iAddressServiceMocked,Mockito.times(1)).save(foundAddress);
  }

  @Test
  public void saveAddressFireStationMappingTestSomeAddressGivenAndExistingWithExistingFireStation() {
    //given
    JsonFireStation givenJsonFireStation = new JsonFireStation(
      "someAddress",
      "someNumber"
    );

    when(iAddressServiceMocked.existsByRoad(givenJsonFireStation.getAddress())).thenReturn(true);
    Address foundAddress = new Address(givenJsonFireStation.getAddress(),"Culver","97451",new FireStation("someNumber"));
    when(iAddressServiceMocked.getByRoad(givenJsonFireStation.getAddress())).thenReturn(foundAddress);

    when(fireStationRepositoryMocked.existsByNumber(givenJsonFireStation.getStation())).thenReturn(true);
    FireStation foundFireStation = new FireStation(givenJsonFireStation.getStation());
    when(fireStationRepositoryMocked.findByNumber(givenJsonFireStation.getStation())).thenReturn(foundFireStation);

    Address savedAddress = new Address(givenJsonFireStation.getAddress(),"Culver","97451",foundFireStation);
    when(iAddressServiceMocked.save(foundAddress)).thenReturn(savedAddress);

    //when
    JsonFireStation result = iFireStationService.saveAddressFireStationMapping(givenJsonFireStation);

    //then
    assertThat(result).isEqualTo(givenJsonFireStation);
    verify(iAddressServiceMocked, Mockito.times(1)).existsByRoad(givenJsonFireStation.getAddress());
    verify(iAddressServiceMocked,Mockito.times(1)).getByRoad(givenJsonFireStation.getAddress());
    verify(fireStationRepositoryMocked,Mockito.times(1)).existsByNumber(givenJsonFireStation.getStation());
    verify(fireStationRepositoryMocked,Mockito.times(0)).save(any(FireStation.class));
    verify(iAddressServiceMocked,Mockito.times(1)).save(foundAddress);
  }


}
