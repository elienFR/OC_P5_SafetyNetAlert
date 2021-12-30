package org.safetynet.p5safetynetalert.dbapi;

import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.controller.ChildAlertRestController;
import org.safetynet.p5safetynetalert.dbapi.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.FireStation;
import org.safetynet.p5safetynetalert.dbapi.service.ChildAlertService;
import org.safetynet.p5safetynetalert.dbapi.service.FireStationService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.JsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChildAlertRestController.class)
public class ChildAlertRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JsonDataInjectorService jsonDataInjectorService;

  @MockBean
  private ChildAlertService childAlertService;

  @Test
  public void getChildrenFromAddress() throws Exception {
    ChildFromAddressDTO children = new ChildFromAddressDTO();

    when(childAlertService.getChildFromAddress("test")).thenReturn(children);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/childAlert")
        .param("address", "test")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void getChildrenFromAddress404() throws Exception {
    ChildFromAddressDTO children = new ChildFromAddressDTO();

    when(childAlertService.getChildFromAddress("test")).thenReturn(children);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/childAlert")
        .param("address", "404")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isOk());
  }
}
