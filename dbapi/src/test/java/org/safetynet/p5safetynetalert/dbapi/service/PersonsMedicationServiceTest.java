package org.safetynet.p5safetynetalert.dbapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Medication;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.model.entity.PersonsMedication;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonsMedicationRepository;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonsMedicationServiceTest {

  @Autowired
  private IPersonsMedicationService iPersonsMedicationService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private PersonsMedicationRepository personsMedicationRepository;


  @Test
  public void getMedicationsFromPersonsMedicationsTest() {
    //given
    List<String> expectedContent = new ArrayList<>(List.of("someMeds", "someOtherMeds"));
    List<PersonsMedication> givenPersonsMedicationList = new ArrayList<>();
    Person personConcerned = new Person();
    givenPersonsMedicationList.add(
      new PersonsMedication(
        personConcerned,
        new Medication("someMeds")
      )
    );
    givenPersonsMedicationList.add(
      new PersonsMedication(
        personConcerned,
        new Medication("someOtherMeds")
      )
    );
    //when
    List<String> result = iPersonsMedicationService.getMedicationsFromPersonsMedications(givenPersonsMedicationList);
    //then
    assertThat(result).containsAll(expectedContent);
    assertThat(result.size()).isEqualTo(expectedContent.size());
  }

  @Test
  public void deleteTest() {
    //given
    Iterable<PersonsMedication> givenPersonsMedicationList = new ArrayList<>();
    //when
    iPersonsMedicationService.deleteAll(givenPersonsMedicationList);
    //then
    verify(personsMedicationRepository, Mockito.times(1)).deleteAll(givenPersonsMedicationList);
  }

  @Test
  public void saveTest() {
    //given
    Person givenPerson = new Person("john","doe",null,null,null,null);
    Medication givenMedication = new Medication("someMeds");
    PersonsMedication expectedPersonsMedication = new PersonsMedication(givenPerson, givenMedication);
    PersonsMedication savedPersonsMeds = new PersonsMedication(givenPerson,givenMedication);
    when(personsMedicationRepository.save(savedPersonsMeds)).thenReturn(expectedPersonsMedication);
    //when
    PersonsMedication result = iPersonsMedicationService.save(savedPersonsMeds);
    //then
    assertThat(result).isEqualTo(expectedPersonsMedication);
    assertThat(result.getMedication().getName()).isEqualTo("someMeds");
    assertThat(result.getPerson().getFirstName()).isEqualTo("john");
    verify(personsMedicationRepository,Mockito.times(1)).save(savedPersonsMeds);
  }

  @Test
  public void deleteAllFromPersonTest(){
    //given
    Person givenPersonToWhomWeDeleteMeds = new Person();
    //when
    iPersonsMedicationService.deleteAllFromPerson(givenPersonToWhomWeDeleteMeds);
    //then
    verify(personsMedicationRepository, Mockito.times(1)).findAllByPerson(givenPersonToWhomWeDeleteMeds);
    verify(personsMedicationRepository, Mockito.times(1)).deleteAll(anyIterable());
  }

}
