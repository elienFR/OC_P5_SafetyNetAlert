package org.safetynet.p5safetynetalert.dbapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.service.MajorityCalculatorService;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class MajorityCalculatorTest {

  private MajorityCalculatorService majorityCalculatorService;

  @BeforeEach
  public void initTest(){
     majorityCalculatorService = new MajorityCalculatorService();
  }

  @Test
  public void isStrictlyOverEighteenWithStringedAgeTest() throws Exception {
    //GIVEN
    String testedAge = "01/01/1965";
    boolean isMajor = false;

    //WHEN
    isMajor =  majorityCalculatorService.isStrictlyOverEighteen(testedAge);

    //THEN
    assertThat(isMajor).isTrue();
  }

  @Test
  public void isUnderEighteenWithStringedAgeTest() throws Exception {
    //GIVEN
    String testedBirthDate = "01/01/";
    Integer yearNow = LocalDate.now().getYear();
    Integer howManyYearsOld = 10;
    // Ten years old person
    testedBirthDate = testedBirthDate + (yearNow-howManyYearsOld);

    MajorityCalculatorService majorityCalculatorService = new MajorityCalculatorService();
    boolean isMajor = true;

    //WHEN
    isMajor =  majorityCalculatorService.isStrictlyOverEighteen(testedBirthDate);

    //THEN
    assertThat(isMajor).isFalse();
  }

  @Test
  public void isStrictlyOverEighteenThrowsExceptionForNegativeAge() throws Exception {
    //GIVEN
    LocalDate testedBirthDate = LocalDate.of(LocalDate.now().getYear()+1,1,1);

    //WHEN

    //THEN
    assertThrows(IllegalArgumentException.class,
        () -> majorityCalculatorService.isStrictlyOverEighteen(testedBirthDate));
  }

  @Test
  public void isOverEighteenWithLocalDatedAgeTest() throws Exception {
    //GIVEN
    LocalDate testedBirthDate = LocalDate.of(1965,1,1);
    boolean isMajor = false;

    //WHEN
    isMajor =  majorityCalculatorService.isStrictlyOverEighteen(testedBirthDate);

    //THEN
    assertThat(isMajor).isTrue();
  }

  @Test
  public void isUnderEighteenWithLocalDatedAgeTest() throws Exception {
    //GIVEN
    LocalDate testedBirthDate = LocalDate.of(LocalDate.now().getYear()-1, 1,1);
    boolean isMajor = true;

    //WHEN
    isMajor =  majorityCalculatorService.isStrictlyOverEighteen(testedBirthDate);

    //THEN
    assertThat(isMajor).isFalse();
  }

}
