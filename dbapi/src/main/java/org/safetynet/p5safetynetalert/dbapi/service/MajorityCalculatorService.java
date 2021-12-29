package org.safetynet.p5safetynetalert.dbapi.service;

import org.apache.tomcat.jni.Local;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class MajorityCalculatorService {
  public static boolean isOverEighteen(LocalDate birthDate) throws Exception {
    double period = Period.between(birthDate, LocalDate.now()).getYears();
    boolean isMajor = false;
    if (period >= 18) {
      isMajor = true;
    }
    return isMajor;
  }

  public Integer countMajors(Iterable<Person> listOfPersons) throws Exception {
    Integer count = 0;
    for (Person person : listOfPersons) {
      LocalDate birthDate = parseStringMMddYYYY(person.getBirthDate());
      if (isOverEighteen(birthDate)) {
        count += 1;
      }
    }
    return count;
  }

  public Integer countMinors(Iterable<Person> listOfPersons) throws Exception {
    Integer count = 0;
    for (Person person : listOfPersons) {
      LocalDate birthDate = parseStringMMddYYYY(person.getBirthDate());
      if (!isOverEighteen(birthDate)) {
        count += 1;
      }
    }
    return count;
  }

  private LocalDate parseStringMMddYYYY(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    return LocalDate.parse(date, formatter);
  }
}
