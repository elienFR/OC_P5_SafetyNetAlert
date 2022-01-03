package org.safetynet.p5safetynetalert.dbapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PhonesDTO;
import org.safetynet.p5safetynetalert.dbapi.service.urls.FireStationService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.JsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneAlertRestController.class)
public class PhoneAlertRestControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private JsonDataInjectorService jsonDataInjectorService;
  @MockBean
  private FireStationService fireStationServiceMocked;

  @BeforeEach
  public void initTest() {
    PhonesDTO phones = new PhonesDTO();
    when(fireStationServiceMocked.getPhonesFromFireStationNumber("1")).thenReturn(phones);
  }

  @Test
  public void getPhoneListFromFireStationTest() throws Exception {
    //GIVEN
    //in initTest()
    //firestation is set to "1"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/phoneAlert")
        .param("firestation", "1")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void getPhoneListFromFireStation404Test() throws Exception {
    //GIVEN
    //in initTest()
    //firestation is set to "1"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/phoneAlert")
        .param("firestation", "2")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isNotFound());
  }

  @Test
  public void testGetPhoneListFromFireStation400() throws Exception {
    //GIVEN
    //in initTest()
    //firestation is set to "1"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/phoneAlert")
        .param("firestation_bad", "2")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isBadRequest());
  }

}
