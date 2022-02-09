package org.safetynet.p5safetynetalert.service.initPersist.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.entity.Address;
import org.safetynet.p5safetynetalert.model.initPersist.JsonData;
import org.safetynet.p5safetynetalert.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.repository.AddressRepository;
import org.safetynet.p5safetynetalert.repository.FireStationRepository;
import org.safetynet.p5safetynetalert.service.initPersist.JsonDataInjectorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class AddressImportService {

  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private FireStationRepository fireStationRepository;

  private static final Logger LOGGER = LogManager.getLogger(JsonDataInjectorServiceImpl.class);

  /**
   * script that import addresses from a jsondata object
   */
  public void importAddresses(JsonData jsonData) {
    LOGGER.debug("importing addresses...");

    //Comparison set to verify uniqueness of address
    Set<String> comparisonSet = new TreeSet<>();

    //create three independent set of road city and zip from persons
    for (JsonPerson jsonPerson : jsonData.getPersons().getPersons()) {
      String road = jsonPerson.getAddress();
      String city = jsonPerson.getCity();
      String zip = jsonPerson.getZip();
      String addressKey = road + city + zip;
      Address addressToAdd = new Address();

      if (!comparisonSet.contains(addressKey)) {
        for (JsonFireStation fireStation : jsonData.getFireStations().getFirestations()) {
          if (fireStation.getAddress().equals(jsonPerson.getAddress())) {
            addressToAdd.setRoad(road);
            addressToAdd.setCity(city);
            addressToAdd.setZipCode(zip);
            addressToAdd.setFireStation(fireStationRepository.findByNumber(fireStation.getStation()));
            addressRepository.save(addressToAdd);
          }
        }
      }
      //it is a set so even if the key already exists keys will be unique, never mind in adding it.
      comparisonSet.add(addressKey);
    }
  }

}
