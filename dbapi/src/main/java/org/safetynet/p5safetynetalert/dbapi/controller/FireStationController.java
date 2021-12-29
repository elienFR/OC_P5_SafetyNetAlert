package org.safetynet.p5safetynetalert.dbapi.controller;

import org.safetynet.p5safetynetalert.dbapi.dto.PersonFromFirestationDTO;
import org.safetynet.p5safetynetalert.dbapi.model.Person;
import org.safetynet.p5safetynetalert.dbapi.service.FireStationService;
import org.safetynet.p5safetynetalert.dbapi.service.MajorityCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FireStationController {

  @Autowired
  FireStationService fireStationService;
  @Autowired
  MajorityCalculatorService majorityCalculatorService;

  @GetMapping("/firestation")
  public String getPersonFromFireStationNumber (
      @RequestParam("stationNumber") String fireStationNumber, Model model) throws Exception {

    Iterable<PersonFromFirestationDTO> personsDTOList =
        fireStationService.getPersonDTOFromFireStationId(fireStationNumber);
    Iterable<Person> personList =
        fireStationService.getPersonFromFireStationId(fireStationNumber);

//    MajorityCalculatorService majorityCalculatorService = new MajorityCalculatorService();
    Integer majors = majorityCalculatorService.countMajors(personList);
    Integer minors = majorityCalculatorService.countMinors(personList);

    model.addAttribute("persons", personsDTOList);
    model.addAttribute("majorsNumber", majors);
    model.addAttribute("minorsNumber",minors);

    return "personsfromfirestation";
  }

}
