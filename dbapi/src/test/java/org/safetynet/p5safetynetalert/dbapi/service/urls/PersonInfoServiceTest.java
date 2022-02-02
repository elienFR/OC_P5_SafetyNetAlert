package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.dto.MedicalRecordsDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonInfoDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsInfoDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.service.AgeService;
import org.safetynet.p5safetynetalert.dbapi.service.IMedicalRecordsService;
import org.safetynet.p5safetynetalert.dbapi.service.IPersonService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonInfoServiceTest {

  @Autowired
  private IPersonInfoService iPersonInfoService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorServiceMocked;
  @MockBean
  private IPersonService iPersonServiceMocked;
  @MockBean
  private AgeService ageServiceMocked;
  @MockBean
  private IMedicalRecordsService iMedicalRecordsServiceMocked;

  @Test
  public void getPersonInfoFromFirstAndOrLastNameTestNull() {
    //given
    String givenFirstName = "firstName";
    String givenLastName = "lastName";
    when(iPersonServiceMocked.getByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(null);
    //when
    PersonsInfoDTO result = iPersonInfoService.getPersonInfoFromFirstAndOrLastName(givenFirstName, givenLastName);
    //then
    assertThat(result).isNull();
  }

  @Test
  public void getPersonInfoFromFirstAndOrLastNameTest() {
    //given
    String givenFirstName = "firstName";
    String givenLastName = "lastName";
    String givenBirthDate = "someDate";
    Person foundPerson = new Person("jim", "carrey", givenBirthDate, null, "someMail", null);
    when(iPersonServiceMocked.getByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPerson);

    when(ageServiceMocked.getAge(foundPerson.getBirthDate())).thenReturn(42);

    MedicalRecordsDTO foundMedicalRecordForPersonInfoDTO = new MedicalRecordsDTO();
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundPerson)).thenReturn(foundMedicalRecordForPersonInfoDTO);

    PersonInfoDTO expectedPersonInfoDTO = new PersonInfoDTO(foundPerson.getFirstName(),foundPerson.getLastName(),42,foundPerson.getEmail(),foundMedicalRecordForPersonInfoDTO);
    expectedPersonInfoDTO.setFirstName(foundPerson.getFirstName());
    expectedPersonInfoDTO.setLastName(foundPerson.getLastName());
    expectedPersonInfoDTO.setAge(42);
    expectedPersonInfoDTO.setMedicalRecords(foundMedicalRecordForPersonInfoDTO);

    Person foundFamilyRelative1 = new Person("remi", "name", givenBirthDate, null, "mail@mail.com", null);
    Person foundFamilyRelative2 = new Person("jorge", "name", givenBirthDate, null, "mail@mail.com", null);
    Person foundFamilyRelative3 = new Person("zoe", "name", givenBirthDate, null, "mail@mail.com", null);
    Collection<Person> foundFamilyRelatives = new ArrayList<>();
    foundFamilyRelatives.add(foundFamilyRelative1);
    foundFamilyRelatives.add(foundFamilyRelative2);
    foundFamilyRelatives.add(foundFamilyRelative3);
    when(iPersonServiceMocked.getAllByName(givenLastName)).thenReturn(foundFamilyRelatives);


    MedicalRecordsDTO foundMedicalRecordForFamilyRelative1 = new MedicalRecordsDTO();
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundFamilyRelative1)).thenReturn(foundMedicalRecordForFamilyRelative1);
    PersonInfoDTO convertedFamilyRelative1 = new PersonInfoDTO(foundFamilyRelative1.getFirstName(), foundFamilyRelative1.getLastName(), 42, foundFamilyRelative1.getEmail(), foundMedicalRecordForFamilyRelative1);

    MedicalRecordsDTO foundMedicalRecordForFamilyRelative2 = new MedicalRecordsDTO();
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundFamilyRelative2)).thenReturn(foundMedicalRecordForFamilyRelative2);
    PersonInfoDTO convertedFamilyRelative2 = new PersonInfoDTO(foundFamilyRelative2.getFirstName(), foundFamilyRelative2.getLastName(), 42, foundFamilyRelative2.getEmail(), foundMedicalRecordForFamilyRelative2);

    MedicalRecordsDTO foundMedicalRecordForFamilyRelative3 = new MedicalRecordsDTO();
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundFamilyRelative3)).thenReturn(foundMedicalRecordForFamilyRelative3);
    PersonInfoDTO convertedFamilyRelative3 = new PersonInfoDTO(foundFamilyRelative3.getFirstName(), foundFamilyRelative3.getLastName(), 42, foundFamilyRelative3.getEmail(), foundMedicalRecordForFamilyRelative3);

    Collection<PersonInfoDTO> expectedFamilyRelativesInPersonDTO = new ArrayList<>();
    expectedFamilyRelativesInPersonDTO.add(convertedFamilyRelative1);
    expectedFamilyRelativesInPersonDTO.add(convertedFamilyRelative2);
    expectedFamilyRelativesInPersonDTO.add(convertedFamilyRelative3);


    PersonsInfoDTO expected = new PersonsInfoDTO();
    expected.setPersonsInfo(expectedPersonInfoDTO);
    expected.setPersonsFromTheSameFamily(expectedFamilyRelativesInPersonDTO);


    //when
    PersonsInfoDTO result = iPersonInfoService.getPersonInfoFromFirstAndOrLastName(givenFirstName, givenLastName);
    //then
    assertThat(result).isEqualTo(expected);
    verify(iPersonServiceMocked, Mockito.times(1)).getByFirstNameAndLastName(givenFirstName,givenLastName);
    verify(ageServiceMocked,Mockito.times(4)).getAge(givenBirthDate);
    verify(iPersonServiceMocked,Mockito.times(1)).getAllByName(givenLastName);
  }

}
