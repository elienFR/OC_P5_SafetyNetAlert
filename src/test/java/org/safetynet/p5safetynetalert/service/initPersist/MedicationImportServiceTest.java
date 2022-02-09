package org.safetynet.p5safetynetalert.service.initPersist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.data.JsonDataGenerator;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.repository.MedicationRepository;
import org.safetynet.p5safetynetalert.service.initPersist.utils.MedicationImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class MedicationImportServiceTest {

  @Autowired
  private MedicationImportService medicationImportServiceUnderTest;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private MedicationRepository medicationRepositoryMocked;

  private JsonData jsonData;
  private JsonDataGenerator jsonDataGenerator = new JsonDataGenerator();

  @BeforeEach
  public void setup() {
    jsonData = jsonDataGenerator.generate();
  }

  @Test
  public void importMedicationsTest() {
    //when
    medicationImportServiceUnderTest.importMedications(jsonData);
    //then
    verify(medicationRepositoryMocked, Mockito.atLeastOnce()).saveAll(any(List.class));
  }

}
