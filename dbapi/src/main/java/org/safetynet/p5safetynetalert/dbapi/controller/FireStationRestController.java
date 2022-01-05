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
