package org.safetynet.p5safetynetalert.dbapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.entity.*;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonsAllergyRepository;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonsAllergyServiceTest {

  @Autowired
  private IPersonsAllergyService iPersonsAllergyService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private PersonsAllergyRepository personsAllergyRepository;

  @Test
  public void getAllergiesFromPersonsMedicationsTest() {
    //given
    List<String> expectedContent = new ArrayList<>(List.of("someAllergy", "someOtherAllergy"));
    List<PersonsAllergy> givenPersonsAllergyList = new ArrayList<>();
    Person personConcerned = new Person();
    givenPersonsAllergyList.add(
      new PersonsAllergy(
        personConcerned,
        new Allergy("someAllergy")
      )
    );
    givenPersonsAllergyList.add(
      new PersonsAllergy(
        personConcerned,
        new Allergy("someOtherAllergy")
      )
    );
    //when
    List<String> result = iPersonsAllergyService.getAllergiesFromPersonsMedications(givenPersonsAllergyList);
    //then
    assertThat(result).containsAll(expectedContent);
    assertThat(result.size()).isEqualTo(expectedContent.size());
  }

  @Test
  public void deleteTest() {
    //given
    Iterable<PersonsAllergy> givenPersonsAllergyList = new ArrayList<>();
    //when
    iPersonsAllergyService.delete(givenPersonsAllergyList);
    //then
    verify(personsAllergyRepository, Mockito.times(1)).deleteAll(givenPersonsAllergyList);
  }

  @Test
  public void saveTest() {
//given
    Person givenPerson = new Person("john","doe",null,null,null,null);
    Allergy givenAllergy = new Allergy("someAllergy");
    PersonsAllergy expectedPersonsAllergy = new PersonsAllergy(givenPerson, givenAllergy);
    PersonsAllergy savedPersonsAllergy = new PersonsAllergy(givenPerson,givenAllergy);
    when(personsAllergyRepository.save(savedPersonsAllergy)).thenReturn(expectedPersonsAllergy);
    //when
    PersonsAllergy result = iPersonsAllergyService.save(savedPersonsAllergy);
    //then
    assertThat(result).isEqualTo(expectedPersonsAllergy);
    assertThat(result.getAllergy().getName()).isEqualTo("someAllergy");
    assertThat(result.getPerson().getFirstName()).isEqualTo("john");
    verify(personsAllergyRepository,Mockito.times(1)).save(savedPersonsAllergy);
  }

  @Test
  public void deleteAllFromPersonTest(){
    //given
    Person givenPersonToWhomWeDeleteAllergies = new Person();
    //when
    iPersonsAllergyService.deleteAllFromPerson(givenPersonToWhomWeDeleteAllergies);
    //then
    verify(personsAllergyRepository, Mockito.times(1)).findAllByPerson(givenPersonToWhomWeDeleteAllergies);
    verify(personsAllergyRepository, Mockito.times(1)).deleteAll(anyIterable());
  }

}
