package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.dto.FireDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonForFireDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;
import org.safetynet.p5safetynetalert.dbapi.model.entity.FireStation;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.service.IAddressService;
import org.safetynet.p5safetynetalert.dbapi.service.IPersonService;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FireServiceTest {

  @Autowired
  private IFireService iFireService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private IPersonService iPersonService;
  @MockBean
  private IAddressService iAddressService;

  @Test
  public void getFireDTOFromAddressInFire() {
    //given
    String givenRoad = "someRoad";

    Address foundAddress = new Address(null,null,null,new FireStation("4"));
    when(iAddressService.getByRoad(givenRoad)).thenReturn(foundAddress);

    Collection<Person> foundPersons = new ArrayList<>();
    when(iPersonService.getPersonsFromAddress(foundAddress)).thenReturn(foundPersons);

    Collection<PersonForFireDTO> convertedPersonsForFireDTO = new ArrayList<>();
    when(iPersonService.convertPersonsInPersonForFireDTO(foundPersons)).thenReturn(convertedPersonsForFireDTO);

    FireDTO expected = new FireDTO();
    expected.setFireStationNumber(foundAddress.getFireStation().getNumber());
    expected.setPersonsList(convertedPersonsForFireDTO);

    //when
    FireDTO result = iFireService.getFireDTOFromAddressInFire(givenRoad);

    //then
    assertThat(result).isEqualTo(expected);
    verify(iAddressService, Mockito.times(1)).getByRoad(givenRoad);
    verify(iPersonService,Mockito.times(1)).getPersonsFromAddress(foundAddress);
    verify(iPersonService,Mockito.times(1)).convertPersonsInPersonForFireDTO(foundPersons);
  }

}
