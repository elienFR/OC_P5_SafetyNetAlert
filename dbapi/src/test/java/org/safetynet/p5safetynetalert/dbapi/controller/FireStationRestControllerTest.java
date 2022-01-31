package org.safetynet.p5safetynetalert.dbapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.safetynet.p5safetynetalert.dbapi.service.urls.IFireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationRestController.class)
public class FireStationRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IJsonDataInjectorService IJsonDataInjectorServiceMock;

  @MockBean
  private IFireStationService iFireStationServiceMock;

  @BeforeEach
  public void initTest() {

  }

  @Test
  public void getPersonsFromFireStationNumberTest() throws Exception {
    //GIVEN
    PersonsFromFireStationDTO persons = new PersonsFromFireStationDTO();
    when(iFireStationServiceMock.getPersonsAndCount("1")).thenReturn(persons);

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .get("/firestation")
      .param("stationNumber", "1")
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void getPersonsFromFireStationNumberTest404() throws Exception {
    //GIVEN
    PersonsFromFireStationDTO persons = new PersonsFromFireStationDTO();
    when(iFireStationServiceMock.getPersonsAndCount("1")).thenReturn(persons);

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .get("/firestation")
      .param("stationNumber", "2")
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isNotFound());
  }

  @Test
  public void getPersonsFromFireStationNumberTest400() throws Exception {
    //GIVEN
    PersonsFromFireStationDTO persons = new PersonsFromFireStationDTO();
    when(iFireStationServiceMock.getPersonsAndCount("1")).thenReturn(persons);

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .get("/firestation")
      .param("stationNumber_bad", "2")
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isBadRequest());
  }

  @Test
  public void postFireStationAddressMappingTest() throws Exception {
    //GIVEN
    JsonFireStation jsonFireStation = new JsonFireStation("some address", "1");
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonFireStation);
    when(iFireStationServiceMock.saveAddressFireStationMapping(jsonFireStation)).thenReturn(new JsonFireStation());

    //WHEN

    //THEN
    mockMvc
      .perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isOk());
  }

  @Test
  public void postFireStationAddressMappingTestNullBody() throws Exception {
    //GIVEN
    JsonFireStation jsonFireStation = null;
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonFireStation);

    //WHEN

    //THEN
    mockMvc
      .perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNoContent());
  }

  @Test
  public void postFireStationAddressMappingTestNullJson() throws Exception {
    //GIVEN
    JsonFireStation jsonFireStation = new JsonFireStation();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonFireStation);
    when(iFireStationServiceMock.saveAddressFireStationMapping(jsonFireStation)).thenReturn(null);

    //WHEN

    //THEN
    mockMvc
      .perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNoContent());
  }

  @Test
  public void putFireStationAddressMappingTest() throws Exception {
    //GIVEN
    JsonFireStation jsonFireStation = new JsonFireStation();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonFireStation);
    when(iFireStationServiceMock.updateAddressFireStationMapping(jsonFireStation)).thenReturn(new JsonFireStation());

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .put("/firestation")
      .content(jsonStringTested)
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void putFireStationAddressMappingTestNullBody() throws Exception {
    //GIVEN
    JsonFireStation jsonFireStation = null;
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonFireStation);

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .put("/firestation")
      .content(jsonStringTested)
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isNoContent());
  }

  @Test
  public void putFireStationAddressMappingTestNotFound() throws Exception {
    //GIVEN
    JsonFireStation jsonFireStation = new JsonFireStation();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonFireStation);
    when(iFireStationServiceMock.updateAddressFireStationMapping(jsonFireStation)).thenReturn(null);

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .put("/firestation")
      .content(jsonStringTested)
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isNotFound());
  }

  @Test
  public void deleteFireStationAddressMappingTest() throws Exception {
    //GIVEN
    JsonFireStation jsonFireStation = new JsonFireStation("some address","3");
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonFireStation);
    when(iFireStationServiceMock.eraseAddressFireStationMapping(jsonFireStation)).thenReturn(new JsonFireStation());

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .delete("/firestation")
      .content(jsonStringTested)
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void deleteFireStationAddressMappingTestNullBody() throws Exception {
    //GIVEN
    JsonFireStation jsonFireStation = null;
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonFireStation);

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .delete("/firestation")
      .content(jsonStringTested)
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isNoContent());
  }

  @Test
  public void deleteFireStationAddressMappingTestNotFound() throws Exception {
    //GIVEN
    JsonFireStation jsonFireStation = new JsonFireStation("some address","3");
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonFireStation);
    when(iFireStationServiceMock.eraseAddressFireStationMapping(jsonFireStation)).thenReturn(null);

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .delete("/firestation")
      .content(jsonStringTested)
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isNotFound());
  }

}
