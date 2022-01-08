package org.safetynet.p5safetynetalert.dbapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.model.dto.FloodPersonsListDTO;
import org.safetynet.p5safetynetalert.dbapi.service.urls.FloodService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.JsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(controllers = FloodRestController.class)
public class FloodRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JsonDataInjectorService jsonDataInjectorService;

  @MockBean
  private FloodService floodService;

  @BeforeEach
  public void initTest() {
    Collection<String> stations = new ArrayList<>();
    for (Integer i = 1; i < 4; i++) {
      stations.add(i.toString());
    }
    FloodPersonsListDTO floodPersonsListDTO = new FloodPersonsListDTO();

    when(floodService.getPersonsFloodDTOFromFireStation(stations)).thenReturn(floodPersonsListDTO);
  }

  @Test
  public void getPersonsFloodDTOFromFireStationsTest() throws Exception {
    //GIVEN in init Test

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("stations", "1");
    params.add("stations", "2");
    params.add("stations", "3");

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .get("/flood/stations")
//      .param("stations","1,2,3")
      .params(params)
      .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isOk());

  }

}
