package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.dto.PhonesDTO;
import org.safetynet.p5safetynetalert.dbapi.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertRestController {

  @Autowired
  private FireStationService fireStationService;

  @GetMapping("")
  public PhonesDTO getPhoneListFromFireStation(
      @RequestParam("firestation") String number) {
    PhonesDTO phones = fireStationService.getPhonesFromFireStationNumber(number);
    if (phones != null) {
      return phones;
    } else {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "fire station not found"
      );
    }
  }
}
