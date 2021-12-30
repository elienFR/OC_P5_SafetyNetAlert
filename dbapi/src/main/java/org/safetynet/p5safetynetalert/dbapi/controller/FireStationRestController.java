package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.dbapi.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("firestation")
public class FireStationRestController {

  @Autowired
  FireStationService fireStationService;

  @GetMapping("")
  public PersonsFromFireStationDTO getPersonsFromFireStationId(@RequestParam("stationNumber") String id) {
    PersonsFromFireStationDTO persons = fireStationService.getPersonsFromFireStationId(id);
    if (persons != null) {
      return persons;
    } else {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "entity not found"
      );
    }
  }
}
