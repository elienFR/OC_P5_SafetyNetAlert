package org.safetynet.p5safetynetalert.service.initPersist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.data.JsonDataGenerator;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.repository.AllergyRepository;
import org.safetynet.p5safetynetalert.service.initPersist.utils.AllergyImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AllergyImportServiceTest {


  private static JsonData jsonData;
  @Autowired
  private AllergyImportService allergyImportService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private AllergyRepository allergyRepository;
  private JsonDataGenerator jsonDataGenerator = new JsonDataGenerator();

  @BeforeEach
  public void setup() {
    jsonData = jsonDataGenerator.generate();
  }

  @Test
  public void importAllergies() {
    //when
    allergyImportService.importAllergies(jsonData);

    //then
    verify(allergyRepository, Mockito.atLeastOnce()).saveAll(any(List.class));
  }
}
