package org.safetynet.p5safetynetalert.dbapi.controller;

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

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FloodRestController.class)
public class FloodRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JsonDataInjectorService jsonDataInjectorService;

  @MockBean
  private FloodService floodService;

  @Test
  public void getPersonsFloodDTOFromFireStationsTest() throws Exception{
    Collection<String> stations = new ArrayList<>();
    for (Integer i = 1; i < 4; i++) {
      stations.add(i.toString());
    }
    FloodPersonsListDTO floodPersonsListDTO = new FloodPersonsListDTO();

    when(floodService.getPersonsFloodDTOFromFireStation(stations)).thenReturn(floodPersonsListDTO);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .get("/flood/stations")
      .param("stations","1")
//      .param("stations","2")
//      .param("stations","3")
      .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isOk());

  }

}
