package org.safetynet.p5safetynetalert.dbapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Medication;
import org.safetynet.p5safetynetalert.dbapi.repository.MedicationRepository;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MedicationServiceTest {

  @Autowired
  private MedicationService medicationService;

  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;

  @MockBean
  private MedicationRepository medicationRepository;

  @Test
  public void getMedicationsTest() {
    //given
    Iterable<Medication> expected = new ArrayList<>();
    when(medicationRepository.findAll()).thenReturn(expected);

    //when
    Iterable<Medication> result = medicationService.getMedications();
    //then
    assertThat(result).isEqualTo(expected);
    verify(medicationRepository, Mockito.times(1)).findAll();
  }

  @Test
  public void saveTest() {
    //given
    Medication medToSave = new Medication();
    Medication expected = new Medication();
    when(medicationRepository.save(medToSave)).thenReturn(expected);
    //when
    Medication result = medicationService.save(medToSave);
    //then
    assertThat(result).isEqualTo(expected);
    verify(medicationRepository, Mockito.times(1)).save(medToSave);
  }

  @Test
  public void existsByNameTest() {
    //given
    String medToCheck = "someMeds";
    boolean expected = true;
    when(medicationRepository.existsByName(medToCheck)).thenReturn(expected);
    //when
    boolean result = medicationService.existsByName(medToCheck);
    //then
    assertThat(result).isEqualTo(expected);
    verify(medicationRepository, Mockito.times(1)).existsByName(medToCheck);
  }

  @Test
  public void getByName() {
    //given
    String medToGet = "someMeds";
    Medication expected = new Medication();
    when(medicationRepository.findByName(medToGet)).thenReturn(expected);
    //when
    Medication result = medicationService.getByName(medToGet);
    //then
    assertThat(result).isEqualTo(expected);
    verify(medicationRepository, Mockito.times(1)).findByName(medToGet);
  }

}
