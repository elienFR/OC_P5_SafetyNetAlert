package org.safetynet.p5safetynetalert.dbapi;


import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.service.MajorityCalculatorService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AgeCalculatorTest {

  @Test
  public void isOverEighteenTest() throws Exception {
    //GIVEN
    LocalDate birtDate = LocalDate.now();
    birtDate = birtDate.minusYears(18);
    boolean isMajor = false;

    //WHEN
    isMajor = MajorityCalculatorService.isOverEighteen(birtDate);

    //THEN
    assertThat(isMajor).isTrue();
  }

  @Test
  public void isUnderEighteenTest() throws Exception {
    //GIVEN
    LocalDate birtDate = LocalDate.now();
    birtDate = birtDate.minusYears(17);
    boolean isMajor = false;

    //WHEN
    isMajor = MajorityCalculatorService.isOverEighteen(birtDate);

    //THEN
    assertThat(isMajor).isFalse();
  }

}
