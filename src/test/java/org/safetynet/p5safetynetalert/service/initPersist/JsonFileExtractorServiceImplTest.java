package org.safetynet.p5safetynetalert.service.initPersist;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.safetynet.p5safetynetalert.CustomProperties;

import static org.assertj.core.api.Assertions.assertThat;


public class JsonFileExtractorServiceImplTest {

  private IJsonFileExtractorService iJsonFileExtractorServiceUnderTest;
  @Mock
  private CustomProperties customPropertiesMocked;

  @BeforeEach
  public void initTest(){
    iJsonFileExtractorServiceUnderTest = new JsonFileExtractorServiceImpl();
  }


//  @Test
//  public void fromFileTest() {
//    //given
//    String givenFileName = "dataTest.json";
//
//    when(customPropertiesMocked.getMainResourcesPath()).thenReturn("test");
//
//    JsonData expected = new JsonData();
//
//    //when
//    JsonData result = iJsonFileExtractorServiceUnderTest.fromFile(givenFileName);
//
//    //then
//    assertThat(result).isEqualTo(expected);
//  }

}
