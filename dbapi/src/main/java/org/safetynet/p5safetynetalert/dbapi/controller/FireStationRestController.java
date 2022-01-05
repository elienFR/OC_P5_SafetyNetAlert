package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.dbapi.service.urls.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/firestation")
public class FireStationRestController {

  @Autowired
  FireStationService fireStationService;

  /**
   * This method responses to a get call to /firestation. It returns a list of people in serialized
   * json covered by the corresponding fire station.
   * So, if the station number = 1, it must return the inhabitants covered by the station number 1.
   * The list includes the following specific information: first name, last name, address,
   * telephone number.
   * It provides a count of adults, and a count of children (anyone aged 18 or over less)
   * in the service area.
   *
   * @param id It is the parameter of the station
   * @return 404 not found if the station does not exist. Or a list a explained in the method
   * description.
   */
  @GetMapping("")
  public PersonsFromFireStationDTO getPersonsFromFireStationId(@RequestParam("stationNumber") String id) {
    PersonsFromFireStationDTO persons = fireStationService.getPersonsAndCount(id);
    if (persons != null) {
      return persons;
    } else {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "entity not found"
      );
    }
  }

  @PostMapping("")
  public JsonFireStation postFireStationAddressMapping(@RequestBody JsonFireStation jsonFireStation) {
    if(jsonFireStation != null) {
      JsonFireStation postedJsonFireStation = fireStationService.saveAddressFireStationMapping(jsonFireStation);
      if (postedJsonFireStation != null) {
        return postedJsonFireStation;
      } else {
        throw new ResponseStatusException(
          HttpStatus.NO_CONTENT, "no address provided"
        );
      }
    } else {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "no content provided"
      );
    }
  }

  @PutMapping("")
  public JsonFireStation putFireStationAddressMapping(
    @RequestBody JsonFireStation jsonFireStation) {
    if (jsonFireStation != null) {
      JsonFireStation updatedJsonFireStation = fireStationService.updateAddressFireStationMapping(jsonFireStation);
      if (updatedJsonFireStation != null) {
        return updatedJsonFireStation;
      } else {
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "no such address or fire station found"
        );
      }
    } else {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "no content provided"
      );
    }
  }

  @DeleteMapping("")
  public JsonFireStation deleteFireStationAddressMapping(
    @RequestBody JsonFireStation jsonFireStation) {
    if (jsonFireStation != null) {
      JsonFireStation deletedJsonFireStation = fireStationService
        .eraseAddressFireStationMapping(jsonFireStation);
      if (deletedJsonFireStation != null) {
        return deletedJsonFireStation;
      } else {
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "no such address"
        );
      }
    } else {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "no content provided"
      );
    }
  }
}
