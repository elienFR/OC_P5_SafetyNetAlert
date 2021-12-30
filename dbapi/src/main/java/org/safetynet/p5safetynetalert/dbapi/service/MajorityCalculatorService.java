package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class MajorityCalculatorService {

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
    }
    else {
      if (period.getYears() > 18) {
        isMajor = true;
      }
      return isMajor;
    }
  }
}
