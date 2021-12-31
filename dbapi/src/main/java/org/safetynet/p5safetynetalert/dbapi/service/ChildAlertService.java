package org.safetynet.p5safetynetalert.dbapi.service;

import org.safetynet.p5safetynetalert.dbapi.model.dto.AddressDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.ChildDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ChildAlertService {
  @Autowired
  AddressService addressService;
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

    Address address = addressService.findByRoad(road);


      Collection<Person> persons = address.getPersons();
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
                      address.getRoad(),
                      address.getCity(),
                      address.getZipCode()
                  )
              )
          );
        }
      }


    ChildFromAddressDTO childFromAddressDTO = new ChildFromAddressDTO();
    childFromAddressDTO.setChildrenAtAddress(childrenToAdd);
    childFromAddressDTO.setOtherAdultsAtAddress(adultsToAdd);
    return childFromAddressDTO;
  }
}
