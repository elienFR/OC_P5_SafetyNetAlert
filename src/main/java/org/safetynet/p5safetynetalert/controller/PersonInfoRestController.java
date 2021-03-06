package org.safetynet.p5safetynetalert.controller;

import com.sun.istack.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.dto.PersonsInfoDTO;
import org.safetynet.p5safetynetalert.service.urls.IPersonInfoService;
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

  private static final Logger LOGGER = LogManager.getLogger(PersonInfoRestController.class);
  @Autowired
  private IPersonInfoService iPersonInfoService;

  @GetMapping("")
  public PersonsInfoDTO getPersonInfoFromFirstAndLastName(
    @RequestParam(value = "firstName") String firstName,
    @NotNull @NotBlank @RequestParam(value = "lastName") String lastName
  ) {
    LOGGER.info("GET request made on /personInfo?firstName=" + firstName + "&lastName=" + lastName);
    PersonsInfoDTO personsInfoDTO = iPersonInfoService
      .getPersonInfoFromFirstAndOrLastName(firstName, lastName);
    if (personsInfoDTO != null) {
      return personsInfoDTO;
    } else {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND, "entity not found"
      );
    }
  }

}
