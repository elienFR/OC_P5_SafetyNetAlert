package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.dto.PersonDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FireStationController {

  @GetMapping("/firestation")
  public List<PersonDTO> getPersonFromFireStationNumber(@RequestParam("stationNumber") int fireStationNumber){


    return null;
  }

}
