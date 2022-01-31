package org.safetynet.p5safetynetalert.dbapi.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.dto.AddressDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AddressServiceTest {

  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorServiceMocked;
  @MockBean
  private AddressRepository addressRepositoryMocked;
  @Autowired
  private IAddressService iAddressService;

  @Test
  public void getByRoadAndCityAndZipCodeTest() {
    //given
    Address givenAddress = new Address();
    Address expectedAddress = new Address();
    when(addressRepositoryMocked.findByRoadAndCityAndZipCode(
      givenAddress.getRoad(),
      givenAddress.getCity(),
      givenAddress.getZipCode()
    )).thenReturn(expectedAddress);
    //when
    Address result = iAddressService.getByRoadAndCityAndZipCode(givenAddress);
    //then
    assertThat(result).isEqualTo(expectedAddress);
    verify(addressRepositoryMocked, Mockito.times(1))
      .findByRoadAndCityAndZipCode(
        givenAddress.getRoad(),
        givenAddress.getCity(),
        givenAddress.getZipCode());
  }

  @Test
  public void getByRoadTest() {
    //given
    String givenRoad = "";
    Address expected = new Address();
    when(addressRepositoryMocked.findByRoad(givenRoad)).thenReturn(expected);
    //when
    Address result = iAddressService.getByRoad(givenRoad);
    //then
    assertThat(result).isEqualTo(expected);
    verify(addressRepositoryMocked, Mockito.times(1)).findByRoad(givenRoad);
  }

  @Test
  public void getByRoadTestNull() {
    //given
    String givenRoad = "";
    Address expected = null;
    when(addressRepositoryMocked.findByRoad(givenRoad)).thenReturn(null);
    //when
    Address result = iAddressService.getByRoad(givenRoad);
    //then
    assertThat(result).isEqualTo(expected);
    verify(addressRepositoryMocked, Mockito.times(1)).findByRoad(givenRoad);
  }

  @Test
  public void existsByRoadTest() {
    //given
    String givenRoad = "";
    boolean expected = true;
    when(addressRepositoryMocked.existsByRoad(givenRoad)).thenReturn(true);
    //when
    boolean result = iAddressService.existsByRoad(givenRoad);
    //then
    assertThat(result).isTrue();
    verify(addressRepositoryMocked, Mockito.times(1)).existsByRoad(givenRoad);
  }

  @Test
  public void existsByRoadAndCityAndZipCodeTest() {
    //given
    Address givenAddress = new Address();
    boolean expectedBoolean = true;
    when(addressRepositoryMocked.existsByRoadAndCityAndZipCode(
      givenAddress.getRoad(),
      givenAddress.getCity(),
      givenAddress.getZipCode()
    )).thenReturn(true);
    //when
    boolean result = iAddressService.existsByRoadAndCityAndZipCode(givenAddress);
    //then
    assertThat(result).isEqualTo(expectedBoolean);
    verify(addressRepositoryMocked, Mockito.times(1))
      .existsByRoadAndCityAndZipCode(
        givenAddress.getRoad(),
        givenAddress.getCity(),
        givenAddress.getZipCode());
  }

  @Test
  public void saveTest() {
    //given
    Address givenAddress = new Address();
    Address expected = new Address();
    when(addressRepositoryMocked.save(givenAddress)).thenReturn(expected);
    //when
    Address result = iAddressService.save(givenAddress);
    //then
    assertThat(result).isEqualTo(expected);
    verify(addressRepositoryMocked, Mockito.times(1)).save(givenAddress);
  }

  @Test
  public void saveTestNull() {
    //given
    Address givenAddress = null;
    //when
    Address result = iAddressService.save(givenAddress);
    //then
    assertThat(result).isNull();
  }

  @Test
  public void getAllByCityTest() {
    //given
    String givenCity = "tested";
    Collection<Address> expected = new ArrayList<>();
    when(addressRepositoryMocked.findAllByCity(givenCity)).thenReturn(expected);
    //when
    Collection<Address> result = iAddressService.getAllByCity(givenCity);
    //then
    assertThat(result).isEqualTo(expected);
    verify(addressRepositoryMocked,Mockito.times(1)).findAllByCity(givenCity);
  }

  @Test
  public void getAllByCityTestNull() {
    //given
    String givenCity = "";
    Collection<Address> expected = null;
    when(addressRepositoryMocked.findAllByCity(givenCity)).thenReturn(expected);
    //when
    Collection<Address> result = iAddressService.getAllByCity(givenCity);
    //then
    assertThat(result).isEqualTo(expected);
    verify(addressRepositoryMocked,Mockito.times(0)).findAllByCity(givenCity);
  }

  @Test
  public void convertAddressToAddressDTO(){
    //given
    Address givenAddress = new Address("rue poitier","nantes","44000",null);
    AddressDTO expected = new AddressDTO("rue poitier","nantes","44000");
    //when
    AddressDTO result = iAddressService.convertAddressToAddressDTO(givenAddress);
    //then
    assertThat(result.getRoad()).isEqualTo(givenAddress.getRoad());
    assertThat(result.getCity()).isEqualTo(givenAddress.getCity());
    assertThat(result.getZip()).isEqualTo(givenAddress.getZipCode());
  }

}
