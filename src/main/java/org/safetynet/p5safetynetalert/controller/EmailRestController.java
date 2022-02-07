package org.safetynet.p5safetynetalert.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.model.dto.EmailListDTO;
import org.safetynet.p5safetynetalert.service.urls.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("communityEmail")
public class EmailRestController {

  private static final Logger LOGGER = LogManager.getLogger(EmailRestController.class);
  @Autowired
  private IEmailService iEmailService;

  @GetMapping("")
  public EmailListDTO getAllEmailFromCityInhabitants(
    @RequestParam("city") String city
  ) {
    LOGGER.info("GET Request on /communityEmail?city=" + city);
    if (city == null || city.isBlank()) {
      LOGGER.debug("Given city is null.");
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "No content provided"
      );
    } else {
      EmailListDTO emailListDTO = iEmailService.getAllEmailFromCityInhabitants(city);
      if (emailListDTO != null) {
        LOGGER.debug("Email list properly serialized.");
        return emailListDTO;
      } else {
        LOGGER.debug("This city is not in the database.");
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "City not found"
        );
      }
    }
  }
}
