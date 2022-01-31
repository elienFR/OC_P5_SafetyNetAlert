package org.safetynet.p5safetynetalert.dbapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsFromFireStationDTO;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonFireStation;
import org.safetynet.p5safetynetalert.dbapi.service.urls.IFireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/firestation")
public class FireStationRestController {

  private static final Logger LOGGER = LogManager.getLogger(FireRestController.class);
  @Autowired
  private IFireStationService iFireStationService;

  /**
   * This method responses to a get call to /firestation. It returns a list of people in serialized
   * json covered by the corresponding fire station.
   * So, if the station number = 1, it must return the inhabitants covered by the station number 1.
   * The list includes the following specific information: first name, last name, address,
   * telephone number.
   * It provides a count of adults, and a count of children (anyone aged 18 or over less)
   * in the service area.
   *
   * @param id It is the parameter of the station
   * @return 404 not found if the station does not exist. Or a list a explained in the method
   * description.
   */
  @GetMapping("")
  public PersonsFromFireStationDTO getPersonsFromFireStationId(
    @RequestParam("stationNumber") String id) {
    LOGGER.info("GET Request on /firestation?stationNumber="+id);
    PersonsFromFireStationDTO persons = iFireStationService.getPersonsAndCount(id);
    if (persons != null) {
      LOGGER.debug("Serialization completed.");
      return persons;
    } else {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "entity not found"
      );
    }
  }

  /**
   * This method response to a Post call. It add a new fire station in the database
   *
   * @param jsonFireStation a json body that refers to a fire station.
   * @return 200 OK if properly executed.
   */
  @PostMapping("")
  public JsonFireStation postFireStationAddressMapping(
    @RequestBody(required = false) JsonFireStation jsonFireStation) {
    LOGGER.info("POST request on /firestation");
    if(jsonFireStation != null) {
      JsonFireStation postedJsonFireStation = iFireStationService
        .saveAddressFireStationMapping(jsonFireStation);
      if (postedJsonFireStation != null) {
        return postedJsonFireStation;
      } else {
        throw new ResponseStatusException(
          HttpStatus.NO_CONTENT, "No address provided."
        );
      }
    } else {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "No content provided."
      );
    }
  }

  /**
   * This method response to a Post call. It updates a fire station in the database.
   *
   * @param jsonFireStation a json body that refers to a fire station.
   * @return 200 OK if properly executed.
   */
  @PutMapping("")
  public JsonFireStation putFireStationAddressMapping(
    @RequestBody(required = false) JsonFireStation jsonFireStation) {
    LOGGER.info("PUT request on /firestation");
    if (jsonFireStation != null) {
      JsonFireStation updatedJsonFireStation = iFireStationService
        .updateAddressFireStationMapping(jsonFireStation);
      if (updatedJsonFireStation != null) {
        return updatedJsonFireStation;
      } else {
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "no such address or fire station found"
        );
      }
    } else {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "no content provided"
      );
    }
  }

  /**
   * This method response to a Delete call. It deletes a fire station in the database.
   *
   * @param jsonFireStation a json body that refers to a fire station.
   * @return 200 OK if properly executed.
   */
  @DeleteMapping("")
  public JsonFireStation deleteFireStationAddressMapping(
    @RequestBody(required = false) JsonFireStation jsonFireStation) {
    LOGGER.info("DELETE request on /firestation");
    if (jsonFireStation != null) {
      JsonFireStation deletedJsonFireStation = iFireStationService
        .eraseAddressFireStationMapping(jsonFireStation);
      if (deletedJsonFireStation != null) {
        return deletedJsonFireStation;
      } else {
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "no such address"
        );
      }
    } else {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "no content provided"
      );
    }
  }
}
