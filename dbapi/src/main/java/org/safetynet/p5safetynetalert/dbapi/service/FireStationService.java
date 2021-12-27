package org.safetynet.p5safetynetalert.dbapi.service;

import lombok.Data;
import org.safetynet.p5safetynetalert.dbapi.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Address;
import org.safetynet.p5safetynetalert.dbapi.model.FireStation;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.repository.AddressRepository;
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

  public Iterable<Person> getPersonFromFireStationId(String id){
    List<Person> myList = new ArrayList<>();
    FireStation fireStation = fireStationRepository.findByNumber(id);
    Collection<Address> addresses = fireStation.getAddresses();
    for(Address address : addresses){
      for(Person person : address.getPersons()){
          //TODO : problem to recover the address without having a recursive infinite loop that leads to stackoverflow
        myList.add(person);
      }
    }
    return myList;
  }

}
