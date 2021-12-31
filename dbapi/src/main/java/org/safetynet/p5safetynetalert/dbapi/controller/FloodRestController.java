package org.safetynet.p5safetynetalert.dbapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flood")
public class FloodRestController {

  @GetMapping("/station")
  public FloodStationDTO getListOfAddressesPersonsAnMedicalRecordsByFireStationList(
      @RequestParam("stations") Iterable<String> stations) {

  }

}
