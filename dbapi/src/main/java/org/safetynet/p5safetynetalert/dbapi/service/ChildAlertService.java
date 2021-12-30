package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.dto.AddressDTO;
import org.safetynet.p5safetynetalert.dbapi.dto.ChildDTO;
import org.safetynet.p5safetynetalert.dbapi.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.dbapi.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ChildAlertService {
  @Autowired
  AddressRepository addressRepository;
  @Autowired
  AgeCalculatorService ageCalculatorService;

  /**
   * This method returns a list of children (anyone aged 18 or under) living at a specific road.
   * The list includes each child's first and last name, age, and a list of others
   * household members. If there is no child, this method return an empty string for children.
   *
   * @param road it is the String of the road
   * @return a ChildFromAddressDTO (see description)
   * @throws Exception
   */
  public ChildFromAddressDTO getChildFromAddress(String road) throws Exception {
    List<ChildDTO> childrenToAdd = new ArrayList<>();
    List<PersonDTO> adultsToAdd = new ArrayList<>();

    Iterable<Address> addressesFromDb = addressRepository.findByRoad(road);

    for (Address addressFromDb : addressesFromDb) {
      Collection<Person> persons = addressFromDb.getPersons();
      for (Person person : persons) {
        String birthDate = person.getBirthDate();
        if (!ageCalculatorService.isStrictlyOverEighteen(birthDate)) {
          childrenToAdd.add(
              new ChildDTO(
                  person.getFirstName(),
                  person.getLastName(),
                  ageCalculatorService.getAge(person.getBirthDate())
              )
          );
        } else {
          adultsToAdd.add(
              new PersonDTO(
                  person.getFirstName(),
                  person.getLastName(),
                  person.getPhone(),
                  person.getBirthDate(),
                  new AddressDTO(
                      addressFromDb.getRoad(),
                      addressFromDb.getCity(),
                      addressFromDb.getZipCode()
                  )
              )
          );
        }
      }
    }

    ChildFromAddressDTO childFromAddressDTO = new ChildFromAddressDTO();
    childFromAddressDTO.setChildrenAtAddress(childrenToAdd);
    childFromAddressDTO.setOtherAdultsAtAddress(adultsToAdd);
    return childFromAddressDTO;
  }
}
