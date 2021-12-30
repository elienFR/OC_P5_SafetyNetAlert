package org.safetynet.p5safetynetalert.dbapi;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.service.MajorityCalculatorService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MajorityCalculatorTest {



  @Test
  public void countAdultsTest() throws Exception {
    //GIVEN
    Person person1 = new Person();
    person1.setBirthDate("01/01/1964");
    Person person2 = new Person();
    person2.setBirthDate("01/01/1965");
    Person person3 = new Person();
    person3.setBirthDate("01/01/1966");
    List<Person> testedPersons = new ArrayList<>();
    testedPersons.add(person1);
    testedPersons.add(person2);
    testedPersons.add(person3);
    Integer expectedAdultNumber = 3;
    Integer actualNumber = 0;
    MajorityCalculatorService majorityCalculatorService = new MajorityCalculatorService();

    //WHEN
    actualNumber = majorityCalculatorService.countAdults(testedPersons);

    //THEN
    assertThat(actualNumber).isEqualTo(expectedAdultNumber);
  }

  @Test
  public void countChildrenTest() throws Exception {
    //GIVEN
    Integer actualYear = LocalDate.now().getYear();
    Person person1 = new Person();
    person1.setBirthDate("01/01/"+(actualYear-10));
    Person person2 = new Person();
    person2.setBirthDate("01/01/"+(actualYear-11));
    Person person3 = new Person();
    person3.setBirthDate("01/01/"+(actualYear-12));
    List<Person> testedPersons = new ArrayList<>();
    testedPersons.add(person1);
    testedPersons.add(person2);
    testedPersons.add(person3);
    Integer expectedChildrenNumber = 3;
    Integer actualNumber = 0;
    MajorityCalculatorService majorityCalculatorService = new MajorityCalculatorService();

    //WHEN
    actualNumber = majorityCalculatorService.countChildren(testedPersons);

    //THEN
    assertThat(actualNumber).isEqualTo(expectedChildrenNumber);
  }
}
