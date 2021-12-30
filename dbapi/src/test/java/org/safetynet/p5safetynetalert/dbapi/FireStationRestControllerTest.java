package org.safetynet.p5safetynetalert.dbapi;

import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.controller.FireStationRestController;
import org.safetynet.p5safetynetalert.dbapi.model.FireStation;
import org.safetynet.p5safetynetalert.dbapi.service.FireStationService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.JsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

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

  @Test
  public void testGetPersonsFromFireStationNumber() throws Exception {
    FireStation fireStation = new FireStation();
    fireStation.setNumber("6");
    List<FireStation> persons = new ArrayList<>();

    when(fireStationServiceMock.getFireStations()).thenReturn(persons);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/testPersonFireStation")
        .param("number", "6")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isOk());

  }


}
