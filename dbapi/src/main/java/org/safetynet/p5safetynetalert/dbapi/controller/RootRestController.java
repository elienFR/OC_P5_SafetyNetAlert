package org.safetynet.p5safetynetalert.dbapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("")
public class RootRestController {
  @GetMapping("")
  public void rootRequest(){
    throw new ResponseStatusException(
      HttpStatus.NOT_FOUND,"no such endpoint"
    );
  }
}
