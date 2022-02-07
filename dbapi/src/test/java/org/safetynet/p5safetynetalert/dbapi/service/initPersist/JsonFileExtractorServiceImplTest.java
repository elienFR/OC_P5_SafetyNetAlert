package org.safetynet.p5safetynetalert.dbapi.service.initPersist;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.safetynet.p5safetynetalert.dbapi.CustomProperties;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


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
