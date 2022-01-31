package org.safetynet.p5safetynetalert.dbapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.dbapi.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/person")
public class PersonRestController {

  private static final Logger LOGGER = LogManager.getLogger(PersonRestController.class);
  @Autowired
  private IPersonService iPersonService;

  /**
   * This method is called with a post request on /person. It saves a new Person in DB thanks to a
   * JsonPerson file.
   * It returns the serialized JsonPerson Object used to save a new Person in DB.
   *
   * @param newJsonPerson is the json Body given during the post request.
   * @return the JsonPerson object of the saved Person.
   */
  @PostMapping("")
  public JsonPerson postPerson(@RequestBody(required = false) JsonPerson newJsonPerson) {
    if (newJsonPerson != null) {
      LOGGER.info("POST request made on url /person.");
      JsonPerson postedJsonPerson = iPersonService.createPerson(newJsonPerson);
      if (postedJsonPerson != null) {
        return postedJsonPerson;
      } else {
        throw new ResponseStatusException(
          HttpStatus.CONFLICT, "No first name And/Or LastName Provided or person already exists"
        );
      }
    } else {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "No body content"
      );
    }
  }

  /**
   * This method is called with a PUT request on /person endpoint. It updates a person in DB.
   *
   * @param putJsonPerson the json person object you want to update in database.
   * @return the serialized updated person.
   */
  @PutMapping("")
  public JsonPerson putPerson(@RequestBody(required = false) JsonPerson putJsonPerson) {
    LOGGER.info("PUT request made on url /person.");
    if (putJsonPerson == null) {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "No body content."
      );
    } else {
      JsonPerson updatedJsonPerson = iPersonService.updatePersonWithJsonPerson(putJsonPerson);
      if (updatedJsonPerson == null) {
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "You must at least furnish a valid first name and last name couple."
        );
      } else {
        return updatedJsonPerson;
      }
    }
  }

  /**
   * This method is called with a DELETE request on /person endpoint. It deletes a person in DB.
   *
   * @param jsonPerson the json person object you want to update in database.
   */
  @DeleteMapping("")
  public void deletePerson(@RequestBody(required = false) JsonPerson jsonPerson) {
    LOGGER.info("DELETE request made on url /person.");
    if (jsonPerson == null) {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "No jsonBody provided"
      );
    } else {
     JsonPerson personToDelete = iPersonService.delete(jsonPerson);
      if (personToDelete == null) {
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "person has not been found"
        );
      } else {
        throw new ResponseStatusException(
          HttpStatus.OK, "Person properly deleted"
        );
      }
    }
  }

}
