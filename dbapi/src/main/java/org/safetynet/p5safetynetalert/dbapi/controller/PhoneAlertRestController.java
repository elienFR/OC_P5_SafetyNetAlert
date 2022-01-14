package org.safetynet.p5safetynetalert.dbapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PhonesDTO;
import org.safetynet.p5safetynetalert.dbapi.service.urls.FireStationService;
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

  private static final Logger LOGGER = LogManager.getLogger(PhoneAlertRestController.class);
  @Autowired
  private FireStationService fireStationService;

  @GetMapping("")
  public PhonesDTO getPhoneListFromFireStation(
      @RequestParam("firestation") String number) {
    LOGGER.info("GET request made on url /phoneAlert?firestation="+number);
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
