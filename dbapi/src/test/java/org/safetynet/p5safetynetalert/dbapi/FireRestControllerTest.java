package org.safetynet.p5safetynetalert.dbapi;

import org.junit.jupiter.api.Test;
import org.safetynet.p5safetynetalert.dbapi.controller.FireRestController;
import org.safetynet.p5safetynetalert.dbapi.dto.FireDTO;
import org.safetynet.p5safetynetalert.dbapi.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.service.AddressService;
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

@WebMvcTest(controllers = FireRestController.class)
public class FireRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JsonDataInjectorService jsonDataInjectorServiceMock;

  @MockBean
  private AddressService addressService;

  @Test
  public void testGetPersonsFromAddressInFire() throws Exception {
    FireDTO fireDTO = new FireDTO();
    when(addressService.getPersonFromAddressInFire("test")).thenReturn(fireDTO);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/fire")
        .param("address", "test")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isOk());
  }

  @Test
  public void testGetPersonsFromAddressInFire404() throws Exception {
    FireDTO fireDTO = new FireDTO();
    when(addressService.getPersonFromAddressInFire("test")).thenReturn(fireDTO);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get("/fire")
        .param("address", "testt")
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andExpect(status().isNotFound());
  }

}
