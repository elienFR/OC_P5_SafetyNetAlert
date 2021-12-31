package org.safetynet.p5safetynetalert.dbapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.controller.EmailRestController;
import org.safetynet.p5safetynetalert.dbapi.dto.EmailListDTO;
import org.safetynet.p5safetynetalert.dbapi.service.AddressService;
import org.safetynet.p5safetynetalert.dbapi.service.EmailService;
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

@WebMvcTest(EmailRestController.class)
public class EmailRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JsonDataInjectorService jsonDataInjectorService;

  @MockBean
  private EmailService emailService;

  @BeforeEach
  public void initTests() {
    EmailListDTO emailListDTO = new EmailListDTO();
    when(emailService.getAllEmailFromCityInhabitants("testCity")).thenReturn(emailListDTO);
  }

  @Test
  public void getAllEmailFromCityInhabitantsTest() throws Exception {
    //GIVEN
    //in initTest()
    //city mocked is "testCity"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/communityEmail")
        .param("city","testCity")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void getAllEmailFromCityInhabitantsTest404() throws Exception {
    //GIVEN
    //in initTest()
    //city mocked is "testCity"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/communityEmail")
        .param("city","testCity1")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isNotFound());
  }

  @Test
  public void getAllEmailFromCityInhabitantsTest400() throws Exception {
    //GIVEN
    //in initTest()
    //city mocked is "testCity"

    //WHEN
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/communityEmail")
        .param("citi","testCity")
        .contentType(MediaType.APPLICATION_JSON);

    //THEN
    mockMvc.perform(builder).andExpect(status().isBadRequest());
  }

}
