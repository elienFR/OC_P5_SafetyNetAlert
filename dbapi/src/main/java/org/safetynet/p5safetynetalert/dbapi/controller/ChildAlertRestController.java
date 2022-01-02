package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.model.dto.ChildFromAddressDTO;
import org.safetynet.p5safetynetalert.dbapi.service.ChildAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/childAlert")
public class ChildAlertRestController {

  @Autowired
  ChildAlertService childAlertService;

  @GetMapping("")
  public ChildFromAddressDTO getChildrenFromAddress(
      @RequestParam("address") String address) throws Exception {
    return childAlertService.getChildrenFromAddress(address);
  }
}
