package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonInfoDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsInfoDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.service.AgeService;
import org.safetynet.p5safetynetalert.dbapi.service.IMedicalRecordsService;
import org.safetynet.p5safetynetalert.dbapi.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class PersonInfoService implements IPersonInfoService {

  private static final Logger LOGGER = LogManager.getLogger(PersonInfoService.class);
  @Autowired
  private IPersonService iPersonService;
  @Autowired
  private AgeService ageService;
  @Autowired
  private IMedicalRecordsService iMedicalRecordsService;


  /**
   * This method returns the name, address, age, email address and medical history (drugs,
   * dosage, allergies) of each inhabitant. If more than one person has the same last name, they
   * all appear.
   *
   * @param firstName it is the first name of the person you desire the info
   * @param lastName  not null - not blank - it is the last name of the person you desire the info
   * @return an object PersonsInfoDTO (see description)
   */
  @Override
  public PersonsInfoDTO getPersonInfoFromFirstAndOrLastName(String firstName, String lastName) {
    LOGGER.debug("A person Info DTO is being created...");
    PersonsInfoDTO personsInfoDTO = new PersonsInfoDTO();
    Person personToConvert = iPersonService.getByFirstNameAndLastName(firstName, lastName);
    if (personToConvert != null) {
      //create personInfoDTO
      PersonInfoDTO personInfoDTOToAdd = convertPersonIntoPersonInfoDTO(personToConvert);
      personsInfoDTO.setPersonsInfo(personInfoDTOToAdd);

      //create its family relatives
      Collection<PersonInfoDTO> familyRelativesInPersonDTO = new ArrayList<>();
      Iterable<Person> familyRelativesInPerson = iPersonService.getAllByName(lastName);
      for (Person person : familyRelativesInPerson) {
        if (!person.getFirstName().equals(personToConvert.getFirstName())) {
          familyRelativesInPersonDTO.add(convertPersonIntoPersonInfoDTO(person));
        }
      }

      personsInfoDTO.setPersonsFromTheSameFamily(familyRelativesInPersonDTO);

      return personsInfoDTO;

    } else {
      LOGGER.warn("Person to convert is null.");
      return null;
    }
  }

  /**
   * This method converts a person object into a PersonInfoDTO Object
   *
   * @param person is the person to be converted
   * @return a PersonInfoDTO Object
   */
  private PersonInfoDTO convertPersonIntoPersonInfoDTO(Person person) {
    LOGGER.debug("Converting a person into a PersonInfoDTO");
    PersonInfoDTO personInfoDTO = new PersonInfoDTO();

    personInfoDTO.setFirstName(person.getFirstName());
    personInfoDTO.setLastName(person.getLastName());
    personInfoDTO.setAge(ageService.getAge(person.getBirthDate()));
    personInfoDTO.setMail(person.getEmail());

    personInfoDTO.setMedicalRecords(
      iMedicalRecordsService.getMedicalRecordsDTOFromPerson(person)
    );

    return personInfoDTO;
  }
}
