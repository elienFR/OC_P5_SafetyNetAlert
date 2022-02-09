package org.safetynet.p5safetynetalert.service.initPersist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.data.JsonDataGenerator;
import org.safetynet.p5safetynetalert.model.entity.FireStation;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.repository.FireStationRepository;
import org.safetynet.p5safetynetalert.service.initPersist.utils.FireStationImportService;
import org.safetynet.p5safetynetalert.service.urls.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class FireStationImportServiceTest {

  @Autowired
  private FireStationImportService fireStationImportServiceUnderTest;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private FireStationRepository fireStationRepositoryMocked;

  private JsonData jsonData;

  private JsonDataGenerator jsonDataGenerator = new JsonDataGenerator();

  @BeforeEach
  public void setup(){
    jsonData = jsonDataGenerator.generate();
  }

  @Test
  public void importFireStations() {
    //when
    fireStationImportServiceUnderTest.importFireStations(jsonData);

    //then
    verify(fireStationRepositoryMocked, Mockito.atLeastOnce()).save(any(FireStation.class));
  }

}
