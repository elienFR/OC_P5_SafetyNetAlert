package org.safetynet.p5safetynetalert.dbapi.service;

import com.sun.istack.NotNull;
import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.model.PersonsMedication;
import org.safetynet.p5safetynetalert.dbapi.model.dto.MedicalRecordsDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonInfoDTO;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsInfoDTO;
import org.safetynet.p5safetynetalert.dbapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private AgeCalculatorService ageCalculatorService;
  @Autowired
  private PersonsMedicationService personsMedicationService;
  @Autowired
  private PersonsAllergyService personsAllergyService;

  public Optional<Person> getPerson(final Integer id) {
    return personRepository.findById(id);
  }

  public Iterable<Person> getPersons() {
    return personRepository.findAll();
  }

  public Iterable<Person> getAllPersonsByFirstNameAndLastName(String firstName, String lastName) {
    return personRepository.findAllByFirstNameAndLastName(firstName, lastName);
  }

  public Iterable<Person> getAllPersonsByLastName(String lastName) {
    return personRepository.findAllByLastName(lastName);
  }

  public MedicalRecordsDTO getMedicalRecords(Person person) {
    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();

    Iterable<PersonsMedication> personsMedications = person.getPersonsMedications();

    List<String> medications = new ArrayList<>(
        personsMedicationService.getMedicationsFromPersonsMedications(
            person.getPersonsMedications()
        )
    );

    List<String> allergies = new ArrayList<>(
        personsAllergyService.getAllergiesFromPersonsMedications(
            person.getPersonsAllergies()
        )
    );

    medicalRecordsDTO.setMedications(medications);
    medicalRecordsDTO.setAllergies(allergies);
    return medicalRecordsDTO;
  }

  public Person savePerson(Person person) {
    Person savedPerson = personRepository.save(person);
    return savedPerson;
  }

  public List<PersonDTO> getPersonFromFireStationNumber(int fireStationNumber) {


    return null;
  }

  /**
   * This url returns the name, address, age, email address and medical history (drugs,
   * dosage, allergies) of each inhabitant. If more than one person has the same last name, they
   * all appear.
   *
   * @param firstName it is the first name of the person you desire the info
   * @param lastName  not null - not blank - it is the last name of the person you desire the info
   * @return an object PersonsInfoDTO (see description)
   */
  public PersonsInfoDTO getPersonInfoFromFirstAndOrLastName(
      String firstName,
      @NotNull @NotBlank String lastName
  ) {
    if ((firstName != null && lastName != null) || (!firstName.equals("") && !lastName.equals(""))) {
      Iterable<Person> persons;
      if(firstName == null || firstName.equals("")){
        persons = getAllPersonsByLastName(lastName);
      }
      else {
        persons = getAllPersonsByFirstNameAndLastName(firstName, lastName);
      }

      List<PersonInfoDTO> personsInfoDTOToAdd = new ArrayList<>();
      for (Person person : persons) {
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();

        personInfoDTO.setFirstName(person.getFirstName());
        personInfoDTO.setLastName(person.getLastName());
        personInfoDTO.setAge(
            ageCalculatorService.getAge(person.getBirthDate())
        );
        personInfoDTO.setMail(person.getEmail());
        personInfoDTO.setMedicalRecords(
            getMedicalRecords(person)
        );

        personsInfoDTOToAdd.add(personInfoDTO);
      }

      PersonsInfoDTO personsInfoDTO = new PersonsInfoDTO();
      personsInfoDTO.setPersonsInfoDTO(personsInfoDTOToAdd);

      //checks if at least one person exists
      if (personsInfoDTOToAdd.size() > 0) {
        return personsInfoDTO;
      } else {
        return null;
      }
    } else {
      return new PersonsInfoDTO();
    }
  }
}
