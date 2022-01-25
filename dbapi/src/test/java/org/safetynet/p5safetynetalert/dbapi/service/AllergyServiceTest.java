package org.safetynet.p5safetynetalert.dbapi.service;

import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Allergy;
import org.safetynet.p5safetynetalert.dbapi.repository.AllergyRepository;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AllergyServiceTest {

  @Autowired
  private AllergyService allergyService;
  @MockBean
  private AllergyRepository allergyRepositoryMocked;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;

  @Test
  public void getAllergiesTest() {
    //given
    Iterable<Allergy> expected = new ArrayList<>();
    when(allergyRepositoryMocked.findAll()).thenReturn(expected);
    //when
    Iterable<Allergy> result = allergyService.getAllergies();
    //then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void saveTest() {
    //given
    Allergy givenAllergy = new Allergy();
    Allergy expected = new Allergy();
    when(allergyRepositoryMocked.save(givenAllergy)).thenReturn(expected);
    //when
    Allergy result = allergyService.save(givenAllergy);
    //then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void existsTest() {
    //given
    String givenAllergy = "";
    boolean expected = true;
    when(allergyRepositoryMocked.existsByName(givenAllergy)).thenReturn(expected);
    //when
    boolean result = allergyService.exists(givenAllergy);
    //then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getByNameTest() {
    //given
    String givenAllergy = "";
    Allergy expected = new Allergy();
    when(allergyRepositoryMocked.findByName(givenAllergy)).thenReturn(expected);
    //when
    Allergy result = allergyService.getByName(givenAllergy);
    //then
    assertThat(result).isEqualTo(expected);
  }
}
