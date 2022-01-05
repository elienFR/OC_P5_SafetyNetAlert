package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPerson;
import org.safetynet.p5safetynetalert.dbapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.beans.PersistenceDelegate;


@RestController
@RequestMapping("/person")
public class PersonRestController {

  @Autowired
  PersonService personService;

  /**
   * This method is called with a post request on /person. It saves a new Person in DB thanks to a
   * JsonPerson file.
   * It returns the serialized JsonPerson Object used to save a new Person in DB.
   *
   * @param newJsonPerson is the json Body given during the post request.
   * @return the JsonPerson object of the saved Person.
   */
  @PostMapping("")
  public JsonPerson postPerson(@RequestBody JsonPerson newJsonPerson) {
    if (newJsonPerson != null) {
      JsonPerson postedJsonPerson = personService.createPerson(newJsonPerson);
      if (postedJsonPerson != null) {
        return newJsonPerson;
      } else {
        throw new ResponseStatusException(
          HttpStatus.NO_CONTENT, "No first name And/Or LastName Provided"
        );
      }

    } else {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "No content"
      );
    }
  }


}
