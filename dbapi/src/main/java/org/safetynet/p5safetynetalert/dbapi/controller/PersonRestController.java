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
  public JsonPerson putPerson(@RequestBody JsonPerson putJsonPerson) {
    if (putJsonPerson == null) {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "No body content."
      );
    } else {
      JsonPerson updatedJsonPerson = personService.updatePerson(putJsonPerson);
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
   * @return the serialized updated person.
   */
  @DeleteMapping("")
  public void deletePerson(@RequestBody JsonPerson jsonPerson) {
    if (jsonPerson == null) {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "No jsonBody provided"
      );
    } else {
     JsonPerson personToDelete = personService.delete(jsonPerson);
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
