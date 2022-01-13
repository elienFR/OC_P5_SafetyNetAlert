package org.safetynet.p5safetynetalert.dbapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.dbapi.service.urls.ChildAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertRestController {

  private static final Logger LOGGER = LogManager.getLogger(ChildAlertRestController.class);
  @Autowired
  ChildAlertService childAlertService;

  @GetMapping("")
  public ChildFromAddressDTO getChildrenFromAddress(
    @RequestParam("address") String address) throws Exception {
    LOGGER.info("GET Request on /childAlert?address=" + address);
    if (address == null) {
      LOGGER.error("No address provided");
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "No content provided"
      );
    } else {
      ChildFromAddressDTO childFromAddressDTO = childAlertService.getChildrenFromAddress(address);
      if (childFromAddressDTO == null) {
        LOGGER.info("Address is not in the database.");
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Address Not Found."
        );
      } else {
        return childFromAddressDTO;
      }
    }
  }
}
