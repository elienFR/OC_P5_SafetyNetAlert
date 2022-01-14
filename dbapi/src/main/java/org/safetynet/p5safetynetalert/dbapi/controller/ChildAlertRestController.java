package org.safetynet.p5safetynetalert.dbapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.safetynet.p5safetynetalert.dbapi.model.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.dbapi.service.urls.IChildAlertService;
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
  private IChildAlertService iChildAlertService;

  /**
   * This url returns a list of children (anyone aged 18 or under) living at this address.
   * The list includes each child's first and last name, age, and a list of others
   * household members. If there is no child, this url returns an empty string.
   *
   * @param address This String is the road you want to extract email from.
   * @return a serialized object. see description
   */
  @GetMapping("")
  public ChildFromAddressDTO getChildrenFromAddress(
    @RequestParam("address") String address) {
    LOGGER.info("GET Request on /childAlert?address=" + address);
    if (address == null) {
      LOGGER.debug("No address provided");
      throw new ResponseStatusException(
        HttpStatus.NO_CONTENT, "No content provided"
      );
    } else {
      ChildFromAddressDTO childFromAddressDTO = iChildAlertService.getChildrenFromAddress(address);
      if (childFromAddressDTO == null) {
        LOGGER.debug("Address is not in the database.");
        throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Address Not Found."
        );
      } else {
        LOGGER.debug("Child(ren) and resident(s) properly serialized.");
        return childFromAddressDTO;
      }
    }
  }
}
