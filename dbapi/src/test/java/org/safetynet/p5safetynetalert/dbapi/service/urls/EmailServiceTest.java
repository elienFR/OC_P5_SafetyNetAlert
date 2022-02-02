package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.safetynet.p5safetynetalert.dbapi.model.dto.EmailListDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Address;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.service.IAddressService;
import org.safetynet.p5safetynetalert.dbapi.service.IPersonService;
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
public class EmailServiceTest {

  @Autowired
  private IEmailService iEmailService;
  @MockBean
  private IJsonDataInjectorService iJsonDataInjectorService;
  @MockBean
  private IAddressService iAddressService;
  @MockBean
  private IPersonService iPersonService;

  @Test
  public void getAllEmailFromCityInhabitantsTestNullAddress() {
    //given
    String givenCity = "someCity";
    when(iAddressService.getAllByCity(givenCity)).thenReturn(null);
    //when
    EmailListDTO result = iEmailService.getAllEmailFromCityInhabitants(givenCity);
    //then
    assertThat(result).isNull();
  }

  @Test
  public void getAllEmailFromCityInhabitantsTestBlankAddress() {
    //given
    String givenCity = "someCity";
    when(iAddressService.getAllByCity(givenCity)).thenReturn(new ArrayList<>());
    //when
    EmailListDTO result = iEmailService.getAllEmailFromCityInhabitants(givenCity);
    //then
    assertThat(result).isNull();
  }

  @Test
  public void getAllEmailFromCityInhabitantsTest() {
    //given
    String givenCity = "someCity";

    Address address1 = new Address();
    Address address2 = new Address();
    Collection<Address> foundAddresses = new ArrayList<>();
    foundAddresses.add(address1);
    foundAddresses.add(address2);
    when(iAddressService.getAllByCity(givenCity)).thenReturn(foundAddresses);

    Person person1fromAddress1 = new Person();
    Person person2fromAddress1 = new Person();
    Person person1fromAddress2 = new Person();
    Collection<Person> foundPersonsFromAddresses = new ArrayList<>();
    foundPersonsFromAddresses.add(person1fromAddress1);
    foundPersonsFromAddresses.add(person2fromAddress1);
    foundPersonsFromAddresses.add(person1fromAddress2);
    when(iPersonService.getPersonsFromAddresses(foundAddresses)).thenReturn(foundPersonsFromAddresses);

    Collection<String> expectedMails = new ArrayList<>(
      List.of("email1","email2","email3")
    );
    when(iPersonService.getEmails(foundPersonsFromAddresses)).thenReturn(expectedMails);

    EmailListDTO expected = new EmailListDTO();
    expected.setCityName(givenCity);
    expected.setEmailList(expectedMails);

    //when
    EmailListDTO result = iEmailService.getAllEmailFromCityInhabitants(givenCity);
    //then
    verify(iAddressService,Mockito.times(1)).getAllByCity(givenCity);
    verify(iPersonService,Mockito.times(1)).getPersonsFromAddresses(foundAddresses);
    verify(iPersonService,Mockito.times(1)).getEmails(foundPersonsFromAddresses);
    assertThat(result).isEqualTo(expected);
  }


}
