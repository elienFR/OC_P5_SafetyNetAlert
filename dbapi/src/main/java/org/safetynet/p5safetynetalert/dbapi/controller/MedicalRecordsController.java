package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonMedicalRecord;
import org.safetynet.p5safetynetalert.dbapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/medicalRecord")
public class MedicalRecordsController {

  @Autowired
  PersonService personService;

  @PostMapping("")
  public JsonMedicalRecord createNewMedicalRecords(@RequestBody JsonMedicalRecord jsonMedicalRecord) {
    if (jsonMedicalRecord == null) {
      throw new ResponseStatusException(
        HttpStatus.ALREADY_REPORTED, "already exists"
      );
    } else {
      JsonMedicalRecord jsonMedicalRecordToCreate = personService
        .createMedicalRecords(jsonMedicalRecord);
      return jsonMedicalRecordToCreate;
    }
  }

}
