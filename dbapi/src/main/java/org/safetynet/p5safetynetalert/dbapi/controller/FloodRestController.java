package org.safetynet.p5safetynetalert.dbapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.dto.FloodPersonsListDTO;
import org.safetynet.p5safetynetalert.dbapi.service.urls.IFLoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/flood")
public class FloodRestController {

  private static final Logger LOGGER = LogManager.getLogger(FloodRestController.class);
  @Autowired
  private IFLoodService iFloodService;

  /**
   * This method is called with a GET request. It returns a list of all the homes served by
   * the fire station. This list includes the people by address. It also includes the name,
   * telephone number and age of the inhabitants, and includes their medical history
   * (medications, dosage and allergies) next to each name.
   *
   * @param stations is the collection of fire station.
   * @return a serialized object. See description.
   */
  @GetMapping("/stations")
  public FloodPersonsListDTO getPersonsFloodDTOFromFireStations(
    @RequestParam("stations") Collection<String> stations) {
    LOGGER.info("GET request on /flood/stations?stations={listOfStations}");
    FloodPersonsListDTO floodPersonsListDTO =
      iFloodService.getPersonsFloodDTOFromFireStation(stations);
    if(floodPersonsListDTO != null) {
      return floodPersonsListDTO;
    } else {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "No fire Station found"
      );
    }
  }


}
