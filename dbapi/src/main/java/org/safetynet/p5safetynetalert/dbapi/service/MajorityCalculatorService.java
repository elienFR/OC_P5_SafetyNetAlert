package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class MajorityCalculatorService {
  private static boolean isStrictlyOverEighteen(LocalDate birthDate) throws Exception {
    double period = Period.between(birthDate, LocalDate.now()).getYears();
    boolean isMajor = false;
    if (period > 18) {
      isMajor = true;
    }
    return isMajor;
  }

  public Integer countAdults(Iterable<Person> listOfPersons) throws Exception {
    Integer count = 0;
    for (Person person : listOfPersons) {
      LocalDate birthDate = parseStringMMddYYYY(person.getBirthDate());
      if (isStrictlyOverEighteen(birthDate)) {
        count += 1;
      }
    }
    return count;
  }

  public Integer countChildren(Iterable<Person> listOfPersons) throws Exception {
    Integer count = 0;
    for (Person person : listOfPersons) {
      LocalDate birthDate = parseStringMMddYYYY(person.getBirthDate());
      if (!isStrictlyOverEighteen(birthDate)) {
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
