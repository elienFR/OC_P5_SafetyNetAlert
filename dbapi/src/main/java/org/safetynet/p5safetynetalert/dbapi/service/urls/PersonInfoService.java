package org.safetynet.p5safetynetalert.dbapi.service.urls;

import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonInfoDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsInfoDTO;
import org.safetynet.p5safetynetalert.dbapi.model.entity.Person;
import org.safetynet.p5safetynetalert.dbapi.service.AgeService;
import org.safetynet.p5safetynetalert.dbapi.service.MedicalRecordsService;
import org.safetynet.p5safetynetalert.dbapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PersonInfoService {

  @Autowired
  private PersonService personService;
  @Autowired
  private AgeService ageService;
  @Autowired
  private MedicalRecordsService medicalRecordsService;


  /**
   * This method returns the name, address, age, email address and medical history (drugs,
   * dosage, allergies) of each inhabitant. If more than one person has the same last name, they
   * all appear.
   *
   * @param firstName it is the first name of the person you desire the info
   * @param lastName  not null - not blank - it is the last name of the person you desire the info
   * @return an object PersonsInfoDTO (see description)
   */
  public PersonsInfoDTO getPersonInfoFromFirstAndOrLastName(String firstName, String lastName) {
    PersonsInfoDTO personsInfoDTO = new PersonsInfoDTO();
    Person personToConvert = personService.getByFirstNameAndLastName(firstName, lastName);
    if (personToConvert != null) {
      //create personInfoDTO
      PersonInfoDTO personInfoDTOToAdd = convertPersonIntoPersonInfoDTO(personToConvert);
      personsInfoDTO.setPersonsInfo(personInfoDTOToAdd);

      //create its family relatives
      Collection<PersonInfoDTO> familyRelativesInPersonDTO = new ArrayList<>();
      Iterable<Person> familyRelativesInPerson = personService.getAllByName(lastName);
      for(Person person : familyRelativesInPerson){
        if(!person.getFirstName().equals(personToConvert.getFirstName())){
          familyRelativesInPersonDTO.add(convertPersonIntoPersonInfoDTO(person));
        }
      }

      personsInfoDTO.setPersonsFromTheSameFamily(familyRelativesInPersonDTO);

      return personsInfoDTO;

    } else {
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
    PersonInfoDTO personInfoDTO = new PersonInfoDTO();

    personInfoDTO.setFirstName(person.getFirstName());
    personInfoDTO.setLastName(person.getLastName());
    personInfoDTO.setAge(ageService.getAge(person.getBirthDate()));
    personInfoDTO.setMail(person.getEmail());

    personInfoDTO.setMedicalRecords(
      medicalRecordsService.getMedicalRecordsDTOFromPerson(person)
    );

    return personInfoDTO;
  }
}
