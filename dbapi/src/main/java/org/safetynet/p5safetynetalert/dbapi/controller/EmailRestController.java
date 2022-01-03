package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.model.dto.EmailListDTO;
import org.safetynet.p5safetynetalert.dbapi.service.urls.EmailService;
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

  @Autowired
  EmailService emailService;

  @GetMapping("")
  public EmailListDTO getAllEmailFromCityInhabitants(
      @RequestParam("city") String city
  ) {
    EmailListDTO emailListDTO = emailService.getAllEmailFromCityInhabitants(city);
    if (emailListDTO != null) {
      return emailListDTO;
    } else {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "address not found"
      );
    }
  }
}
