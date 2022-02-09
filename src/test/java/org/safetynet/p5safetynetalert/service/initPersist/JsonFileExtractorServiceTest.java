package org.safetynet.p5safetynetalert.service.initPersist;

import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.CustomProperties;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
public class JsonFileExtractorServiceTest {

  @Autowired
  private IJsonFileExtractorService iJsonFileExtractorServiceUnderTest;

  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;

  @MockBean
  private CustomProperties customPropertiesMocked;

  @Test
  public void fromFileTest() {
    //given
    String givenJsonFileName = "dataTest.json";
    String givenMainResourcePath = "./src/test/resources/";

    when(customPropertiesMocked.getMainResourcesPath()).thenReturn(givenMainResourcePath);

    //when
    JsonData result = iJsonFileExtractorServiceUnderTest.fromFile(givenJsonFileName);

    //then
    assertThat(result.getPersons().getPersons().size()).isGreaterThan(0);
    assertThat(result.getFireStations().getFirestations().size()).isGreaterThan(0);
    assertThat(result.getMedicalRecords().getMedicalrecords().size()).isGreaterThan(0);
  }

  @Test
  public void fromFileTestNull() {
    //given
    String givenJsonFileName = "";
    String givenMainResourcePath = "./src/test/resources/";

    when(customPropertiesMocked.getMainResourcesPath()).thenReturn(givenMainResourcePath);

    //when
    JsonData result = iJsonFileExtractorServiceUnderTest.fromFile(givenJsonFileName);

    //then
    assertThat(result).isNull();
  }

}
