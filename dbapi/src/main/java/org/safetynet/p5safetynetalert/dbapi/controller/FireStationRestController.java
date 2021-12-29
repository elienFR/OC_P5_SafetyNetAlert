package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.dto.PersonFromFirestationDTO;
import org.safetynet.p5safetynetalert.dbapi.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationRestController {

  @Autowired
  FireStationService fireStationService;

  @GetMapping("/testPersonFireStation")
  public Iterable<PersonFromFirestationDTO> get(@RequestParam("number") String id) {
    return fireStationService.getPersonDTOFromFireStationId(id);
  }
}
