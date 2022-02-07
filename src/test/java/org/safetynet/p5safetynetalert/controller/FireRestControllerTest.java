package org.safetynet.p5safetynetalert.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.model.dto.FireDTO;
import org.safetynet.p5safetynetalert.service.urls.FireService;
import org.safetynet.p5safetynetalert.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireRestController.class)
public class FireRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IJsonDataInjectorService IJsonDataInjectorServiceMock;

  @MockBean
  private FireService fireService;

  @BeforeEach
  public void initTest() {
    FireDTO fireDTO = new FireDTO();
    when(fireService.getFireDTOFromAddressInFire("test")).thenReturn(fireDTO);
  }



  @Test
  public void testGetPersonsFromAddressInFire() throws Exception {
    //GIVEN
    // is in initTest()
    //road used is "test"

    //WHEN
    MockHttpServletRequestBuilder builder = get("/fire")
      .param("address", "test")
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void testGetPersonsFromAddressInFire404() throws Exception {
    //GIVEN
    // is in initTest()
    //road used is "test"

    //WHEN
    MockHttpServletRequestBuilder builder = get("/fire")
      .param("address", "testt")
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isNotFound());
  }

  @Test
  public void testGetPersonsFromAddressInFire400() throws Exception {
    //GIVEN
    // is in initTest()
    //road used is "test"

    //WHEN
    MockHttpServletRequestBuilder builder = get("/fire")
      .param("address_with_bad_request", "test")
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isBadRequest());
  }

}
