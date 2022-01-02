package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class AgeService {

  /**
   * Parse a string patterned MM/dd/yyyy into a LocalDate
   *
   * @param date is the string patterned MM/dd/yyyy
   * @return the date in LocalDate time format
   */
  private static LocalDate parseStringMMddYYYY(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    return LocalDate.parse(date, formatter);
  }

  /**
   * Calculate majority over eighteen years old
   *
   * @param birthDate is the date of birth given with the pattern MM/dd/yyyy
   * @return true if age is strictly superior to eighteen years old
   * @throws IllegalArgumentException when age is negative
   */
  public boolean isStrictlyOverEighteen(String birthDate) throws Exception {
    LocalDate formattedBirthDate = parseStringMMddYYYY(birthDate);
    return isStrictlyOverEighteen(formattedBirthDate);
  }

  /**
   * Calculate majority over eighteen years old
   *
   * @param birthDate is the date of birth
   * @return true if age is strictly superior to eighteen years old
   * @throws IllegalArgumentException when age is negative
   */
  public boolean isStrictlyOverEighteen(LocalDate birthDate) throws Exception {
    Period period = Period.between(birthDate, LocalDate.now());
    boolean isMajor = false;
    if (period.isNegative() || period.isZero()) {
      throw new IllegalArgumentException("Error while calculating Age. Age is negative.");
    } else {
      if (period.getYears() > 18) {
        isMajor = true;
      }
      return isMajor;
    }
  }

  /**
   * Return the age regarding a certain birth date.
   *
   * @param birthDate a string patterned MM/dd/yyyy
   * @return An integer of the age in Year. Under a year it says 0 years old.
   */
  public Integer getAge(String birthDate) {
    LocalDate formattedBirthDate = parseStringMMddYYYY(birthDate);
    Period period = Period.between(formattedBirthDate, LocalDate.now());
    return period.getYears();
  }

  public Map<String, Integer> countAdultsAndChildren(Collection<PersonDTO> persons) throws Exception {
    Map<String,Integer> adultsAndChildrenCount = new HashMap<>();
    adultsAndChildrenCount.put("adults",0);
    adultsAndChildrenCount.put("children",0);
    for (PersonDTO person : persons) {
      if (isStrictlyOverEighteen(person.getBirthDate())) {
        Integer current = adultsAndChildrenCount.get("adults");
        adultsAndChildrenCount.put("adults", current+1);
      } else {
        Integer current = adultsAndChildrenCount.get("children");
        adultsAndChildrenCount.put("children", current+1);
      }
    }
    return adultsAndChildrenCount;
  }
}
