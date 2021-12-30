package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.dto.AddressDTO;
import org.safetynet.p5safetynetalert.dbapi.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.FireStation;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class FireStationService {

  @Autowired
  AgeCalculatorService ageCalculatorService;
  @Autowired
  private FireStationRepository fireStationRepository;

  public Optional<FireStation> getFireStation(final Integer id) {
    return fireStationRepository.findById(id);
  }

  public Iterable<FireStation> getFireStations() {
    return fireStationRepository.findAll();
  }

  public void deleteFireStation(final Integer id) {
    fireStationRepository.deleteById(id);
  }

  public FireStation saveFireStation(FireStation fireStation) {
    FireStation savedFireStation = fireStationRepository.save(fireStation);
    return savedFireStation;
  }


  /**
   * This method should return a list of people covered by the corresponding fire station.
   * So, if the station number = 1, it must return the inhabitants covered by the station number 1.
   * The list includes the following specific information: first name, last name, address,
   * telephone number.
   * What's more, it must provide a count of the number of adults,
   * and the number of children (anyone aged 18 or over less) in the service area.
   *
   * @param number is the number of the firestation
   * @return see description
   */
  public PersonsFromFireStationDTO getPersonsFromFireStationId(String number) {
    PersonsFromFireStationDTO listOfPersons = new PersonsFromFireStationDTO();
    List<PersonDTO> personsList = new ArrayList<>();
    Integer adultCount = 0;
    Integer childrenCount = 0;

    FireStation fireStation = fireStationRepository.findByNumber(number);
    if(fireStation!=null) {
      Collection<Address> addresses = fireStation.getAddresses();

      for (Address address : addresses) {
        Collection<Person> persons = address.getPersons();

        for (Person person : persons) {
          try {
            if (ageCalculatorService.isStrictlyOverEighteen(person.getBirthDate())) {
              adultCount += 1;
            } else {
              childrenCount += 1;
            }
            personsList.add(new PersonDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getPhone(),
                person.getBirthDate(),
                new AddressDTO(
                    address.getRoad(),
                    address.getCity(),
                    address.getZipCode()
                )
            ));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }

      listOfPersons.setPersonsList(personsList);
      listOfPersons.setAdultCount(adultCount);
      listOfPersons.setChildrenCount(childrenCount);

      return listOfPersons;

    } else {
      return null;
    }
  }

}
