package org.safetynet.p5safetynetalert.dbapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.controller.ChildAlertRestController;
import org.safetynet.p5safetynetalert.dbapi.model.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.dbapi.service.ChildAlertService;
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

@WebMvcTest(ChildAlertRestController.class)
public class ChildAlertRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JsonDataInjectorService jsonDataInjectorService;

  @MockBean
  private ChildAlertService childAlertService;

  @BeforeEach
  public void iniTest() throws Exception {
    ChildFromAddressDTO children = new ChildFromAddressDTO();
    when(childAlertService.getChildFromAddress("test")).thenReturn(children);
  }

  @Test
  public void getChildrenFromAddress() throws Exception {
    //GIVEN
    //in initTest()
    //road used is "test"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/childAlert")
        .param("address", "test")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void getChildrenFromAddress400() throws Exception {
    //GIVEN
    //in initTest()
    //road used is "test"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/childAlert")
        .param("addressBadRequest", "test")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isBadRequest());
  }
}
