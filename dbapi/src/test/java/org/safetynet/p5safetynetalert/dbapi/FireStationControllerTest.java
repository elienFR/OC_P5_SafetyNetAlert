package org.safetynet.p5safetynetalert.dbapi;

import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.service.FireStationService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.JsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  private JsonDataInjectorService jsonDataInjectorService;

  @MockBean
  private FireStationService fireStationService;

  @Test
  public void checkStatusOfPersonFromFireStationNumber() throws Exception {

    MockHttpServletRequestBuilder builder = get("/firestation")
        .param("stationNumber", "6")
        .contentType("text/html;charset=UTF-8");

    mockMvc.perform(builder).andExpect(status().isOk());

  }

  @Test
  public void getPersonFromFireStationNumberTest() throws Exception {
    mockMvc.perform(get("/firestation").param("stationNumber","1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("personsfromfirestation"))
        .andExpect(content().string(containsString("Peter")));
  }
}
