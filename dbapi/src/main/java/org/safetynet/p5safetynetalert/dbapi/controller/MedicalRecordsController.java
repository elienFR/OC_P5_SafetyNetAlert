package org.safetynet.p5safetynetalert.dbapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.dbapi.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordsController {

  private static final Logger LOGGER = LogManager.getLogger(MedicalRecordsController.class);
  @Autowired
  private IPersonService iPersonService;

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
      LOGGER.warn("No body provided.");
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "The request needs a body"
      );
    } else {
      JsonMedicalRecord jsonMedicalRecordToCreate = iPersonService.createMedicalRecords(jsonMedicalRecord);
      if (jsonMedicalRecordToCreate == null) {
        LOGGER.warn("Medical record already exists.");
        throw new ResponseStatusException(
          HttpStatus.ALREADY_REPORTED, "Medical Record already exists"
        );
      } else {
        return jsonMedicalRecordToCreate;
      }
    }
  }

  @PutMapping("")
  public JsonMedicalRecord updateMedicalRecords(@RequestBody JsonMedicalRecord jsonMedicalRecord) {
    if (jsonMedicalRecord == null) {
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "Body is needed in request"
      );
    } else {
      JsonMedicalRecord jsonMedicalRecordUpdated = iPersonService.updateMedicalRecords(jsonMedicalRecord);
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
      JsonMedicalRecord jsonMedicalRecordToBeDeleted = iPersonService.deleteMedicalRecords(jsonMedicalRecord);
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
