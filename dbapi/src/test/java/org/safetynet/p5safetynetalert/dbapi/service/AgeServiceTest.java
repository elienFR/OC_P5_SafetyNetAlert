package org.safetynet.p5safetynetalert.dbapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class AgeServiceTest {

  private AgeService ageService;

  @BeforeEach
  public void initTest() {
    ageService = new AgeService();
  }

  @Test
  public void isStrictlyOverEighteenWithStringedAgeTest() {
    //GIVEN
    String testedAge = "01/01/1965";
    boolean isMajor;

    //WHEN
    isMajor = ageService.isStrictlyOverEighteen(testedAge);

    //THEN
    assertThat(isMajor).isTrue();
  }

  @Test
  public void isUnderEighteenWithStringedAgeTest(){
    //GIVEN
    String testedBirthDate = "01/01/";
    Integer yearNow = LocalDate.now().getYear();
    Integer howManyYearsOld = 10;
    // Ten years old person
    testedBirthDate = testedBirthDate + (yearNow - howManyYearsOld);

    AgeService ageService = new AgeService();
    boolean isMajor;

    //WHEN
    isMajor = ageService.isStrictlyOverEighteen(testedBirthDate);

    //THEN
    assertThat(isMajor).isFalse();
  }

  @Test
  public void isStrictlyOverEighteenThrowsExceptionForNegativeAge() {
    //GIVEN
    LocalDate testedBirthDate = LocalDate.of(LocalDate.now().getYear() + 1, 1, 1);

    //WHEN

    //THEN
    assertThrows(IllegalArgumentException.class,
      () -> ageService.isStrictlyOverEighteen(testedBirthDate));
  }

  @Test
  public void isOverEighteenWithLocalDatedAgeTest(){
    //GIVEN
    LocalDate testedBirthDate = LocalDate.of(1965, 1, 1);
    boolean isMajor;

    //WHEN
    isMajor = ageService.isStrictlyOverEighteen(testedBirthDate);

    //THEN
    assertThat(isMajor).isTrue();
  }

  @Test
  public void isUnderEighteenWithLocalDatedAgeTest() {
    //GIVEN
    LocalDate testedBirthDate = LocalDate.of(LocalDate.now().getYear() - 1, 1, 1);
    boolean isMajor;

    //WHEN
    isMajor = ageService.isStrictlyOverEighteen(testedBirthDate);

    //THEN
    assertThat(isMajor).isFalse();
  }

  @Test
  public void countAdultsAndChildren() {
    //Given
    Collection<PersonDTO> personDTOCollectionToTest = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      personDTOCollectionToTest.add(
        new PersonDTO(null, null, null, "01/01/1965", null)
      );
    }
    for (int i = 0; i < 2; i++) {
      personDTOCollectionToTest.add(
        new PersonDTO(null, null, null, "01/01/" + (LocalDate.now().getYear() - 4), null)
      );
    }

    //When
    Map<String,Integer> adultsAndChildren = ageService.countAdultsAndChildren(personDTOCollectionToTest);

    //Then
    assertThat(adultsAndChildren.get("adults")).isEqualTo(3);
    assertThat(adultsAndChildren.get("children")).isEqualTo(2);
  }

}
