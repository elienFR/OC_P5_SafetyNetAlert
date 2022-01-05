package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.model.initPersist.JsonPerson;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class PersonRestController {

  /**
   *
   *
   * @param newJsonPerson
   * @return
   */
  @PostMapping("")
  public JsonPerson postPerson(@RequestBody JsonPerson newJsonPerson) {

  }


}
