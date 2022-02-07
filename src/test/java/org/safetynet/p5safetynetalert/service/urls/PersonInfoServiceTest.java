package org.safetynet.p5safetynetalert.service.urls;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.model.dto.MedicalRecordsDTO;
import org.safetynet.p5safetynetalert.model.dto.PersonInfoDTO;
import org.safetynet.p5safetynetalert.model.dto.PersonsInfoDTO;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.service.AgeService;
import org.safetynet.p5safetynetalert.service.IMedicalRecordsService;
import org.safetynet.p5safetynetalert.service.IPersonService;
import org.safetynet.p5safetynetalert.service.initPersist.IJsonDataInjectorService;
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
    Person foundPerson = new Person();
    foundPerson.setFirstName("jim");
    foundPerson.setLastName("carrey");
    foundPerson.setBirthDate(givenBirthDate);
    foundPerson.setEmail("someMail");
    when(iPersonServiceMocked.getByFirstNameAndLastName(givenFirstName, givenLastName)).thenReturn(foundPerson);

    when(ageServiceMocked.getAge(foundPerson.getBirthDate())).thenReturn(42);

    MedicalRecordsDTO foundMedicalRecordForPersonInfoDTO = new MedicalRecordsDTO();
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundPerson)).thenReturn(foundMedicalRecordForPersonInfoDTO);

    PersonInfoDTO expectedPersonInfoDTO = new PersonInfoDTO();
    expectedPersonInfoDTO.setFirstName(foundPerson.getFirstName());
    expectedPersonInfoDTO.setLastName(foundPerson.getLastName());
    expectedPersonInfoDTO.setAge(42);
    expectedPersonInfoDTO.setMail(foundPerson.getEmail());
    expectedPersonInfoDTO.setMedicalRecords(foundMedicalRecordForPersonInfoDTO);

    Person foundFamilyRelative1 = new Person();
    foundFamilyRelative1.setFirstName("remi");
    foundFamilyRelative1.setLastName("name");
    foundFamilyRelative1.setBirthDate(givenBirthDate);
    foundFamilyRelative1.setEmail("mail@mail.com");
    Person foundFamilyRelative2 = new Person();
    foundFamilyRelative2.setFirstName("jorge");
    foundFamilyRelative2.setLastName("name");
    foundFamilyRelative2.setBirthDate(givenBirthDate);
    foundFamilyRelative2.setEmail("mail@mail.com");
    Person foundFamilyRelative3 = new Person();
    foundFamilyRelative3.setFirstName("zoe");
    foundFamilyRelative3.setLastName("name");
    foundFamilyRelative3.setBirthDate(givenBirthDate);
    foundFamilyRelative3.setEmail("mail@mail.com");
    
    Collection<Person> foundFamilyRelatives = new ArrayList<>();
    foundFamilyRelatives.add(foundFamilyRelative1);
    foundFamilyRelatives.add(foundFamilyRelative2);
    foundFamilyRelatives.add(foundFamilyRelative3);
    when(iPersonServiceMocked.getAllByName(givenLastName)).thenReturn(foundFamilyRelatives);


    MedicalRecordsDTO foundMedicalRecordForFamilyRelative1 = new MedicalRecordsDTO();
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundFamilyRelative1)).thenReturn(foundMedicalRecordForFamilyRelative1);
    PersonInfoDTO convertedFamilyRelative1 = new PersonInfoDTO();
    convertedFamilyRelative1.setFirstName(foundFamilyRelative1.getFirstName());
    convertedFamilyRelative1.setLastName(foundFamilyRelative1.getLastName());
    convertedFamilyRelative1.setAge(42);
    convertedFamilyRelative1.setMail(foundFamilyRelative1.getEmail());
    convertedFamilyRelative1.setMedicalRecords(foundMedicalRecordForFamilyRelative1);
    
    MedicalRecordsDTO foundMedicalRecordForFamilyRelative2 = new MedicalRecordsDTO();
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundFamilyRelative2)).thenReturn(foundMedicalRecordForFamilyRelative2);
    PersonInfoDTO convertedFamilyRelative2 = new PersonInfoDTO();
    convertedFamilyRelative2.setFirstName(foundFamilyRelative2.getFirstName());
    convertedFamilyRelative2.setLastName(foundFamilyRelative2.getLastName());
    convertedFamilyRelative2.setAge(42);
    convertedFamilyRelative2.setMail(foundFamilyRelative2.getEmail());
    convertedFamilyRelative2.setMedicalRecords(foundMedicalRecordForFamilyRelative2);
    
    MedicalRecordsDTO foundMedicalRecordForFamilyRelative3 = new MedicalRecordsDTO();
    when(iMedicalRecordsServiceMocked.getMedicalRecords(foundFamilyRelative3)).thenReturn(foundMedicalRecordForFamilyRelative3);
    PersonInfoDTO convertedFamilyRelative3 = new PersonInfoDTO();
    convertedFamilyRelative3.setFirstName(foundFamilyRelative3.getFirstName());
    convertedFamilyRelative3.setLastName(foundFamilyRelative3.getLastName());
    convertedFamilyRelative3.setAge(42);
    convertedFamilyRelative3.setMail(foundFamilyRelative3.getEmail());
    convertedFamilyRelative3.setMedicalRecords(foundMedicalRecordForFamilyRelative3);
    
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
