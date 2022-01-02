package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.model.dto.FireDTO;
import org.safetynet.p5safetynetalert.dbapi.service.FireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/fire")
public class FireRestController {

  @Autowired
  private FireService fireService;

  /**
   * This url returns the list of inhabitants living at the given address
   * as well as the number of the barracks of firefighters serving it.
   * The list includes name, phone number, age and background (medications, dosage and allergies)
   * for each person.
   *
   * @param road is the string of the road
   * @return a FireDTO object (see description)
   */
  @GetMapping("")
  public FireDTO getPersonsFromAddressInFire(
      @RequestParam("address") String road) {
//    FireDTO fireDTO = addressService.getFireDTOFromAddressInFire(road);
    FireDTO fireDTO = fireService.getFireDTOFromAddressInFire(road);

    if(fireDTO != null){
      return fireDTO;
    }else{
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "address not found"
      );
    }

  }

}
