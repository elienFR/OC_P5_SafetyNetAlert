package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.dto.FloodPersonsListDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonForFloodDTO;
import org.safetynet.p5safetynetalert.dbapi.service.initPersist.IJsonDataInjectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FloodServiceTest {

  @Autowired
  private IFLoodService iFloodService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private IFireStationService iFireStationService;

  @Test
  public void getPersonsFloodDTOFromFireStationTest() {
    //given
    Collection<String> givenStations = new ArrayList<>(List.of("1","2"));

    PersonForFloodDTO foundPerson1FromStation1 = new PersonForFloodDTO();
    PersonForFloodDTO foundPerson2FromStation1 = new PersonForFloodDTO();
    Collection<PersonForFloodDTO> foundCollectionForStation1 = new ArrayList<>();
    foundCollectionForStation1.add(foundPerson1FromStation1);
    foundCollectionForStation1.add(foundPerson2FromStation1);
    when(iFireStationService.getPersonsForFlood("1")).thenReturn(foundCollectionForStation1);

    PersonForFloodDTO foundPerson1FromStation2 = new PersonForFloodDTO();
    Collection<PersonForFloodDTO> foundCollectionForStation2 = new ArrayList<>();
    foundCollectionForStation2.add(foundPerson1FromStation2);
    when(iFireStationService.getPersonsForFlood("2")).thenReturn(foundCollectionForStation2);

    Collection<PersonForFloodDTO> expectedCollectionPersonsForFlood = new ArrayList<>();
    expectedCollectionPersonsForFlood.addAll(foundCollectionForStation1);
    expectedCollectionPersonsForFlood.addAll(foundCollectionForStation2);

    FloodPersonsListDTO expected = new FloodPersonsListDTO();
    expected.setPersons(expectedCollectionPersonsForFlood);

    //when
    FloodPersonsListDTO result = iFloodService.getPersonsFloodDTOFromFireStation(givenStations);

    //then
    assertThat(result).isEqualTo(result);
    verify(iFireStationService, Mockito.times(1)).getPersonsForFlood("1");
    verify(iFireStationService, Mockito.times(1)).getPersonsForFlood("2");

  }

}
