package org.safetynet.p5safetynetalert.service.initPersist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.data.JsonDataGenerator;
import org.safetynet.p5safetynetalert.model.entity.Address;
import org.safetynet.p5safetynetalert.model.entity.FireStation;
import org.safetynet.p5safetynetalert.model.initPersist.*;
import org.safetynet.p5safetynetalert.repository.AddressRepository;
import org.safetynet.p5safetynetalert.repository.FireStationRepository;
import org.safetynet.p5safetynetalert.service.initPersist.utils.AddressImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AddressImportServiceTest {

  private static JsonData jsonData;
  @Autowired
  private AddressImportService addressImportServiceUnderTest;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorServiceMocked;
  @MockBean
  private AddressRepository addressRepositoryMocked;
  @MockBean
  private FireStationRepository fireStationRepositoryMocked;
  private JsonDataGenerator jsonDataGenerator = new JsonDataGenerator();

  @BeforeEach
  public void setup() {
    jsonData = jsonDataGenerator.generate();
  }

  @Test
  public void importAddressesTest() {
    //given
    FireStation givenFireStation = new FireStation();
    when(fireStationRepositoryMocked.findByNumber("someStation")).thenReturn(givenFireStation);
    //when
    addressImportServiceUnderTest.importAddresses(jsonData);
    //then
    verify(addressRepositoryMocked, Mockito.atLeastOnce()).save(any(Address.class));
  }

}
