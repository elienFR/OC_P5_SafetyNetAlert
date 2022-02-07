package org.safetynet.p5safetynetalert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.service.IPersonService;
import org.safetynet.p5safetynetalert.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordsRestController.class)
public class MedicalRecordsRestControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private IJsonDataInjectorService IJsonDataInjectorServiceMock;
  @MockBean
  private IPersonService iPersonService;

  @Test
  public void postNewMedicalRecordsTest() throws Exception {
    //GIVEN
    JsonMedicalRecord jsonMedicalRecord = new JsonMedicalRecord();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonMedicalRecord);
    when(iPersonService.createMedicalRecords(jsonMedicalRecord)).thenReturn(new JsonMedicalRecord());

    //WHEN

    //THEN
    mockMvc
      .perform(post("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isOk());
  }

  @Test
  public void postNewMedicalRecordsTestWithNullBody() throws Exception {
    //GIVEN
    JsonMedicalRecord jsonMedicalRecord = null;
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonMedicalRecord);

    //WHEN

    //THEN
    mockMvc
      .perform(post("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNoContent());
  }

  @Test
  public void postNewMedicalRecordsTestIsAlreadyReported() throws Exception {
    //GIVEN
    JsonMedicalRecord jsonMedicalRecord = new JsonMedicalRecord();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonMedicalRecord);
    when(iPersonService.createMedicalRecords(jsonMedicalRecord)).thenReturn(null);

    //WHEN

    //THEN
    mockMvc
      .perform(post("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isAlreadyReported());
  }

  @Test
  public void putMedicalRecordsTest() throws Exception {
    //GIVEN
    JsonMedicalRecord jsonMedicalRecord = new JsonMedicalRecord();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonMedicalRecord);
    when(iPersonService.updateMedicalRecords(jsonMedicalRecord)).thenReturn(new JsonMedicalRecord());

    //WHEN

    //THEN
    mockMvc
      .perform(put("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isOk());
  }
  @Test
  public void putMedicalRecordsTestWithNullBody() throws Exception {
    //GIVEN
    JsonMedicalRecord jsonMedicalRecord = null;
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonMedicalRecord);

    //WHEN

    //THEN
    mockMvc
      .perform(put("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNoContent());
  }


  @Test
  public void putMedicalRecordsTestIsNotFound() throws Exception {
    //GIVEN
    JsonMedicalRecord jsonMedicalRecord = new JsonMedicalRecord();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonMedicalRecord);
    when(iPersonService.updateMedicalRecords(jsonMedicalRecord)).thenReturn(null);

    //WHEN

    //THEN
    mockMvc
      .perform(put("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNotFound());
  }

  @Test
  public void deleteMedicalRecordsTest() throws Exception {
    //GIVEN
    JsonMedicalRecord jsonMedicalRecord = new JsonMedicalRecord();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonMedicalRecord);
    when(iPersonService.deleteMedicalRecords(jsonMedicalRecord)).thenReturn(new JsonMedicalRecord());

    //WHEN

    //THEN
    mockMvc
      .perform(delete("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isOk());
  }

  @Test
  public void deleteMedicalRecordsTestWithNullBody() throws Exception {
    //GIVEN
    JsonMedicalRecord jsonMedicalRecord = null;
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonMedicalRecord);
    when(iPersonService.deleteMedicalRecords(jsonMedicalRecord)).thenReturn(new JsonMedicalRecord());

    //WHEN

    //THEN
    mockMvc
      .perform(delete("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNoContent());
  }

  @Test
  public void deleteMedicalRecordsTestNotFound() throws Exception {
    //GIVEN
    JsonMedicalRecord jsonMedicalRecord = new JsonMedicalRecord();
    String jsonStringTested = new ObjectMapper().writeValueAsString(jsonMedicalRecord);
    when(iPersonService.deleteMedicalRecords(jsonMedicalRecord)).thenReturn(null);

    //WHEN

    //THEN
    mockMvc
      .perform(delete("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON).content(jsonStringTested))
      .andExpect(status().isNotFound());
  }

}
