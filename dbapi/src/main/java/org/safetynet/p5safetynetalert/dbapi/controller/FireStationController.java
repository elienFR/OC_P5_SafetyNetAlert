package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.dto.PersonDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FireStationController {

  @Autowired
  FireStationService fireStationService;

  @GetMapping("/firestation")
  public Iterable<PersonDTO> getPersonFromFireStationNumber(@RequestParam("stationNumber") String fireStationNumber){
    return fireStationService.getPersonFromFireStationId(fireStationNumber);
  }
}
