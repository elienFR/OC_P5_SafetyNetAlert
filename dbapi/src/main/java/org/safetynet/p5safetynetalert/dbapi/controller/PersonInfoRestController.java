package org.safetynet.p5safetynetalert.dbapi.controller;

import com.sun.istack.NotNull;
import org.safetynet.p5safetynetalert.dbapi.model.dto.PersonsInfoDTO;
import org.safetynet.p5safetynetalert.dbapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoRestController {

  @Autowired
  PersonService personService;

  //TODO : need to fix the required = false firstName that does not work. ie : server throws an 500 error when the firstName parameter is not specified.
  @GetMapping("")
  public PersonsInfoDTO getPersonInfoFromFirstAndLastName(
      @RequestParam(value = "firstName",required = false) String firstName,
      @NotNull @NotBlank @RequestParam(value = "lastName") String lastName
  ) {
    PersonsInfoDTO personsInfoDTO = personService
        .getPersonInfoFromFirstAndOrLastName(firstName, lastName);
    if(personsInfoDTO != null) {
      return personsInfoDTO;
    } else {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "entity not found"
      );
    }
  }

}
