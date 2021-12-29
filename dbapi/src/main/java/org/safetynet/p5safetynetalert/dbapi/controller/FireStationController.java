package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.dto.PersonFromFirestationDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FireStationController {

  @Autowired
  FireStationService fireStationService;

  @GetMapping("/firestation")
  public String getPersonFromFireStationNumber(
      @RequestParam("stationNumber") String fireStationNumber, Model model) {

    Iterable<PersonFromFirestationDTO> personsList =
        fireStationService.getPersonFromFireStationId(fireStationNumber);

    model.addAttribute("persons", personsList);

    return "personsfromfirestation";
  }

}
