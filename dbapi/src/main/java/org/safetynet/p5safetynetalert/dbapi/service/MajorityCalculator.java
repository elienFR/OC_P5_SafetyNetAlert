package org.safetynet.p5safetynetalert.dbapi.service;

import java.time.LocalDate;
import java.time.Period;

public class MajorityCalculator {
  public static boolean isOverEighteen(LocalDate birthDate) throws Exception{
    double period = Period.between(birthDate, LocalDate.now()).getYears();
    boolean isMajor = false;
    if (period>=18){
      isMajor=true;
    }
    return isMajor;
  }
}
