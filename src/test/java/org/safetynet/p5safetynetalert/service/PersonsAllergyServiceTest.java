package org.safetynet.p5safetynetalert.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.repository.PersonsAllergyRepository;
import org.safetynet.p5safetynetalert.service.initPersist.IJsonDataInjectorService;
import org.safetynet.p5safetynetalert.model.entity.Allergy;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.entity.PersonsAllergy;
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

    Allergy linkedAllergy1 = new Allergy();
    linkedAllergy1.setName("someAllergy");
    PersonsAllergy personsAllergy1 = new PersonsAllergy();
    personsAllergy1.setPerson(personConcerned);
    personsAllergy1.setAllergy(linkedAllergy1);

    Allergy linkedAllergy2 = new Allergy();
    linkedAllergy2.setName("someOtherAllergy");
    PersonsAllergy personsAllergy2 = new PersonsAllergy();
    personsAllergy2.setPerson(personConcerned);
    personsAllergy2.setAllergy(linkedAllergy2);

    givenPersonsAllergyList.add(personsAllergy1);
    givenPersonsAllergyList.add(personsAllergy2);

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
    iPersonsAllergyService.deleteAll(givenPersonsAllergyList);
    //then
    verify(personsAllergyRepository, Mockito.times(1)).deleteAll(givenPersonsAllergyList);
  }

  @Test
  public void saveTest() {
//given
    Person givenPerson = new Person();
    givenPerson.setFirstName("john");
    givenPerson.setLastName("doe");

    Allergy givenAllergy = new Allergy();
    givenAllergy.setName("someAllergy");

    PersonsAllergy expectedPersonsAllergy = new PersonsAllergy();
    expectedPersonsAllergy.setPerson(givenPerson);
    expectedPersonsAllergy.setAllergy(givenAllergy);

    PersonsAllergy savedPersonsAllergy = new PersonsAllergy();
    savedPersonsAllergy.setPerson(givenPerson);
    savedPersonsAllergy.setAllergy(givenAllergy);
    when(personsAllergyRepository.save(savedPersonsAllergy)).thenReturn(expectedPersonsAllergy);

    //when
    PersonsAllergy result = iPersonsAllergyService.save(savedPersonsAllergy);
    //then
    assertThat(result).isEqualTo(expectedPersonsAllergy);
    assertThat(result.getAllergy().getName()).isEqualTo("someAllergy");
    assertThat(result.getPerson().getFirstName()).isEqualTo("john");
    verify(personsAllergyRepository, Mockito.times(1)).save(savedPersonsAllergy);
  }

  @Test
  public void deleteAllFromPersonTest() {
    //given
    Person givenPersonToWhomWeDeleteAllergies = new Person();
    //when
    iPersonsAllergyService.deleteAllFromPerson(givenPersonToWhomWeDeleteAllergies);
    //then
    verify(personsAllergyRepository, Mockito.times(1)).findAllByPerson(givenPersonToWhomWeDeleteAllergies);
    verify(personsAllergyRepository, Mockito.times(1)).deleteAll(anyIterable());
  }

}
