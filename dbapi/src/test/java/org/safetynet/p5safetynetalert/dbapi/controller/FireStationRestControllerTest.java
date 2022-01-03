package org.safetynet.p5safetynetalert.dbapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsFromFireStationDTO;
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

@WebMvcTest(controllers = FireStationRestController.class)
public class FireStationRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JsonDataInjectorService jsonDataInjectorServiceMock;

  @MockBean
  private FireStationService fireStationServiceMock;

  @BeforeEach
  public void initTest() {
    PersonsFromFireStationDTO persons = new PersonsFromFireStationDTO();
    when(fireStationServiceMock.getPersonsAndCount("1")).thenReturn(persons);
  }

  @Test
  public void testGetPersonsFromFireStationNumber() throws Exception {
    //GIVEN
    //is in initTest()
    //firestation number is set to "1"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/firestation")
        .param("stationNumber", "1")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void testGetPersonsFromFireStationNumber404() throws Exception {
    //GIVEN
    //is in initTest()
    //firestation number is set to "1"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/firestation")
        .param("stationNumber", "2")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isNotFound());
  }

  @Test
  public void testGetPersonsFromFireStationNumber400() throws Exception {
    //GIVEN
    //is in initTest()
    //firestation number is set to "1"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/firestation")
        .param("stationNumber_bad", "2")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isBadRequest());
  }


}
