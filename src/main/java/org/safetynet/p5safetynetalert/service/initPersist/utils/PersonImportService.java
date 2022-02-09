package org.safetynet.p5safetynetalert.service.initPersist.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.entity.Address;
import org.safetynet.p5safetynetalert.model.entity.Person;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.repository.AddressRepository;
import org.safetynet.p5safetynetalert.repository.AllergyRepository;
import org.safetynet.p5safetynetalert.repository.PersonRepository;
import org.safetynet.p5safetynetalert.service.initPersist.JsonDataInjectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class PersonImportService {

  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private AddressRepository addressRepository;

  private static final Logger LOGGER = LogManager.getLogger(JsonDataInjectorServiceImpl.class);

  /**
   * script that import persons from a jsondata object
   */
  public void importPersons(JsonData jsonData) {
    LOGGER.debug("importing persons...");

    Set<String> mySet = new TreeSet<>();
    for (JsonPerson jsonPerson : jsonData.getPersons().getPersons()) {
      Person personToAdd = new Person();
      //Setting FirstName, LastName, Phone, Email
      personToAdd.setFirstName(jsonPerson.getFirstName());
      personToAdd.setLastName(jsonPerson.getLastName());
      personToAdd.setPhone(jsonPerson.getPhone());
      personToAdd.setEmail(jsonPerson.getEmail());

      //Setting the Address
      String road = jsonPerson.getAddress();
      String city = jsonPerson.getCity();
      String zip = jsonPerson.getZip();
      Address addressToAdd = addressRepository.findByRoadAndCityAndZipCode(road, city, zip);
      personToAdd.setAddress(addressToAdd);

      //Setting BirthDate
      for (JsonMedicalRecord jsonMedicalRecord : jsonData.getMedicalRecords().getMedicalrecords()) {
        if (jsonMedicalRecord.getFirstName().equals(jsonPerson.getFirstName())
          && jsonMedicalRecord.getLastName().equals(jsonPerson.getLastName())) {
          personToAdd.setBirthDate(jsonMedicalRecord.getBirthdate());
          break;
        }
      }

      //Uniqueness of each people imported
      String uniqueKey = jsonPerson.getFirstName() + jsonPerson.getLastName() + jsonPerson.getAddress();
      if (!mySet.contains(uniqueKey)) {
        personRepository.save(personToAdd);
      }
      mySet.add(uniqueKey);
    }
  }
}
