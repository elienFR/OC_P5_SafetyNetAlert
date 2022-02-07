package org.safetynet.p5safetynetalert.service.urls;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.model.dto.ChildDTO;
import org.safetynet.p5safetynetalert.model.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.model.dto.PersonDTO;
import org.safetynet.p5safetynetalert.model.entity.Address;
import org.safetynet.p5safetynetalert.service.IAddressService;
import org.safetynet.p5safetynetalert.service.IPersonService;
import org.safetynet.p5safetynetalert.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ChildAlertServiceTest {

  @Autowired
  private IChildAlertService iChildAlertService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private IPersonService iPersonServiceMocked;
  @MockBean
  private IAddressService iAddressServiceMocked;

  @Test
  public void getChildrenFromAddressTestWithNullAddress() {
    //given
    String givenRoad = "someRoad";
    when(iAddressServiceMocked.getByRoad(givenRoad)).thenReturn(null);
    //when
    ChildFromAddressDTO result = iChildAlertService.getChildrenFromAddress(givenRoad);
    //then
    assertThat(result).isNull();
    verify(iPersonServiceMocked, Mockito.times(0)).getChildrenDTO(any(Address.class));
    verify(iPersonServiceMocked, Mockito.times(0)).getAdultsDTO(any(Address.class));
  }

  @Test
  public void getChildrenFromAddressTest() {
    //given
    String givenRoad = "someRoad";

    Address foundAddress = new Address();
    foundAddress.setRoad("someRoad");
    when(iAddressServiceMocked.getByRoad(givenRoad)).thenReturn(foundAddress);

    Collection<ChildDTO> createdChildrenAtAddress = new ArrayList<>();
    when(iPersonServiceMocked.getChildrenDTO(foundAddress)).thenReturn(createdChildrenAtAddress);
    Collection<PersonDTO> createdAdultsAtAddress = new ArrayList<>();
    when(iPersonServiceMocked.getAdultsDTO(foundAddress)).thenReturn(createdAdultsAtAddress);

    ChildFromAddressDTO expected = new ChildFromAddressDTO();
    expected.setChildrenAtAddress(createdChildrenAtAddress);
    expected.setOtherAdultsAtAddress(createdAdultsAtAddress);

    //when
    ChildFromAddressDTO result = iChildAlertService.getChildrenFromAddress(givenRoad);
    //then
    assertThat(result).isEqualTo(expected);
    verify(iPersonServiceMocked, Mockito.times(1)).getChildrenDTO(foundAddress);
    verify(iPersonServiceMocked, Mockito.times(1)).getAdultsDTO(foundAddress);
  }

}
