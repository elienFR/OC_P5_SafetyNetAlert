package org.safetynet.p5safetynetalert.dbapi;

import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.controller.PhoneAlertRestController;
import org.safetynet.p5safetynetalert.dbapi.dto.PhonesDTO;
import org.safetynet.p5safetynetalert.dbapi.service.FireStationService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.JsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;
import java.util.TreeSet;

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

  @Test
  public void getPhoneListFromFireStationTest() throws Exception {
    //GIVEN
    PhonesDTO phones = new PhonesDTO();
    when(fireStationServiceMocked.getPhonesFromFireStationNumber("1")).thenReturn(phones);

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/phoneAlert")
        .param("firestation", "1")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isOk());

  }

  @Test
  public void getPhoneListFromFireStation404Test() throws Exception {
    //GIVEN
    PhonesDTO phones = new PhonesDTO();
    when(fireStationServiceMocked.getPhonesFromFireStationNumber("1")).thenReturn(phones);

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/phoneAlert")
        .param("firestation", "2")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isNotFound());

  }

}
