package org.safetynet.p5safetynetalert.dbapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.dbapi.service.IPersonService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonRestController.class)
public class PersonRestControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private IPersonService iPersonService;


  @Test
  public void postPersonTest() throws Exception{
    //GIVEN
    JsonPerson jsonPerson = new JsonPerson();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonPerson);
    when(iPersonService.createPerson(jsonPerson)).thenReturn(new JsonPerson());

    //WHEN

    //THEN
    mockMvc
      .perform(post("/person")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isOk());
  }

  @Test
  public void postPersonTestWithNullPostedJsonPerson() throws Exception{
    //GIVEN
    String jsonStringTested = "";

    //WHEN

    //THEN
    mockMvc
      .perform(post("/person")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNoContent());
  }

  @Test
  public void postPersonTestConflict() throws Exception{
    //GIVEN
    JsonPerson jsonPerson = new JsonPerson();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonPerson);
    when(iPersonService.createPerson(jsonPerson)).thenReturn(null);

    //WHEN

    //THEN
    mockMvc
      .perform(post("/person")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isConflict());
  }

  @Test
  public void putPersonTest() throws Exception{
    //GIVEN
    JsonPerson jsonPerson = new JsonPerson();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonPerson);
    when(iPersonService.updatePersonWithJsonPerson(jsonPerson)).thenReturn(new JsonPerson());

    //WHEN

    //THEN
    mockMvc
      .perform(put("/person")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isOk());
  }

  @Test
  public void putPersonTestWithNullPutJsonPerson() throws Exception{
    //GIVEN
    JsonPerson jsonPerson = null;
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonPerson);

    //WHEN

    //THEN
    mockMvc
      .perform(put("/person")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNoContent());
  }

  @Test
  public void putPersonTestNotFound() throws Exception{
    //GIVEN
    JsonPerson jsonPerson = new JsonPerson();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonPerson);
    when(iPersonService.updatePersonWithJsonPerson(jsonPerson)).thenReturn(null);

    //WHEN

    //THEN
    mockMvc
      .perform(put("/person")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNotFound());
  }

  @Test
  public void deletePersonTest() throws Exception{
    //GIVEN
    JsonPerson jsonPerson = new JsonPerson();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonPerson);
    when(iPersonService.delete(jsonPerson)).thenReturn(new JsonPerson());

    //WHEN

    //THEN
    mockMvc
      .perform(delete("/person")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isOk());
  }

  @Test
  public void deletePersonTestWithNullDeletedPerson() throws Exception{
    //GIVEN
    JsonPerson jsonPerson = null;
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonPerson);

    //WHEN

    //THEN
    mockMvc
      .perform(delete("/person")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNoContent());
  }

  @Test
  public void deletePersonTestNotFound() throws Exception{
    //GIVEN
    JsonPerson jsonPerson = new JsonPerson();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonPerson);
    when(iPersonService.delete(jsonPerson)).thenReturn(null);

    //WHEN

    //THEN
    mockMvc
      .perform(delete("/person")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNotFound());
  }



}
