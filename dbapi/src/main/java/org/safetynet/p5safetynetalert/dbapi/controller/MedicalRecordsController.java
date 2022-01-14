package org.safetynet.p5safetynetalert.dbapi.controller;

import lombok.extern.java.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.dbapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordsController {

  private static final Logger LOGGER = LogManager.getLogger(MedicalRecordsController.class);
  @Autowired
  private PersonService personService;

  /**
   * This method is called with a POST http request. It creates a new medical record.
   *
   * @param jsonMedicalRecord is the json body of the created medical record.
   * @return the json body of the created medical record if properly saved.
   */
  @PostMapping("")
  public JsonMedicalRecord createNewMedicalRecords(
    @RequestBody JsonMedicalRecord jsonMedicalRecord) {
    LOGGER.info("POST request made on url /medicalRecord.");
    if (jsonMedicalRecord == null) {
      JsonMedicalRecord jsonMedicalRecordToCreate = personService.createMedicalRecords(jsonMedicalRecord);
      if (jsonMedicalRecordToCreate == null) {
        LOGGER.warn("Medical record already exists.");
        throw new ResponseStatusException(
          HttpStatus.ALREADY_REPORTED, "Medical Record already exists"
        );
      } else {
        return jsonMedicalRecordToCreate;
      }
    } else {
      LOGGER.warn("No body provided.");
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "The request needs a body"
      );
    }
  }

  @PutMapping("")
  public JsonMedicalRecord updateMedicalRecords(@RequestBody JsonMedicalRecord jsonMedicalRecord) {
    if (jsonMedicalRecord == null) {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "Body is needed in request"
      );
    } else {
      JsonMedicalRecord jsonMedicalRecordUpdated = personService.updateMedicalRecords(jsonMedicalRecord);
      if (jsonMedicalRecordUpdated == null) {
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Person not found"
        );
      } else {
        return jsonMedicalRecordUpdated;
      }
    }
  }

  @DeleteMapping("")
  public JsonMedicalRecord deleteMedicalRecords(@RequestBody JsonMedicalRecord jsonMedicalRecord) {
    if (jsonMedicalRecord == null) {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "no body request"
      );
    } else {
      JsonMedicalRecord jsonMedicalRecordToBeDeleted = personService.deleteMedicalRecords(jsonMedicalRecord);
      if (jsonMedicalRecordToBeDeleted == null) {
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Person not found."
        );
      } else {
        return jsonMedicalRecordToBeDeleted;
      }
    }
  }

}
