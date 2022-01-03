package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.model.dto.FloodPersonsListDTO;
import org.safetynet.p5safetynetalert.dbapi.service.FloodService;
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

  @Autowired
  FloodService floodService;

  @GetMapping("/stations")
  public FloodPersonsListDTO getPersonsFloodDTOFromFireStations(
    @RequestParam("stations") Collection<String> stations) {
    FloodPersonsListDTO floodPersonsListDTO =
      floodService.getPersonsFloodDTOFromFireStation(stations);
    if(floodPersonsListDTO != null) {
      return floodPersonsListDTO;
    } else {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "No fire Station found"
      );
    }
  }


}
