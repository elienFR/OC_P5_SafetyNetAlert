package org.safetynet.p5safetynetalert.dbapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsInfoDTO;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.safetynet.p5safetynetalert.dbapi.service.urls.IPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonInfoRestController.class)
public class PersonInfoRestControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private IPersonInfoService iPersonInfoServiceMocked;

  @BeforeEach
  public void initTest() {
    PersonsInfoDTO personsInfoDTO = new PersonsInfoDTO();
    when(iPersonInfoServiceMocked.getPersonInfoFromFirstAndOrLastName("Allison","Boyd")).thenReturn(personsInfoDTO);
  }

  @Test
  public void getPersonInfoFromFirstAndLastNameTest() throws Exception {
    //GIVEN
    // in before each

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .get("/personInfo")
      .param("firstName", "Allison")
      .param("lastName", "Boyd")
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect((status().isOk()));
  }

  @Test
  public void getPersonInfoFromFirstAndLastNameTestNotFound() throws Exception {
    //GIVEN

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
      .get("/personInfo")
      .param("firstName", "not")
      .param("lastName", "found")
      .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect((status().isNotFound()));
  }

}
